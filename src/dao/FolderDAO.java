package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import model.Item;
import model.ItemFolder;
import model.User;
import server.Server;

public class FolderDAO implements DAO<ItemFolder> {

	@Override
	public ItemFolder DBtoObject(int id) {
		
		Connection con = Server.getConnection();
		ItemFolder folder = null;
		
		try( Statement stmt = con.createStatement() ) {
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM folder WHERE folderID = " + id );
			
			if( rs.next() ) {
				
				folder = new ItemFolder();
				folder.setID( rs.getInt("folderID") );
				folder.setName( rs.getString("name") );
				folder.setUserID( rs.getInt("userID") );
				folder.setParentID( rs.getInt("parentFolderID") );
				
			}
			
		} catch (SQLException e) { 
			e.printStackTrace(); 
		}
		
		return folder;
	}
	public void checkInsert(ItemFolder folder) throws SQLException
	{
		Connection con = Server.getConnection();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM folder where userID = " + folder.getUserID() +
										" AND parentFolderID = " + folder.getParentID() + 
										" AND name = '" + folder.getName() + "'");
		if (rs.next()){			
			throw new SQLException("There is a folder with the same name");
		}
	}
	
	@Override
	public void ObjectToDB(ItemFolder folder) {
		
		Connection con = Server.getConnection();
		try( Statement stmt = con.createStatement() ) {
			int update = stmt.executeUpdate("UPDATE folder SET userID = " + folder.getUserID() +
											", parentFolderID = " + folder.getParentID() + 
											", name = '" + folder.getName() + 
											"' WHERE folderID = " + folder.getID());

			if(update == 0) {
				stmt.executeUpdate("INSERT INTO folder (userID, parentFolderID, name) VALUES(" + folder.getUserID() + ", " 
																							+ folder.getParentID()  + ", '" 
																							+ folder.getName() + "')" ,
																							Statement.RETURN_GENERATED_KEYS);
				
				ResultSet rs = stmt.getGeneratedKeys();
				if( rs.next() ) {
					folder.setID( rs.getInt(1) );
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace(); 
		}
		
	}

	@Override
	public ItemFolder DBtoObject(String name) {

		Connection con = Server.getConnection();
		ItemFolder folder = null;
		
		try( Statement stmt = con.createStatement() ) {
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM folder WHERE name = '" + name + "'" );
			
			if( rs.next() ) {
				
				folder = new ItemFolder();
				folder.setID( rs.getInt("folderID") );
				folder.setName( rs.getString("name") );
				folder.setUserID( rs.getInt("userID") );
				folder.setParentID( rs.getInt("parentFolderID") );
				
			}
			
		} catch (SQLException e) { 
			e.printStackTrace(); 
		}
		
		return folder;
	}

	@Override
	public Set<ItemFolder> getAllfromDB() {
		Connection con = Server.getConnection();
		HashSet<ItemFolder> set = null;

		try( Statement stmt = con.createStatement() ) {
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM folder");
			set = new HashSet<ItemFolder>();
			
			while( rs.next() ) {
				
				ItemFolder folder = new ItemFolder();
				folder.setID( rs.getInt("folderID") );
				folder.setUserID( rs.getInt("userID") );
				folder.setName( rs.getString("name") );
				folder.setParentID( rs.getInt("parentFolderID") );
				set.add( folder );
				
			}
			
		} catch(SQLException e) {
			e.printStackTrace(); 
		}
		
		return set;
	}

	@Override
	public void putAlltoDB(Set<ItemFolder> list) {
		
		for(ItemFolder folder : list ) {
			
			ObjectToDB(folder);

		}
		
	}

	@Override
	public void deleteFromDB(ItemFolder folder){
		
		Connection con = Server.getConnection();
		try( Statement stmt = con.createStatement() ) {
			
			stmt.executeUpdate("DELETE FROM folder WHERE folderID = " + folder.getID() );
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public HashSet<ItemFolder> getUserFolders(int id) {
		Connection con = Server.getConnection();
		HashSet<ItemFolder> set = null;

		try( Statement stmt = con.createStatement() ) {
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM folder WHERE userID = " + id);
			set = new HashSet<ItemFolder>();
			
			while( rs.next() ) {
				
				ItemFolder folder = new ItemFolder();
				folder.setID( rs.getInt("folderID") );
				folder.setUserID( rs.getInt("userID") );
				folder.setName( rs.getString("name") );
				folder.setParentID( rs.getInt("parentFolderID") );
				set.add( folder );
				
			}
			
		} catch(SQLException e) {
			e.printStackTrace(); 
		}
		
		return set;
	}
	
	/**
	 * Create and insert to DB, root folder for the user.
	 * @param userID
	 */
	public void createRootInDB(User user) {
		
		ItemFolder rootFolder = new ItemFolder();
		rootFolder.setName("/");
		rootFolder.setParentID(0);
		rootFolder.setUserID(user.getID());
		ObjectToDB(rootFolder);
	}
	
	

	public void createFolderContents(ItemFolder fol){
		Set<Item> files = new FileDAO().getFolderFiles(fol);
		Set<Item> subFolders = new FolderDAO().getFolderSubFolders(fol);
		for(Item f : files) {
			f.setPath(fol.getFullPath()+f.getName());
		}
		for(Item sub : subFolders ) {
			ItemFolder s = (ItemFolder)sub;
			//DefaultMutableTreeNode node = new DefaultMutableTreeNode(s);
			//node.setParent(fol.getTreeNode());
			//fol.getTreeNode().add(node);
			//s.setTreeNode(node);
			s.setPath(fol.getFullPath()+s.getName());
			createFolderContents(s);
		}

		files.addAll(subFolders);
		fol.setContents(files);
	}

	
	private Set<Item> getFolderSubFolders(ItemFolder fol) {
		Connection con = Server.getConnection();
		HashSet<Item> set = new HashSet<>();
		try( Statement stmt  = con.createStatement() ) 
		{
			ResultSet rs = stmt.executeQuery("SELECT folderID FROM folder WHERE parentFolderID = "+ fol.getID());
			while(rs.next())
			  {
				Item sub = new FolderDAO().DBtoObject(rs.getInt(1));
				set.add(sub);
			  }
		} catch (SQLException e) { e.printStackTrace(); }
		
		return set;
	}

	public void DBtoTree(User user) throws SQLException {		
		ItemFolder root = getRootFolderFromDB( user.getID() );
		//root.setTreeNode(new DefaultMutableTreeNode(root) );
		root.setPath("");
		createFolderContents(root);
		user.setRootFolder(root);
		
	}
	
	private ItemFolder getRootFolderFromDB(int userID) throws SQLException {
		
		ItemFolder root = null;
		Connection con = Server.getConnection();
		try( Statement stmt  = con.createStatement() ) {
			ResultSet rs = stmt.executeQuery("SELECT folderID FROM folder WHERE userID = " + userID + " AND name = '/'");
			if( rs.next() ) 
				root = new FolderDAO().DBtoObject( rs.getInt(1) );
			else 
				System.err.println("ERROR: root folder for user: " +userID +" is not exist!! ");

		}
		return root;
	}



//		public static ItemFolder DBtoItemTree(int userID) throws SQLException {
//			FolderDAO root = null;
//			Connection con = Server.getConnection();
//			try(Statement stmt  = con.createStatement() ) 
//			{
//				ResultSet rs = stmt.executeQuery("SELECT folderID FROM folder WHERE userID = "+userID+" AND name = '/'");
//				if(rs.next())
//					{
//					root = new FolderDAO();
//					root.setParent(null);
//					root.setName("/");
//					root.setId( rs.getInt("folderID") );
//					root.createContent();
//					}
//				else 
//					System.err.println("ERROR: root folder for user" +userID +" is not exist!! ");
//
//			}
//			return root;
//		}
//		
//		
//		private void createContent() {
//			Connection con = Server.getConnection();
//			try( Statement stmt  = con.createStatement() ) 
//			{
//				ResultSet rs = stmt.executeQuery("SELECT fileID FROM userfiles WHERE folderID = "+getId());
//				while(rs.next())
//				  {
//					FileDAO file = FileDAO.DBtoObject(rs.getInt("fileID"));
//				  }
//			}
//			
//			stmt2 = connection.prepareStatement("SELECT * FROM userfiles,file where userId = ? and file.id = userfiles.fileId");//where userId = ?
//			stmt2.setInt(1, user.getID());
//			rs2 = stmt2.executeQuery();
//		}
//
//
//		//Used by the server only
//		public ItemFolder DBgetFolder(int userID, int folderID) {
//			return null;
//		}
//		
//		//Used by the server only
//		public ItemFolder DBgetFolder(int userID, int folderID,String folderName) {
//			return null;
//		}
//
//		//Used by the server only
//		public Item[] DBgetFolderItems(int userID, int folderID) {
//			return null;
//		}




}
