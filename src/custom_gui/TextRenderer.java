package custom_gui;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class TextRenderer implements ListCellRenderer<String>{

	
	public Component getListCellRendererComponent(JList<? extends String> list,
			String value, int index, boolean isSelected, boolean cellHasFocus) {
		// TODO Auto-generated method stub
		return null;
	}
//	@Override
//    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
//         Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
//         if (renderer instanceof JLabel && value instanceof CD) {
//             // Here value will be of the Type 'CD'
//             ((JLabel) renderer).setText(((CD) value).getName());

}
