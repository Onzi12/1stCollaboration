package model;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.tree.DefaultMutableTreeNode;

import boundary.MyBox_GUI;
import controller.MyBoxController;
import controller.NavigationManager;

/**
 * The {@link ItemFile} class extends {@link Item} and adds specific file properties.
 */
public class ItemFile extends Item {


	/**
	 * 
	 */
	private static final long serialVersionUID = -7663628153116863268L;
	/**
	 * A byte representation of the physical file.
	 */
	private byte[] file;
	
	/**
	 * The description of the file.
	 */
	private String description;
	
	/**
	 * The {@link Privilege} of the file.
	 */
	private Privilege privilege;
	
	/**
	 * The current {@link State} of the file 
	 */
	private State state;
	
	/**
	 * Is the file being edited.
	 */
	private boolean isEdited;
	
	/**
	 * The {@link User} that own's the file.
	 */
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
	
	public enum State {
		NORMAL(0) ,ABANDONED(1) , DELETED(2);

	    private final int value;
	    
	    private State(int value) {
	        this.value = value;
	    }

	    public int getValue() {
	        return value;
	    }
	}
	
	/**
	 * Get the byte array of the physical file.
	 * @return byte[]
	 */
	public byte[] getFile() {
		return file;
	}

	/**
	 * Set the byte array of the physical file.
	 * @param file - byte array.
	 */
	public void setFile(byte[] file) {
		this.file = file;
	}
	
	/**
	 * Set the description of the file.
	 * @param description - String
	 */
	public void setDescription(String description){
		this.description = description;
	}
	
	/**
	 * Get the description of the file.
	 * @return String
	 */
	public String getDescription(){
			return description;
	}
	
	/**
	 * Set the {@link Privilege} of the file.
	 * @param privilege
	 */
	public void setPrivilege(Privilege privilege) {
		this.privilege = privilege;
	}
	
	/**
	 * Get the {@link Privilege} of the file.
	 * @return
	 */
	public Privilege getPrivilege(){
		return privilege;
	}

	/**
	 * Get the {@link State} of the file.
	 * @return
	 */
	public State getState() {
		return state;
	}
	
	/**
	 * Set the {@link State} of the file.
	 * @param state
	 */
	public void setState(State state) {
		this.state = state;
	}

	/**
	 * Get the owner of the file.
	 * @return {@link User}
	 */
	public User getOwner() {
		return owner;
	}

	/**
	 * Set the owner of the file.
	 * @param owner
	 */
	public void setOwner(User owner) {
		this.owner = owner;
	}

	/**
	 * @return the isEdited
	 */
	public boolean isEdited() {
		return isEdited;
	}
	
	/**
	 * Get the isEdited integer value.
	 * @return {@link Integer}
	 */
	public int getIsEditedValue() {
		return isEdited ? 1 : 0 ;
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

	@Override
	public String getFullPath() {
		MyBoxController control = (MyBoxController) NavigationManager.getInstance().getCurrentController();
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)((MyBox_GUI)control.getGui()).getTree().getLastSelectedPathComponent();
		ItemFolder parent = (ItemFolder) node.getUserObject();
		return parent.getFullPath()+getName();
	}
	
}