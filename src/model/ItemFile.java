package model;

import javax.swing.Icon;
import javax.swing.ImageIcon;



public class ItemFile extends Item {

	private static final long serialVersionUID = 243683429589199787L;
	
	private byte[] file;
	private String description;
	private Privilege privilege;
	private boolean isDeleted;
	private boolean isEdited;
	private User owner;
	private static Icon icon = new ImageIcon("file.png");
	
	public enum Privilege {
		PRIVATE(0) ,GROUP(1) , PUBLIC(2);

	    private final int value;
	    
	    private Privilege(int value) {
	        this.value = value;
	    }

	    public int getValue() {
	        return value;
	    }
	}
	
	

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public String getDescription(){
			return description;
	}
	public void setPrivilege(Privilege privilege) {
		this.privilege = privilege;
	}
	
	public Privilege getPrivilege(){
		return privilege;
	}


	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	/**
	 * @return the isDeleted
	 */
	public boolean isDeleted() {
		return isDeleted;
	}

	/**
	 * @param isDeleted the isDeleted to set
	 */
	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	/**
	 * @return the isEdited
	 */
	public boolean isEdited() {
		return isEdited;
	}

	/**
	 * @param isEdited the isEdited to set
	 */
	public void setIsEdited(boolean isEdited) {
		this.isEdited = isEdited;
	}

	/**
	 * @return the file icon
	 */
	@Override
	public Icon getIcon() {
		return icon;
	}

	/**
	 * @param icon the icon to set
	 */
	public static void setIcon(ImageIcon icon) {
		ItemFile.icon = icon;
	}	
	
}