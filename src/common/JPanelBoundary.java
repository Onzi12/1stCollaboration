package common;


import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.CreateAccountController;
import controller.NavigationManager;

@SuppressWarnings("serial")
public abstract class JPanelBoundary extends JPanel implements Boundary{
	
	protected Controller controller;
	
	public JPanelBoundary(Controller controller)
	{
		this.controller = controller;
		draw();
		registerListeners();
	}
	
	
	/**
	 * Method that when overridden is used to draw the UI
	 */
	public abstract void draw();
	
	
	public void display() {
//		if(controller instanceof CreateAccountController )  //add more instanceof checks
//			NavigationManager.getInstance().pushController(controller);
//		else
			NavigationManager.getInstance().replaceController(controller);
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
	public void close(){}
	
	public abstract void registerListeners();
}
