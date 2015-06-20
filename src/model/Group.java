package model;


import java.util.TreeSet;

public class Group {
	
	private int groupID;
	private String groupName;
	private TreeSet<User> users;
	

	/**
	 * @return the users
	 */
	public TreeSet<User> getUsers() {
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
	public void setUsers(TreeSet<User> users) {
		this.users = users;
	}



	/**
	 * @return the name
	 */
	public String getName() {
		return groupName;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String groupName) {
		this.groupName = groupName;
	}
	
	public boolean equals(Object obj){
		return groupName.equals(((Group)obj).groupName );
	}

	/**
	 * @return the groupID
	 */
	public int getGroupID() {
		return groupID;
	}

	/**
	 * @param groupID the groupID to set
	 */
	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}
}
