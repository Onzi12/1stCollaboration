package model;

import java.io.Serializable;

import javax.swing.Icon;

/**
 * {@link Item} is an abstract class that represents the basic data structure for a system file in the application.
 *
 */
public abstract class Item implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -7984889302443602233L;
	/** The name of the item */
	private String name;
	/** String representation of the item's path */
	private String path="";
	/** The id of the parent item (ItemFolder) of this item */
	private int parentID;
	/** The id of the item */
	private int id;
	/** The id of the client that has the item */
	private int userID;


	/**
	 * Get the name of the item.
	 * @return String
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Get the path of the file.
	 * @return
	 */
	 public String getPath() {
		 return path;
	 }
	public void concateToPath(String item) {
		path = path.concat(item);
	}

	/**
	 * Get the parent id of the item.
	 * @return integer
	 */
	public int getParentID() {
		return parentID;
	}
	
	public int hashCode() {
		return id;
	}
	/**
	 * Set the id of the parent of this item.
	 * @param itemID
	 */
	public void setParentID(int itemID) {
		parentID = itemID;
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
	public abstract String getFullPath();
	
	public void setPath(String path){
		this.path = path;
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
