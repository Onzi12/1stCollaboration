package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeSet;

import model.ItemFile.Privilege;
import server.Server;

public class UserDAO extends User {


	
	public static User DBtoObject(int userID) {
		
		Connection con = Server.getConnection();
		UserDAO user = null;
		
		if( (user = Server.getUserIfLoggedIn(userID)) != null )
			return user;
		
		try(PreparedStatement stmt = con.prepareStatement("SELECT * FROM user WHERE userID = ?") ) {
			stmt.setInt(1, userID);
			ResultSet rs = stmt.executeQuery();
			user = new UserDAO();
			user.setID(rs.getInt(1));
			user.setUserName(rs.getString(2));
			user.setPassword(rs.getString(3));
			user.setStatus(Status.values()[rs.getInt(4)]);
			user.setAdmin(rs.getBoolean(5));
			user.setCounter(rs.getInt(6));
			user.setGroups(GroupDAO.getUserGroups(user.getID()));
		} catch(SQLException e){ Server.gui.showMessage("SQLERROR in FileDAO DBtoObject");}
		
		
		return file;
	}




	

}
