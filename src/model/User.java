package model;

import java.io.Serializable;
import java.util.Set;
import dao.UserDAO;

public class User implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 8687482046790563981L;

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

	
	private int userID;
    private String userName;
    private String password;
    private Status status;
    private boolean isAdmin;
    private int counter;
    private ItemFolder rootFolder;
    private Set<Group> groups;
    private Set<ItemFile> files;
	

	public void setGroups(Set<Group> userGroups) {
		groups = userGroups;
	}
	
	public Set<Group> getGroups() {
		return groups;
	}
	
	public int hashCode(){
		return userID;
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
	 * Set the userName for the user (Unique).
	 * @param username
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	/**
	 * Set the password for the user.
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	

	
	
	/**
	 * Get the unique userName of the user.
	 * @return the user userName 
	 */
	public String getUserName() {
		return userName;
	}
	


	/**
	 * Get the password of the user.
	 * @return the user password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Get the unique id of the user.
	 * @return the user Database id
	 */
	public int getID() {
		return userID;
	}
	
	/**
	 * Set the unique id of the user.
	 * @param userID the user Database id
	 */
	public void setID(int userID) {
		this.userID = userID;
	}
		

	/**
	 * Set the isAdmin value of the user
	 * @param admin 
	 */
	public void setAdmin(boolean admin) {
		this.isAdmin = admin;
	}
	
	/**
	 * Check if user is admin
	 * @return true - if user is admin <br>
	 * 		   false - user is not an admin
	 */
	public boolean isAdmin() {
		return isAdmin;
	}
	
	
	public boolean equals(Object obj) {
		
		 User user = (User)obj;
		 return user.getID() == getID();
		
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
				if(counter == 3)
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
		user.setCounter(0);
		user.setStatus(Status.CONNECTED);
		uDao.ObjectToDB(user);
		return user;
		
	}

	public int getIsAdminValue() {
		return isAdmin() ? 1 : 0;
	}

	/**
	 * @return the files
	 */
	public Set<ItemFile> getFiles() {
		return files;
	}

	/**
	 * @param files the files to set
	 */
	public void setFiles(Set<ItemFile> files) {
		this.files = files;
	}

}
