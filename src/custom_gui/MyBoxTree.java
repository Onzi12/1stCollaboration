package custom_gui;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Set;

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
	public DefaultMutableTreeNode addObject(ItemFolder child, boolean shouldBeVisible) {

		DefaultMutableTreeNode parentNode = null;
	    TreePath parentPath = getSelectionPath();
	    DefaultMutableTreeNode rootNode = getRoot();
	    
	    if (parentPath == null) {
	        //There is no selection. Default to the root node.
	        parentNode = rootNode;
	    } else {
	        parentNode = (DefaultMutableTreeNode)getLastSelectedPathComponent();
	    }

	    return addObject(parentNode, child, shouldBeVisible);
	}
	
	// Add a child to a specific parent
	public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, ItemFolder child, boolean shouldBeVisible) {

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
		
		Set<Item> items = folder.getContents();
		for ( Item item : items ) {
			if (item instanceof ItemFolder) {
				
				ItemFolder f = (ItemFolder)item;
				f.setTreeNode(addObject(parent, f, false)); 
				processTreeHierarchy(f);
				
			}
		}
		
	}
	
	public void showFolder(ItemFolder folder) {
		DefaultMutableTreeNode node = findNode(folder);
        if( node != null ) {
            TreePath path = new TreePath(node.getPath());
            setSelectionPath(path);
            scrollPathToVisible(path);
        }
	}
	
    private final DefaultMutableTreeNode findNode(ItemFolder searchFolder) {

        ArrayList<DefaultMutableTreeNode> searchNodes = getSearchNodes((DefaultMutableTreeNode)getModel().getRoot());
        DefaultMutableTreeNode foundNode = null;

        for(int index = 0; index < searchNodes.size(); index++) {    
        	ItemFolder folder = (ItemFolder)searchNodes.get(index).getUserObject();
            if(folder.equals(searchFolder)) {
                foundNode = searchNodes.get(index);
                break;
            }
        }

        return foundNode;
    }   

    private final ArrayList<DefaultMutableTreeNode> getSearchNodes(DefaultMutableTreeNode root) {
    	ArrayList<DefaultMutableTreeNode> searchNodes = new ArrayList<DefaultMutableTreeNode>();
        Enumeration<?> e = root.preorderEnumeration();
        while(e.hasMoreElements()) {
            searchNodes.add((DefaultMutableTreeNode)e.nextElement());
        }
        return searchNodes;
    }
	
}
