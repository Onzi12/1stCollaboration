package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import model.ItemFolder;
import server.Server;

public class FolderDAO extends DAO<ItemFolder> {

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
				folder.setParent( DBtoObject(rs.getInt("parentFolderID")) );
				
			}
			
		} catch (SQLException e) { 
			e.printStackTrace(); 
		}
		
		return folder;
	}

	@Override
	public void ObjectToDB(ItemFolder folder) {
		
		Connection con = Server.getConnection();
		try( Statement stmt = con.createStatement() ) {
			
			int update = stmt.executeUpdate("UPDATE folder SET userID = " + folder.getUserID() +
											", parentFolderID = " + folder.getParent().getID() + 
											", name = '" + folder.getName() + 
											"' WHERE folderID = " + folder.getID());

			if(update == 0) {
				stmt.executeUpdate("INSERT INTO folder (userID, parentFolderID, name) VALUES(" + folder.getUserID() + ", " 
																							+ folder.getParent().getID()  + ", '" 
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
				folder.setParent( DBtoObject(rs.getInt("parentFolderID")) );
				
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
				folder.setParent( DBtoObject( rs.getInt("parentFolderID") ) );
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
	public void deleteFromDB(ItemFolder folder) {
		
		Connection con = Server.getConnection();
		try( Statement stmt = con.createStatement() ) {
			
			stmt.executeUpdate("DELETE FROM folder WHERE folderID = " + folder.getID() );
			
		} catch (SQLException e) {
			e.printStackTrace(); 
		}
		
	}
	
	public Set<ItemFolder> getUserFolders(int id) {
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
				folder.setParent( DBtoObject( rs.getInt("parentFolderID") ) );
				set.add( folder );
				
			}
			
		} catch(SQLException e) {
			e.printStackTrace(); 
		}
		
		return set;
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
