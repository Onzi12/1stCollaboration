package model;

import java.io.Serializable;

import client.Client;

public abstract class Item implements Serializable {

	private static final long serialVersionUID = -3661349268417620657L;
	
	/** The name of the item */
	private String name;
	private String path;
	private Item parent;
	private int id;

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

	public Item getParent() {
		return parent;
	}
	
	public void setParent(Item item) {
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
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

}
