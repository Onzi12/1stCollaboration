package boundary;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;

public class FileTreeModelListenter implements TreeModelListener {

	@Override
	public void treeNodesChanged(TreeModelEvent e) {
		System.out.println("treeNodesChanged");
	}

	@Override
	public void treeNodesInserted(TreeModelEvent e) {
		System.out.println("treeNodesInserted");
	}

	@Override
	public void treeNodesRemoved(TreeModelEvent e) {
		System.out.println("treeNodesRemoved");
	}

	@Override
	public void treeStructureChanged(TreeModelEvent e) {
		System.out.println("treeStructureChanged");
	}

}
