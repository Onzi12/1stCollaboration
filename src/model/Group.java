package model;


import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

public class Group implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -688004859034712745L;
	private int groupID;
	private String groupName;
	private Set<User> users;
	

	/**
	 * @return the users
	 */
	public Set<User> getUsers() {
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

	public int hashCode(){
		return groupID;
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
		if(obj instanceof Group)
			{
			Group g = (Group)obj;
			return groupID  == g.groupID ;
			}
		
		return false;
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
	
	public String toString(){
		return groupName;
	}
}
