package custom_gui;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import model.Item;
import model.ItemFolder;
import boundary.FileTreeModel;

@SuppressWarnings("serial")
public class MyBoxTree extends JTree {
	
	public DefaultMutableTreeNode getRoot() {
		DefaultMutableTreeNode root = (DefaultMutableTreeNode)getModel().getRoot();
		return root;
	}
	
	// Add a child by a selection path
	public DefaultMutableTreeNode addObject(Object child) {

	    DefaultMutableTreeNode parentNode = null;
	    TreePath parentPath = getSelectionPath();
		DefaultMutableTreeNode rootNode = getRoot();

	    if (parentPath == null) {
	        //There is no selection. Default to the root node.
	        parentNode = rootNode;
	    } else {
	        parentNode = (DefaultMutableTreeNode)parentPath.getLastPathComponent();
	    }

	    return addObject(parentNode, child, true);
	}
	
	// Add a child to a specific parent
	public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child, boolean shouldBeVisible) {

		DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);
		FileTreeModel model = (FileTreeModel)getModel();
		model.insertNodeInto(childNode, parent, parent.getChildCount());
		
		if (shouldBeVisible) {
			scrollPathToVisible(new TreePath(childNode.getPath()));
		}
		
		
		return childNode;
	}
	
	// Remove a child by a selection path
	public void removeObject() {
	    TreePath path = getSelectionPath();
	    if (path != null) {
			DefaultMutableTreeNode nodeToRemove = (DefaultMutableTreeNode)path.getLastPathComponent();	    
			FileTreeModel model = (FileTreeModel)getModel();
			model.removeNodeFromParent(nodeToRemove);
	    }
	}
	
	public void processTreeHierarchy(ItemFolder folder) {

		DefaultMutableTreeNode parent = folder.getTreeNode();
		
		for (Item item : folder.getFiles().values()) {
			
			if (item instanceof ItemFolder) {
				
				ItemFolder f = (ItemFolder)item;
				f.setTreeNode(addObject(parent, f, false)); 
				processTreeHierarchy(f);
			}
			
		}
		
	}
	
}
