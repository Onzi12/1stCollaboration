package model;

import java.io.Serializable;

import client.Client;

public class Item implements Serializable {

	private static final long serialVersionUID = -3661349268417620657L;
	
	/** The name of the item */
	private String name;
	
	/** The uniqe id of the item */
	private int id;
	
	private int folderID;
	
	/**
	 * Constructs an instance of an Item.
	 * @param id - the uniqe id of the item.
	 */
	public Item(int id) {
		this.id = id;
	}
	
	/**
	 * Constructs an instance of an Item.
	 */
	public Item() {}

	/**
	 * Get the name of the item.
	 * @return String
	 */
	public String getName() {
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
	 * Get the uniqe id of the item.
	 * @return int
	 */
	public int getID() {
		return id;
	}
	
	/**
	 * Set the uniqe id of the item
	 * @param id
	 */
	public void setID(int id) {
		this.id = id;
	}
	
	/**
	 * Get a string representation of the uniqe id.
	 * @return String
	 */
	public String getStringID() {
		return Integer.toString(getID());
	}
	
	public ItemFolder getFolder() {
		User user = Client.getInstance().getUser();
		ItemFolder fo = (ItemFolder)(user.getFiles().get("folder" + Integer.toString(folderID)));
		return fo;
	}
	
	public void setFolder(int folderID) {
		this.folderID = folderID;
	}
	
	/**
	 * Get the path to the location of the item.
	 * @return String
	 */
	public String getFullPath() {
		
		Item item = getFolder();
		String path = "";
		
		if (item != null) {
			
			 path += item.getFullPath() + "/";
			
		} 
		
		path += getName();
		
		return path;
	}

}
