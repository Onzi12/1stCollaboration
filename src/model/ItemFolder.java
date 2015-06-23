package model;

import java.util.Set;
import java.util.function.Predicate;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.tree.DefaultMutableTreeNode;

public class ItemFolder extends Item {


	/**
	 * 
	 */
	private static final long serialVersionUID = -9179731707857058359L;
	private Set<Item> contents;
	private DefaultMutableTreeNode treeNode;
	private static Icon icon = new ImageIcon("Folder.png");
	
	
	public void addItem(Item item) {
			contents.add(item);
		}

	public String getFullPath() {
		if( this.getParentID() == 0)
			return getName();
		else 
			return getPath()+"/";
	}

	public void removeItem(Item item) {
		if( contents.remove(item) == false )
			System.err.println("ERROR removeItem: the item was not in the folder!");
	}

	
	public boolean equals(Object obj) {
		if(obj instanceof ItemFolder)
			return this.getID() == ((ItemFolder)obj).getID();
		return false;
	}
	
	public DefaultMutableTreeNode getTreeNode() {
		return treeNode;
	}

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


	public void setContents(Set<Item> items) {
		this.contents = items;
	}
	
	
}
