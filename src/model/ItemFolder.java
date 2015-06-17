package model;

import java.util.HashMap;

import javax.swing.tree.DefaultMutableTreeNode;

public class ItemFolder extends Item {

	private static final long serialVersionUID = -1040876964303827361L;
	private HashMap<String, Item> files = new HashMap<String, Item>();
	private DefaultMutableTreeNode treeNode;
	
	public ItemFolder(int id) {
		super(id);
	}
	
	public ItemFolder() {}
	
	public HashMap<String, Item> getFiles() {
		return files;
	}


	public void addFile(Item item) {
		if (item instanceof ItemFile) {
			files.put("file" + item.getStringID(), item);
		} else if (item instanceof ItemFolder) {
			files.put("folder" + item.getStringID(), item);
		}
	}
	
	public void removeFile(Item item) {
		if (item instanceof ItemFile) {
			files.remove("file" + item.getStringID());
		} else if (item instanceof ItemFolder) {
			files.remove("folder" + item.getStringID());
		}
	}

	public DefaultMutableTreeNode getTreeNode() {
		return treeNode;
	}

	public void setTreeNode(DefaultMutableTreeNode treeNode) {
		this.treeNode = treeNode;
	}
	
	
}
