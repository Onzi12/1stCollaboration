package boundary;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import model.Item;

/** 
 * Adds a file icons in the main MyBox window table
 */
public class FileTreeCellRenderer extends DefaultTreeCellRenderer {

	private static final long serialVersionUID = 2535842331897450564L;

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
//did something strange in debug
        if (value instanceof DefaultMutableTreeNode) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
            if (node.getUserObject() instanceof String) {                
            } else if (node.getUserObject() instanceof Item) {
            	Item item = (Item)  node.getUserObject();
            	setText(item.getName());
            }
        }

        return this;
    }
}
