package custom_gui;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;

import model.ItemFolder;

public class FolderTreeCellEditor extends DefaultTreeCellEditor {

    public FolderTreeCellEditor(JTree tree, DefaultTreeCellRenderer renderer) {
        super(tree, renderer);
    }

    @Override
    public Object getCellEditorValue() {
        String value = (String) super.getCellEditorValue();
        ItemFolder folder = new ItemFolder();
        folder.setName(value);
        return folder;
    }
    
}
