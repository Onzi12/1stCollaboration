package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import model.ItemFile;
import model.ItemFile.Privilege;
import server.Server;

public class FileDAO extends DAO<ItemFile> {
/*
	public static FileDAO DBtoObject(int fileID) {
		Connection con = Server.getConnection();
		FileDAO file = null;
		try(PreparedStatement stmt = con.prepareStatement("SELECT * FROM file WHERE fileID = ?") ) {
			stmt.setInt(1, fileID);
			ResultSet rs = stmt.executeQuery();
			file = new FileDAO();
			file.setID(rs.getInt(1));
			file.setName(rs.getString(2));
			file.setDescription(rs.getString(3));
			file.setPrivilege(Privilege.values()[rs.getInt(4)]);
			file.setIsDeleted(rs.getBoolean(5));
			file.setIsEdited(rs.getBoolean(6));
			file.setOwner(UserDAO.DBtoObject(rs.getInt(7)));	
		} catch(SQLException e){ Server.gui.showMessage("SQLERROR in FileDAO DBtoObject");}
		
		
		return file;
	}

}
*/

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
				file.setIsDeleted(rs.getBoolean(5));
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
				file.setIsDeleted(rs.getBoolean(5));
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



	public void ObjectToDB(ItemFile file){
		Connection con = Server.getConnection();
		try(Statement stmt = con.createStatement()){
			int update = stmt.executeUpdate("UPDATE file SET fileID = "+ file.getID() + ", fileName = " + file.getName() + 
											", Descripition = " + file.getDescription() + ", privilege = " + file.getPrivilege() + 
											", isDeleted = " + file.isDeleted() + ", isEdited = " + file.isEdited() + 
											", ownerID = " + file.getOwner().getID());
			if (update == 0)
				stmt.executeUpdate("INSERT INTO USER (fileID,fileName,Descripition,privilege,isDeleted,isEdited,ownerID) VALUES (" 
									+ file.getID() + "," + file.getName() + "," + file.getDescription() + ","
									+ file.getPrivilege() + "," + file.isDeleted() +  file.isEdited() + "," + 
									file.getOwner().getID() + ")",Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = stmt.getGeneratedKeys();
			if( rs.next() ) 
				file.setID( rs.getInt(1) );
			} catch (SQLException e) { e.printStackTrace(); }
		
	}
	
	public Set<ItemFile> getUserFiles(int id)
	{
		Connection con = Server.getConnection();
		HashSet<ItemFile> set = null;
		try(Statement stmt = con.createStatement() ) {
			ResultSet rs = stmt.executeQuery("SELECT * FROM userfiles u,file f where u.fileID = f.fileID and u.userID = " + id);
			set = new HashSet<ItemFile>();
			while( rs.next() )
				{
				ItemFile file = new ItemFile();
				file.setID(rs.getInt(4));
				file.setName(rs.getString(5));
				file.setDescription(rs.getString(6));
				file.setPrivilege(Privilege.values()[rs.getInt(7)]);
				file.setIsDeleted(rs.getBoolean(8));
				file.setIsEdited(rs.getBoolean(9));
				file.setOwner(new UserDAO().DBtoObject(rs.getInt(10)) );
				set.add(file);
				}
			} catch(SQLException e){ e.printStackTrace(); }
		
		
		
		return set;
	}

	@Override
	public void deleteFromDB(ItemFile file) {
		Connection con = Server.getConnection();
		try(Statement stmt = con.createStatement() ) 
		{
			stmt.executeUpdate("DELETE FROM file WHERE fileID = " + file.getID() );
		} catch (SQLException e) { e.printStackTrace(); }
		
	}
	
	public ItemFile DBtoObject(String name) {
		Connection con = Server.getConnection();
		ItemFile file = null;
		try (Statement stmt = con.createStatement()) {
			ResultSet rs = stmt.executeQuery("SELECT * FROM file WHERE fileName = " + name);
			if (rs.next()){
				file = new ItemFile();
				file.setID(rs.getInt(1));
				file.setName(rs.getString(2));
				file.setDescription(rs.getString(3));
				file.setPrivilege(Privilege.values()[rs.getInt(4)]);
				file.setIsDeleted(rs.getBoolean(5));
				file.setIsEdited(rs.getBoolean(6));
				file.setOwner(new UserDAO().DBtoObject(rs.getInt(7)) );
				}
			} catch(SQLException e){ e.printStackTrace(); }
		return file;
		}
	}