package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.TreeSet;

import dao.UserDAO;
import server.Server;

public class User {
	
	public static enum Status {
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
	 * @return User the object of the registered user
	 * @throws Exception
	 */
	public static synchronized  User authenticate(String userName,String enteredPassword) throws Exception {

		String exception = "The userName \""+userName+"\" ";
		UserDAO uDao = new UserDAO();
		User user = uDao.DBtoObject(userName);
		
		//user exists?
		if(user == null )
			throw new Exception(exception+"does not exists!");
		
		//user is blocked on the Database?
		if(user.getStatus() == Status.BLOCKED )
			throw new Exception(exception+"is blocked\n please contact the administrator!");
		
		//user already logged in?
		else if(user.getStatus() == Status.CONNECTED )
			throw new Exception(exception+ "is already connected!" );
		
		//enterdPassword is wrong?
		if( user.getPassword().equals(enteredPassword) == false ) 
			{	
				int counter = user.getCounter();
				++counter;
				user.setCounter(counter);
				if(counter == 2)
					{
					user.setStatus(Status.BLOCKED);
					uDao.ObjectToDB(user);
					throw new Exception(exception+ "is blocked\n please contact the administrator!");
					}
				else
					{
					uDao.ObjectToDB(user);
					throw new Exception(exception+ "Password is incorrect!");
					}
			}
		return user;
		
	}

	public int getIsAdminValue() {
		return isAdmin() ? 1 : 0;
	}



}
