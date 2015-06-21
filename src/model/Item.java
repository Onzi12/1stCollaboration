package model;

import java.io.Serializable;

import javax.swing.Icon;

import client.Client;

public abstract class Item implements Serializable {

	private static final long serialVersionUID = -3661349268417620657L;
	
	/** The name of the item */
	private String name;
	private String path;
	private ItemFolder parent;
	private int id;
	private int userID;


	/**
	 * Get the name of the item.
	 * @return String
	 */
	public String getName() {
		return name;
	}
	
	public void concateToPath(String item) {
		path = path.concat(item);
	}

	public ItemFolder getParent() {
		return parent;
	}
	
	public void setParent(ItemFolder item) {
		parent = item;
	}
	
	
	@Override
	public String toString() { 
	return name;
	}

	/**
	 * Set the name of the item.
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * Get the path to the location of the item.
	 * @return String
	 */
	public String getFullPath() {
		return path+"/"+name;
	}

	/**
	 * @return the id
	 */
	public int getID() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setID(int id) {
		this.id = id;
	}

	/**
	* @return the Item icon
	*/
	public abstract Icon getIcon();

	/**
	 * @return the User id
	 */
	public int getUserID() {
		return userID;
	}

	/**
	 * Set the User id
	 * @param userID
	 */
	public void setUserID(int userID) {
		this.userID = userID;
	}


}
