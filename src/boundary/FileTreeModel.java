package boundary;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import model.ItemFolder;


public class FileTreeModel extends DefaultTreeModel {

	private static final long serialVersionUID = 1214116617410377448L;

	public FileTreeModel(TreeNode root) {
		super(root);
		
	}
	
	public void valueForPathChanged(TreePath path, Object newValue) {
		
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)path.getLastPathComponent();
		ItemFolder folder = (ItemFolder)node.getUserObject();
		folder.setName((String)newValue);
		nodeChanged(node);
		
	}
}
