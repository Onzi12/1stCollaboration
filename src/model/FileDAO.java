package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

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

public void putAlltoDB(Set<ItemFile> list) {
	for(ItemFile file : list )
		ObjectToDB(file);
}



public void ObjectToDB(ItemFile item){
	con = Server.getConnection();
	try(Statement stmt = con.createStatement()){
		int update = stmt.executeUpdate("UPDATE USER SET userID = "+ user.getID() +
										", userName = " + user.getUserName() + 
										", password = " + user.getPassword() + 
										", isAdmin = " + user.isAdmin() + 
										", counter = " + user.getCounter());
		if (update == 0)
			stmt.executeUpdate("INSERT INTO USER (userID,userName,password,status,isAdmin,counter) VALUES (" 
								+ user.getID() + "," + user.getUserName() + "," + user.getPassword() + ","
								+ user.isAdmin() + "," + user.getCounter() + ")",Statement.RETURN_GENERATED_KEYS);
		ResultSet rs = stmt.getGeneratedKeys();
		if( rs.next() ) 
		user.setID( rs.getInt(1) );
		} catch (SQLException e) { e.printStackTrace(); }
	
	}
	


@Override
public void deleteFromDB(ItemFile file) {
	Connection con = Server.getConnection();
	try(Statement stmt = con.createStatement() ) 
	{
		stmt.executeUpdate("DELETE FROM file WHERE fileID = " + file.getID() );
	} catch (SQLException e) { e.printStackTrace(); }
	
}

/*
@Override
public DAO DBtoObject(int id) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public void ObjectToDB(DAO obj) {
	// TODO Auto-generated method stub
	
}

@Override
public DAO DBtoObject(String name) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Set<DAO> getAllfromDB() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public void putAlltoDB(Set<DAO> list) {
	// TODO Auto-generated method stub
	
}

@Override
public void deleteFromDB(DAO obj) {
	// TODO Auto-generated method stub
	*/
}