package model;

import java.util.HashMap;

public class User extends HashMap<String, Object> {

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
	 * Set the first name of the user.
	 * @param fName
	 */
	public void setFirstName(String fName) {
		put("firstName", fName);
	}
	
	/**
	 * Set the last name of the user.
	 * @param lName
	 */
	public void setLastName(String lName) {
		put("lastName", lName);
	}
	
	/**
	 * Set the email of the user.
	 * @param email
	 */
	public void setEmail(String email) {
		put("email", email);
	}
	
	/**
	 * Get the unique username of the user.
	 * @return String
	 */
	public String getUsername() {
		return (String)get("username");
	}

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
	
	/**
	 * Get the first name of the user.
	 * @return
	 */
	public String getFirstName() {
		return (String)get("firstName");
	}
	
	/**
	 * Get the last name of the user.
	 * @return
	 */
	public String getLastName() {
		return (String)get("lastName");
	}
	
	/**
	 * Get the email of the user.
	 * @return
	 */
	public String getEmail() {
		return (String)get("email");
	}
	
	public HashMap<String, Item> getFiles() {
		return files;
	}
	
	public void setFiles(HashMap<String, Item> files) {
		this.files = files;
	}
}
