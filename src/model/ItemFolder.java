package model;

import java.util.Set;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * The {@link ItemFolder} class extends {@link Item} and adds specific folder properties.
 *
 */
public class ItemFolder extends Item {


	/**
	 * 
	 */
	private static final long serialVersionUID = -9179731707857058359L;
	
	/**
	 * The contents of the folder.
	 */
	private Set<Item> contents;
	
	/**
	 * A reference to the tree node.
	 */
	private DefaultMutableTreeNode treeNode;
	private static Icon icon = new ImageIcon("Folder.png");
	
	/**
	 * Add an {@link Item} to the contents of the folder.
	 * @param item
	 */
	public void addItem(Item item) {
			contents.add(item);
		}

	@Override
	public String getFullPath() {
		if( this.getParentID() == 0)
			return getName();
		else 
			return getPath()+"/";
	}

	/**
	 * Remove {@link Item} from the folder's contents.
	 * @param item
	 */
	public void removeItem(Item item) {
		if( contents.remove(item) == false )
			System.err.println("ERROR removeItem: the item was not in the folder!");
	}
	
	
	public boolean equals(Object obj) {
		if(obj instanceof ItemFolder)
			return this.getID() == ((ItemFolder)obj).getID();
		return false;
	}
	
	/**
	 * Get the tree node of the folder.
	 * @return {@link DefaultMutableTreeNode}
	 */
	public DefaultMutableTreeNode getTreeNode() {
		return treeNode;
	}

	/**
	 * Set the tree node of the folder.
	 * @param treeNode
	 */
	public void setTreeNode(DefaultMutableTreeNode treeNode) {
		this.treeNode = treeNode;
	}


	/**
	 * @return the folder icon
	 */
	@Override
	public Icon getIcon() {
		return icon;
	}

	/**
	 * Get folder contents.
	 * @return
	 */
	public Set<Item> getContents() {
		return contents;
	}


	/**
	 * Set the contents of the folder.
	 * @param items
	 */
	public void setContents(Set<Item> items) {
		this.contents = items;
	}
	
	
}
