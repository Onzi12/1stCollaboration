package common;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public abstract class Boundary extends JPanel{
	
	Controller controller;
	
	public Boundary(Controller controller)
	{
		this.controller = controller;
		draw();
		registerListeners();
		
	}
	/**
	 * Method that when overridden is used to draw the UI
	 */
	public abstract void draw();
	
	public abstract void display();
	
	public abstract void update();
	public abstract void registerListeners();
	/**
	 * Display messages onto the GUI
	 */
	public void showMessage(String str)
	{ 
		JOptionPane.showMessageDialog(this, str, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Method that when overridden is used to close the displayed window
	 */
	public abstract void closeWindow();
}
