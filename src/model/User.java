package model;

import java.io.Serializable;
import java.util.Set;
import dao.UserDAO;

/**
 * The {@link User} class contains the details of a user in the application.
 */
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

	/**
	 * The user id.
	 */
	private int userID;
	
	/**
	 * The user name.
	 */
    private String userName;
    
    /**
     * The user password.
     */
    private String password;
    
    /**
     * The user {@link Status}.
     */
    private Status status;
    
    /**
     * States if the user is an administrator.
     */
    private boolean isAdmin;
    
    /**
     * The number of failed attempts to login.
     */
    private int counter;
    
    /**
     * The root folder for the user files.
     */
    private ItemFolder rootFolder;
    
    /**
     * The groups that the user is part of.
     */
    private Set<Group> groups;
    
    /**
     * The files of the user.
     */
    private Set<ItemFile> files;
	

    /**
     * Set the Groups that the user is part of.
     * @param userGroups
     */
	public void setGroups(Set<Group> userGroups) {
		groups = userGroups;
	}
	
	/**
	 * Get the groups that the user is part of.
	 * @return
	 */
	public Set<Group> getGroups() {
		return groups;
	}
	
	public int hashCode(){
		return userID;
	}
	
	/** 
	 * Set the user {@link Status}.
	 * @param status
	 */
	public void setStatus(Status status) {
		this.status = status;
	}
	
	/**
	 * Get the user {@link Status}.
	 * @return {@link Status}
	 */
	public Status getStatus() {
		return status;
	}
	
	/**
	 * Get the {@link Status} integer value.
	 * @return
	 */
	public int getStatusValue() {
		return status.getValue();
	}
	
	/**
	 * Get the number of the failed attempts to login.
	 * @return int
	 */
	public int getCounter() {
		return counter;
	}
	
	/**
	 * Set the number of failed attempts to login.
	 * @param counter
	 */
	public void setCounter(int counter){
		this.counter = counter;
	}
	
	/**
	 * Get the root folder of the user.
	 * @return {@link ItemFolder}
	 */
    public ItemFolder getRootFolder() {
    		return rootFolder;
    }

    /**
     * Set the root folder of the user.
     * @param root
     */
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
	 * Handle all the login of user login functionality<br>
	 * Check the user details against the Database
	 * @param userName - The user that is trying to log in
	 * @param enteredPassword - the user entered password
	 * @return User On Successful login: the object of the registered user<br>
	 * 		   Else - an exception is thrown
	 * @throws Exception With the appropriate error message
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
			throw new Exception(exception+"is blocked\nPlease contact the administrator!");
		
		//User already logged in?
		else if(user.getStatus() == Status.CONNECTED )
			throw new Exception(exception+ "is already connected!" );
		
		//enterdPassword is wrong?
		if( user.getPassword().equals(enteredPassword) == false ) 
			{	
				int counter = user.getCounter();
				++counter;
				user.setCounter(counter);
				
				//Block user
				if(counter == 3)
					{
					user.setStatus(Status.BLOCKED);
					user.setCounter(0);
					uDao.ObjectToDB(user);
					throw new Exception(exception+ "is blocked\n please contact the administrator!");
					}
				//Just update the the counter in DB
				else 
					{
					uDao.ObjectToDB(user);
					throw new Exception(exception+ "Password is incorrect!"+
								"\nCarefull, You have "+(3-counter)+" more Tries to Log in!" );
					}
			}
		
		//Success!!
		user.setCounter(0);
		user.setStatus(Status.CONNECTED);
		uDao.ObjectToDB(user);
		return user;
		
	}

	/**
	 * Get the isAdmin integer value.
	 * @return int
	 */
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
