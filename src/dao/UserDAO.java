package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import model.Group;
import model.Item;
import model.ItemFile;
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
			stmt = connection.prepareStatement("select * from user where userName = ?");
			stmt.setString(1, user.getUsername());
			rs = stmt.executeQuery();
			if (rs.next()) {
				passwordFromDB = rs.getString("password");
				user.setId(rs.getInt("userID"));
				user.setStatus(rs.getInt("status"));
				setStatusDB(user);
				user.setCounter(rs.getInt("counter"));
				setCounterDB(user);
				user.setAdmin(rs.getInt("isAdmin"));
				if(user.getStatus() == 2)
					throw new SQLException("The username " + user.getUsername() + " is blocked.");
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
	
	public HashMap<String, Group> getAllUserInGroup(User user) throws SQLException {
		HashMap<String, Group> items = new HashMap<String, Group>();
		ResultSet rs = null;
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement("SELECT * FROM usergroups u,groups g where u.userID = ? and g.id = u.groupID");
			stmt.setInt(1, user.getID());
			rs = stmt.executeQuery();
			while (rs.next()) {
					Group group= new Group();
					group.setGroupID(rs.getInt("groupID"));
					group.setName(rs.getString("groupName"));
					System.out.println(group.getName()+("\n"));
					items.put("group" + group.getStringID(), group);
			}

			return items;		
		}
		finally {
			if (rs != null)
				rs.close();
			if (stmt != null) 
				stmt.close();
		}
	}
	
	public HashMap<String, Group> getAllUserNotInGroup(User user) throws SQLException {
		HashMap<String, Group> items = new HashMap<String, Group>();
		ResultSet rs = null;
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement("SELECT * FROM groups g where g.groupID not in(select u.groupID from usergroups u where u.userID = ?)");
			stmt.setInt(1, user.getID());
			rs = stmt.executeQuery();
			while (rs.next()) {
				Group group= new Group();
				group.setGroupID(rs.getInt("groupID"));
				group.setName(rs.getString("groupName"));
				System.out.println(group.getName()+("\n"));
				items.put("group" + group.getStringID(), group);
		}


			return items;		
		}
		finally {
			if (rs != null)
				rs.close();
			if (stmt != null) 
				stmt.close();
		}
	}
	
	public void setStatusDB(User user) throws SQLException{
		PreparedStatement stmt = null;
		stmt = connection.prepareStatement("UPDATE user SET status=? where userName = ?");
		stmt.setInt(1, user.getStatus());
		stmt.setString(2, user.getUsername());
		stmt.executeUpdate();
	}
	
	public void setCounterDB(User user) throws SQLException{
		PreparedStatement stmt = null;
		stmt = connection.prepareStatement("UPDATE user SET counter=? where userName = ?");
		stmt.setInt(1, user.getCounter());
		stmt.setString(2, user.getUsername());
		stmt.executeUpdate();
	}
	
	/**
	 * Adds the user to the users table in the data base
	 * @param user - The user to sign up
	 * @return Integer (0 - failed, other - generated id for the user)
	 * @throws SQLException
	 */
	public int signUp(User user) throws SQLException {
		PreparedStatement stmt = null,stmt2 = null;
		ResultSet rs = null;		
		try {
			stmt = connection.prepareStatement("insert into user" + " (userName, password)" + " values (?, ?)", Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword());	
			stmt.executeUpdate();

			rs = stmt.getGeneratedKeys();
		    if (rs.next()) {
		    	stmt2 = connection.prepareStatement("insert into folder" + " (parentFolderID, userID, name)" + " values(null, ?, '/')", Statement.RETURN_GENERATED_KEYS);
		    	stmt2.setInt(1, rs.getInt(1));
				stmt2.executeUpdate();
		    	return rs.getInt(1);
		    } 		    
		    return 0;
		}
		finally {
			if (rs != null)
				rs.close();
			if (stmt != null) 
				stmt.close();
			if (stmt2 != null)
				stmt2.close();
		}
	}
	
}

