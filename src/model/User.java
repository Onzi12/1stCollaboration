package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.sql.Connection;
import java.sql.Statement;

import dao.UserDAO;
import model.User;
import model.User.Status;

public class User extends HashMap<String, Object> {
	
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
	
	
	private HashMap<String, Item> files = new HashMap<String, Item>();
	
	/**
	 * Constructs an instance of a User.
	 * @param username - The username for the user (required and unique).
	 * @param password - The password for the user (required).
	 */
	public User(String username, String password) {
		setUsername(username);
		setPassword(password);
		}
	
	/**
	 * Constructs an instance of a User.
	 */
	public User() {}

	/**
	 * Set the username for the user (required and unique).
	 * @param username
	 */
	public void setUsername(String username) {
		put("username", username);
	}
	
	/**
	 * Set the password for the user (required).
	 * @param password
	 */
	public void setPassword(String password) {
		put("password", password);
	}
	
	/**
	 * Set the unique id of the user.
	 * @param id
	 */
	public void setId(int uid) {
		put("uid", uid);
	}
	
	
	/**
	 * Set the counter of the user.
	 * @param counter
	 */
	public void setCounter(int counter)throws SQLException {
		put("counter",counter);
	}
	
	/**
	 * Get the unique username of the user.
	 * @return String
	 */
	public String getUsername() {
		return (String)get("username");
	}
	
	/**
	 * change 
	 */

	/**
	 * Get the password of the user.
	 * @return String
	 */
	public String getPassword() {
		return (String)get("password");
	}
	
	/**
	 * Get the unique id of the user.
	 * @return String
	 */
	public int getID() {
		return (int)get("uid");
	}
		
	public int getCounter() {
		return (int)get("counter");
	}
	
	public HashMap<String, Item> getFiles() {
		return files;
	}
	
	public void setFiles(HashMap<String, Item> files) {
		this.files = files;
	}
	
	/**
	 * Get the status of the user.
	 * @return
	 */
	public int getStatus() {
		Status status = (Status)get("status");
	    return status.getValue();
	}
	
	
	/**
	 * Set the status of the user.
	 * @param status
	 */
//	public void setStatus(Status status)throws SQLException {
//		put("status", status);
//	}
	
	public void setStatus(int status) {
		
		switch (status) {
		case 0:
			put("status", Status.NOTCONNECTED);
			break;

		case 1:
			put("status", Status.CONNECTED);
			break;
			
		case 2:
			put("status", Status.BLOCKED);
			break;
		}
	}

	public void setAdmin(int admin) {
		put("admin",admin);
	}
	
	public int getAdmin(){
		return (int) get("admin");
	}
	
	
}
