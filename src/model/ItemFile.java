package model;

import java.util.Set;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.tree.DefaultMutableTreeNode;

import boundary.MyBox_GUI;
import controller.MyBoxController;
import controller.NavigationManager;



public class ItemFile extends Item {


	/**
	 * 
	 */
	private static final long serialVersionUID = -7663628153116863268L;
	private byte[] file;
	private String description;
	private Privilege privilege;
	private State state;
	private boolean isEdited;
	private User owner;
	private static Icon icon = new ImageIcon("file.png");
	//private Set<ItemFile> parentFolders;
	
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

	public State getState() {
		return state;
	}
	
	public void setState(State state) {
		this.state = state;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	/**
	 * @return the isEdited
	 */
	public boolean isEdited() {
		return isEdited;
	}
	
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