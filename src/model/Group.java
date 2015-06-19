package model;

import java.util.HashSet;

public class Group {
	private String name;
	private String id;
	private HashSet<User> users;
	
	public Group(String name, String id) {
		this.setName(name);
		this.setId(id);
	}

	/**
	 * @return the users
	 */
	public HashSet<User> getUsers() {
		return users;
	}

	/**
	 * 
	 * @param user the user to add to group
	 * @return true - if this group did not already contain the specified user
	 */
	public boolean addUser(User user) {
		return users.add(user);
	}
	
	/**
	 * 
	 * @param user the user to remove from the group
	 * @return true if the group contained the specified user
	 */
	public boolean removeUser(User user) {
		return users.remove(user);
	}
	
	/**
	 * @param users the users to set
	 */
	public void setUsers(HashSet<User> users) {
		this.users = users;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean equals(Object obj){
		return name.equals(((Group)obj).name );
	}
}
