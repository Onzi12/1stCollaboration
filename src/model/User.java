package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.TreeSet;

import server.Server;

public class User {
	
	public enum Status {
		NOTCONNECTED(0) ,CONNECTED(1) , BLOCKED(2);

	    private final int value;
	    
	    private Status(int value) {
	        this.value = value;
	    }

	    public int getValue() {
	        return value;
	    }
	}
	

	private static final long serialVersionUID = 5100974740161696228L;
	
	private int userID;
    private String userName;
    private String password;
    private Status status;
    private boolean isAdmin;
    private int counter;
    
    private ItemFolder rootFolder;
    private TreeSet<Group> groups;
    
	
	private static Connection connection;

	private void setGroups(TreeSet<Group> userGroups) {
		groups = userGroups;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public int getStatusValue() {
		return status.getValue();
	}
	
	public int getCounter() {
		return counter;
	}
	
	public void setCounter(int counter){
		this.counter = counter;
	}
	
    public ItemFolder getRootFolder() {
    		return rootFolder;
    }

    public void setRootFolder(ItemFolder root) {
    	rootFolder = root;
    }
    

	/**
	 * Set the username for the user (required and unique).
	 * @param username
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	/**
	 * Set the password for the user (required).
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	

	
	
	/**
	 * Get the unique username of the user.
	 * @return String
	 */
	public String getUserName() {
		return userName;
	}
	


	/**
	 * Get the password of the user.
	 * @return String
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Get the unique id of the user.
	 * @return id the user Database id
	 */
	public int getID() {
		return userID;
	}
	
	/**
	 * Set the unique id of the user.
	 * @param id the Database userID
	 */
	public void setID(int userID) {
		this.userID = userID;
	}
		

	public void setAdmin(boolean admin) {
		this.isAdmin = admin;
	}
	
	public boolean isAdmin() {
		return isAdmin;
	}
	
	public boolean equals(Object obj) {
		return userName == ((User)obj).userName;
	}
	
	
	/**
	 * Check the user details against the data base
	 * @param user - The user that is trying to log in
	 * @return boolean
	 * @throws Exception
	 */
	public static synchronized  User authenticate(String userName,String enteredPassword) throws Exception {
		String passwordFromDB;
		String exception = "The userName \""+userName+"\" ";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		User user;
		try {
			stmt = connection.prepareStatement("select * from user where username = ?");
			stmt.setString(1,userName);
			rs = stmt.executeQuery();
			
			// user exist in the Database?
			if (rs.next() == false )
				throw new SQLException(exception + "does not exists!");
			
			passwordFromDB = rs.getString("password");
			
			//get user database status
			Status status = Status.values()[ rs.getInt("status") ];
			
			//user is blocked on the Database?
			if( status == Status.BLOCKED )
				throw new Exception(exception+ "is blocked\n please contact the administrator!"
						+ "\n(Password was NOT checked)");			


			//user already logged in?
			if (status == Status.CONNECTED )
				throw new Exception(exception+ "is already connected!" );
			
			//enterdPassword is wrong?
			if( passwordFromDB.equals(enteredPassword) == false ) 
				{	
					int counter = rs.getInt("counter") ;
					setCounterDB(userName,  ++counter );
					if(counter == 2)
						{
						setStatusDB(userName, status);
						throw new Exception(exception+ "is blocked\n please contact the administrator!");
						}
					else
						throw new Exception(exception+ "Password is incorrect!");
				}
			

				//At last! all checks are fine - create user
				
				user = new User(userName,enteredPassword);
				user.setID( rs.getInt("iduser") );
				user.setAdmin( rs.getInt("admin") == 1 );
				user.setRootFolder( ItemFolder.DB;
			return user;		
		}
		finally {
			if (rs != null)
				rs.close();
			if (stmt != null) 
				stmt.close();
		}	
	}


public static void setStatusDB(String userName, Status status) throws SQLException{
	PreparedStatement stmt = null;
	stmt = getConnection().prepareStatement("UPDATE user SET status=? WHERE username = ?");
	stmt.setInt(1, status.value );
	stmt.setString(2, userName );
	stmt.executeUpdate();
}

private static Connection getConnection() {
	if(connection == null ) 
		connection = Server.getConnection();
	
	return connection;
}

public static void setCounterDB(String userName, int count) throws SQLException{
	PreparedStatement stmt = null;
	stmt = connection.prepareStatement("UPDATE user SET counter=? where username = ?");
	stmt.setInt(1, count);
	stmt.setString(2, userName);
	stmt.executeUpdate();
}


}
