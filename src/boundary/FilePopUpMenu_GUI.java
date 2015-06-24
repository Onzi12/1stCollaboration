package boundary;

import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.border.TitledBorder;

/**
 * Graphical User Interface that opens with a right click is performed on a file
 * @author Idan
 *
 */
@SuppressWarnings("serial")
public class FilePopUpMenu_GUI extends JPopupMenu /*implements Displayable*/ {

	/**
	 * Update File button
	 */
	private JMenuItem menuItemUpdate;
	
	/**
	 * Delete File Virtually button
	 */
	private JMenuItem menuItemDelete;
	
	/**
	 * Read File button
	 */
	private JMenuItem menuItemRead;
	
	/**
	 * Edit File button
	 */
	private JMenuItem menuItemEdit;
	
	/**
	 * Move Files from directory to directory button
	 */
	private JMenuItem menuItemMoveto;
	
	/** 
	 * Constructs the popup window
	 * @param title
	 */
	public FilePopUpMenu_GUI(String title) {
		super(title);
		draw();
	}
	
	/**
	 * Draws the popup window
	 */
	public void draw() {
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
	    
	    menuItemEdit = new JMenuItem("Edit");
	    menuItemEdit.setBackground(Color.WHITE);
	    add(menuItemEdit);
	    
	    menuItemMoveto = new JMenuItem("Move To..");
	    menuItemMoveto.setBackground(Color.WHITE);
	    add(menuItemMoveto);
	    
	}
	
	/**
	 * registers Read button Listener
	 * @param listener
	 */
	public void registerMenuItemReadListener(ActionListener listener) {
		menuItemRead.addActionListener(listener);
	}
	
	/**
	 * registers Update button Listener
	 * @param listener
	 */
	public void registerMenuItemUpdateListener(ActionListener listener) {
		menuItemUpdate.addActionListener(listener);
	}
	
	/**
	 * registers Delete Virtually button Listener
	 * @param listener
	 */
	public void registerMenuItemDeleteListener(ActionListener listener) {
		menuItemDelete.addActionListener(listener);
	}
	
	/**
	 * registers Edit button Listener
	 * @param listener
	 */
	public void registerMenuItemEditListener(ActionListener listener) {
		menuItemEdit.addActionListener(listener);
	}
	
	/**
	 * registers MoveTo button Listener
	 * @param listener
	 */
	public void registerMenuItemMovetoListener(ActionListener listener) {
		menuItemMoveto.addActionListener(listener);
	}
}
