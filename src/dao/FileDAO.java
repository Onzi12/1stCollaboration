package dao;

import java.io.File;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import model.Group;
import model.Item;
import model.ItemFile;
import model.ItemFile.Privilege;
import model.ItemFile.State;
import model.ItemFolder;
import server.Server;

public class FileDAO implements DAO<ItemFile> {


	@Override
	public Set<ItemFile> getAllfromDB() {
		Connection con = Server.getConnection();
		HashSet<ItemFile> set = null;
	
		try(Statement stmt = con.createStatement() ) {
			ResultSet rs = stmt.executeQuery("SELECT * FROM file");
			set = new HashSet<ItemFile>();
			while( rs.next() )
				{
				ItemFile file = new ItemFile();
				file.setID(rs.getInt(1));
				file.setName(rs.getString(2));
				file.setDescription(rs.getString(3));
				file.setPrivilege(Privilege.values()[rs.getInt(4)]);
				file.setState(State.values()[rs.getInt(5)]);
				file.setIsEdited(rs.getBoolean(6));
				file.setOwner(new UserDAO().DBtoObject(rs.getInt(7)) );
				set.add(file);
				}
			} catch(SQLException e){ e.printStackTrace(); }
		
		return set;
	
		// TODO Auto-generated method stub
	}
/*
public static void objectToDB(FileDAO file) {
	Connection con = Server.getConnection();
	try(Statement stmt = con.createStatement() )
		{
		int update = stmt.executeUpdate("UPDATE file SET fileName = '"+ file.getName() + 
									   "' WHERE fileID = "+file.getFileID());
		if(update == 0)
			stmt.executeUpdate("INSERT INTO file (fileName) VALUES('"+file.getName()+"')" );
		} catch (SQLException e) { e.printStackTrace(); }
}*/
	
	public Set<Item> getFolderFiles(ItemFolder fol){
		Connection con = Server.getConnection();
		HashSet<Item> set = new HashSet<>();
		try( Statement stmt  = con.createStatement() ) 
		{
			ResultSet rs = stmt.executeQuery("SELECT fileID FROM userFiles WHERE folderID = "+fol.getID());
			while(rs.next())
			  {
				Item file = new FileDAO().DBtoObject(rs.getInt("fileID"));
				set.add(file);
			  }
		} catch (SQLException e)  { e.printStackTrace(); }
		
		return set;	
	}
	
	
	
	public ItemFile DBtoObject(int id) {
		Connection con = Server.getConnection();
		ItemFile file = new ItemFile();
		try (Statement stmt = con.createStatement()) {
			ResultSet rs = stmt.executeQuery("SELECT * FROM file WHERE fileId = " + id);
			if (rs.next()){
				file = new ItemFile();
				file.setID(rs.getInt(1));
				file.setName(rs.getString(2));
				file.setDescription(rs.getString(3));
				file.setPrivilege(Privilege.values()[rs.getInt(4)]);
				file.setState(State.values()[rs.getInt(5)]);
				file.setIsEdited(rs.getBoolean(6));
				file.setOwner(new UserDAO().DBtoObject(rs.getInt(7)) );
				}
			} catch(SQLException e){ e.printStackTrace(); }
		return file;
	}
	
	public void putAlltoDB(Set<ItemFile> list) {
		for(ItemFile file : list )
			ObjectToDB(file);
	}

	public Set<ItemFile> getGroupFiles(Group group){
		HashSet<ItemFile> set = null;
		Connection con = Server.getConnection();
		FileDAO fileDAO = new FileDAO();
		try(Statement stmt = con.createStatement())
		{					
			set = new HashSet<ItemFile>();
			ResultSet rs = stmt.executeQuery("SELECT fileID FROM fileGroups WHERE groupID = "+group.getGroupID() );	
			while(rs.next())
			{
				set.add(fileDAO.DBtoObject(rs.getInt(1)));	
			}  
	} catch(SQLException e){ e.printStackTrace(); }
		
		
		return set;
	}

