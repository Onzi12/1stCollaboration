package common;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public abstract class JPanelBoundary extends JPanel implements Boundary{
	
	protected Controller controller;
	
	public JPanelBoundary(Controller controller)
	{
		this.controller = controller;
		draw();
		registerListeners();
		display();
		
	}
	/**
	 * Method that when overridden is used to draw the UI
	 */
	public abstract void draw();
	
	public void display() {
		this.setVisible(true);
	}
	
	
	public void update(){}

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
	public void closeWindow(){}
}
