package custom_gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class ImageRenderer extends DefaultTableCellRenderer implements TableCellRenderer{

	private static final long serialVersionUID = 3528335619052958044L;
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		setText((String) value);
		setIcon(UIManager.getIcon("FileView.fileIcon"));
	    Color normal = new Color(table.getBackground().getRGB());
		if (isSelected) {
			setBackground(table.getSelectionBackground());
		} else {
			setBackground(normal);
		}
		return this;
	}
}