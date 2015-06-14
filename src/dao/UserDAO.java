package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.User;


public class UserDAO {

	private Connection connection; 
	
	public UserDAO(Connection connection) {
		this.connection = connection;
	}
	
	/**
	 * Check the user details against the data base
	 * @param user - The user that is trying to log in
	 * @return boolean
	 * @throws Exception
	 */
	public boolean authenticate(User user) throws SQLException {
		String enteredPassword = user.getPassword();
		String passwordFromDB = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = connection.prepareStatement("select * from user where username = ?");
			stmt.setString(1, user.getUsername());
			rs = stmt.executeQuery();
			if (rs.next()) {
				passwordFromDB = rs.getString("password");
				user.setId(rs.getInt("iduser"));
			} else {
				throw new SQLException("The username " + user.getUsername() + " does not exists.");
			}
			
			
			return enteredPassword.equals(passwordFromDB);		
		}
		finally {
			if (rs != null)
				rs.close();
			if (stmt != null) 
				stmt.close();
		}	
	}

	/**
	 * Adds the user to the users table in the data base
	 * @param user - The user to sign up
	 * @return Integer (0 - failed, other - generated id for the user)
	 * @throws SQLException
	 */
	public int signUp(User user) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;		
		try {
			stmt = connection.prepareStatement("insert into user" + " (username, password, firstname, lastname, email)" + " values (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword());	
			stmt.setString(3, user.getFirstName());
			stmt.setString(4, user.getLastName());
			stmt.setString(5, user.getEmail());
			stmt.executeUpdate();
			
			rs = stmt.getGeneratedKeys();
		    if (rs.next()) {
		    	return rs.getInt(1);
		    } 		    
		    return 0;
		}
		finally {
			if (rs != null)
				rs.close();
			if (stmt != null) 
				stmt.close();
		}
	}
}