	public void ObjectToDB(ItemFile file) {
		Connection con = Server.getConnection();
		try(Statement stmt = con.createStatement()){
			int update = stmt.executeUpdate("UPDATE file SET fileName = '" + file.getName() + 
											"', Description = '" + file.getDescription() + "', privilege = " + file.getPrivilege().getValue() + 
											" , isDeleted = " + file.getState().getValue() + ", isEdited = " + file.getIsEditedValue() + 
											", ownerID = " + file.getOwner().getID()+
											" WHERE fileID = "+file.getID());
			
			if (update == 0)
				stmt.executeUpdate("INSERT INTO file (fileName,Description,privilege,isDeleted,isEdited,ownerID) VALUES ('" 
									+ file.getName() + "','" + file.getDescription() + "',"
									+ file.getPrivilege().getValue() + "," + file.getState().getValue() +","+  file.getIsEditedValue() + "," + 
									file.getOwner().getID() + ")",Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = stmt.getGeneratedKeys();
			if( rs.next() ) 
				file.setID( rs.getInt(1) );
			} catch (SQLException e) { e.printStackTrace(); }
		
	}
	
	public void addToUserFilesDB(ItemFile file) throws SQLException {
		 
		Connection con = Server.getConnection();
		try( Statement stmt = con.createStatement() ) {
			
			stmt.executeUpdate("INSERT INTO userfiles (userID ,folderID ,fileID) VALUES (" 
					+ file.getOwner().getID() + "," 
					+ file.getParentID() + ","
					+ file.getID() + ")");	
			
		} 
		
	}
	
	public void updateFileLocationInDB(ItemFile file, int oldParentID) throws SQLException {

		Connection con = Server.getConnection();
		try( Statement stmt = con.createStatement() ) {
			
			stmt.executeUpdate("UPDATE userfiles SET userID = " + file.getUserID()
																+ ", folderID = " + file.getParentID()
																+ ", fileID = " + file.getID() 
																+ " WHERE fileID = " + file.getID() 
																+ " AND folderID = " + oldParentID 
																+ " AND userID = " + file.getUserID());	
			
		} 
		
	}
	
	/*
	public Set<ItemFile> getUserAddFiles(int id)
	{
		Connection con = Server.getConnection();
		HashSet<ItemFile> set = null;
		try(Statement stmt = con.createStatement()){
			ResultSet rs = stmt.executeQuery("select * from file where isDeleted = 0 AND privilege = 2 AND fileID not in(SELECT fileID From userfile WHERE userID = " + id +") ");
		}catch(SQLException e){ e.printStackTrace(); 
		
		return set;
	}*/
	
	public Set<ItemFile> getUserFiles(int id)
	{
		FileDAO fileDAO = new FileDAO();
		Connection con = Server.getConnection();
		HashSet<ItemFile> set = null;
		try(Statement stmt = con.createStatement() ) {
			ResultSet rs = stmt.executeQuery("SELECT * FROM userFiles u,file f where u.fileID = f.fileID and u.userID = " + id);
			set = new HashSet<ItemFile>();
			while( rs.next() )
				{
					set.add(fileDAO.DBtoObject(rs.getInt(1)));	  
				}
			} catch(SQLException e){ e.printStackTrace();}
		return set;
	}
	
	public void deletePhysicalFile(ItemFile file)
	{
		Connection con = Server.getConnection();
		try(Statement stmt = con.createStatement()){
			stmt.executeUpdate("UPDATE file set isDeleted = 2 where fileID = " + file.getID());
			System.out.println("delete physical file method before file");
			File pfile = new File(System.getProperty("user.home") + "\\desktop\\MyBox\\"+file.getName());

			if (pfile.exists()) 
				pfile.delete();

		} catch(SQLException e){ e.printStackTrace();}
		  catch (Exception e){e.printStackTrace();}
		
			
	}
	
	public Set<ItemFile> getDeletePhysicalFiles(int uid)
	{
		HashSet<ItemFile> set = null;
		Connection con = Server.getConnection();
		FileDAO fileDAO = new FileDAO();
		try(Statement stmt = con.createStatement())
		{
			ResultSet rs = stmt.executeQuery("SELECT fileID FROM file where isDeleted <> 2 and isEdited = 0 and ownerID = "+ uid );
			set = new HashSet<ItemFile>();
			while(rs.next())
			{
				set.add(fileDAO.DBtoObject(rs.getInt(1)));	
			}  
		}catch(SQLException e){ e.printStackTrace();}
		
		return set;
	}
	
	public Set<ItemFile> getAddUserFiles(int uid,int fid)
	{
		FileDAO fileDAO = new FileDAO();
		Connection con = Server.getConnection();
		HashSet<ItemFile> set = null;
		System.out.println(fid);
		String str = "SELECT * FROM file f WHERE isDeleted = 0 and fileID not in(SELECT fileID FROM userfiles WHERE userID = " + uid +" and folderID = " + fid + " ) AND privilege = ";
		try(Statement stmt = con.createStatement() ) {
			ResultSet rs = stmt.executeQuery(str + "2");
			set = new HashSet<ItemFile>();
			while(rs.next())
				{
					set.add(fileDAO.DBtoObject(rs.getInt(1)));	
				}  
			Statement stmt2 = con.createStatement();
			ResultSet rs2 = stmt2.executeQuery(str + " 1 and fileID in (SELECT fg.fileID FROM filegroups fg,usergroups ug where ug.userID = " + uid + " AND fg.groupID = ug.groupID)");
			while(rs2.next())
				{
					set.add(fileDAO.DBtoObject(rs2.getInt(1)));	
				}  
			} catch(SQLException e){ e.printStackTrace();}
		return set;
	}
	
	public Set<ItemFile> getUserRestoreFile(int uid)
	{
		Set<ItemFile> set = null;
		Connection con = Server.getConnection();
		FileDAO fileDAO = new FileDAO();
		try(Statement stmt = con.createStatement())
		{
			ResultSet rs = stmt.executeQuery("SELECT fileID FROM file where isDeleted = 1 and ownerID = "+ uid );
			set = new HashSet<ItemFile>();
			while(rs.next())
			{
				set.add(fileDAO.DBtoObject(rs.getInt(1)));	
			}  
		}catch(SQLException e){ e.printStackTrace();}
		
		return set;
	}
	
	public void userRestoreFile(int fid) {
		
		Connection con = Server.getConnection();
		try( Statement stmt = con.createStatement() ) {
			stmt.executeUpdate("UPDATE file set isDeleted = 0 AND fileID  = " + fid);
		} catch(SQLException e) { 
			e.printStackTrace();
		}
		
	}
	
	
	@Override
	public void deleteFromDB(ItemFile file) {
		Connection con = Server.getConnection();
		try(Statement stmt = con.createStatement() ) 
		{
			stmt.executeUpdate("DELETE FROM file WHERE fileID = " + file.getID() );
		} catch (SQLException e) { e.printStackTrace(); }
		
	}
	
	public void deleteFromUserFiles(ItemFile file, int parentID) {
		System.out.println("fileID: " + file.getID());
		System.out.println("folderID: " + parentID);
		System.out.println("userID: " + file.getUserID());

		Connection con = Server.getConnection();
		try( Statement stmt = con.createStatement() ) {
			stmt.executeUpdate("DELETE FROM userfiles WHERE fileID = " + file.getID() 
																+ " AND folderID = " + parentID 
																+ " AND userID = " + file.getUserID());
		} catch (SQLException e) { 
			e.printStackTrace();
		}
	}
	public void insertToUserFiles(ItemFile file, int parentID, int userID) throws SQLException
	{
		Connection con = Server.getConnection();
		try ( Statement stmt = con.createStatement() ) {
			stmt.executeUpdate("INSERT INTO userfiles (userID, folderID, fileID) VALUES (" 
					+ userID + ", " 
					+ parentID + ", "
					+ file.getID() + ")");	
		} 
			
	}
//	public void UpdateIsEdi
	public ItemFile DBtoObject(String name) {
		Connection con = Server.getConnection();
		ItemFile file = null;
		try (Statement stmt = con.createStatement()) {
			ResultSet rs = stmt.executeQuery("SELECT * FROM file WHERE fileName = " + name);
			if (rs.next()){
				file = new ItemFile();
				file.setID(rs.getInt(1));
				file.setName(rs.getString(2));
				file.setDescription(rs.getString(3) );
				file.setPrivilege(Privilege.values()[rs.getInt(4)]);
				file.setState(State.values()[rs.getInt(5)]);
				file.setIsEdited(rs.getBoolean(6));
				file.setOwner(new UserDAO().DBtoObject(rs.getInt(7)) );
				}
			} catch(SQLException e){ e.printStackTrace(); }
		return file;
		}
	}