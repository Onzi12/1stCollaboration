package common;

import javax.swing.JOptionPane;

public interface Boundary {

	/**
	 * Method that when overridden is used to draw the UI
	 */
	public void draw();
	
	public void display();
	
	
	public abstract void update();
	public abstract void registerListeners();
	/**
	 * Display messages onto the GUI
	 */
	public void showMessage(String str);
	
	/**
	 * Method that when overridden is used to close the displayed window
	 */
	public abstract void closeWindow();
}
