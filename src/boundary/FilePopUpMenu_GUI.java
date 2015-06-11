package boundary;

import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.border.TitledBorder;

import common.Displayable;

public class FilePopUpMenu_GUI extends JPopupMenu implements Displayable {

	private static final long serialVersionUID = -6005336868368607973L;
	private JMenuItem menuItemUpdate;
	private JMenuItem menuItemDelete;
	private JMenuItem menuItemRead;
	
	public FilePopUpMenu_GUI(String title) {
		super(title);
		displayWindow();
	}
	
	@Override
	public void displayWindow() {
		setBackground(Color.WHITE);
	    TitledBorder labelBorder = BorderFactory.createTitledBorder(
	    		BorderFactory.createEmptyBorder(), getLabel(),
	            TitledBorder.CENTER, TitledBorder.ABOVE_TOP);
	    setBorder(BorderFactory.createCompoundBorder(getBorder(),
	            labelBorder));
	    addSeparator();
	    
	    menuItemRead = new JMenuItem("Read");
	    menuItemRead.setBackground(Color.WHITE);
	    add(menuItemRead);
	    
	    menuItemUpdate = new JMenuItem("Update");
	    menuItemUpdate.setBackground(Color.WHITE);
	    add(menuItemUpdate);
	    
	    menuItemDelete = new JMenuItem("Delete");
	    menuItemDelete.setBackground(Color.WHITE);
	    add(menuItemDelete);
	}
	
	
	public void registerMenuItemReadListener(ActionListener listener) {
		menuItemRead.addActionListener(listener);
	}
	
	public void registerMenuItemUpdateListener(ActionListener listener) {
		menuItemUpdate.addActionListener(listener);
	}
	
	public void registerMenuItemDeleteListener(ActionListener listener) {
		menuItemDelete.addActionListener(listener);
	}

	@Override
	public void showMessage(String str) {}

	@Override
	public void closeWindow() {}

}
