package common;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import model.ItemFile;
import boundary.AppFrame;

@SuppressWarnings("serial")
public abstract class JDialogBoundary extends JDialog implements Boundary{
	
	
	protected Controller controller;
	
	public JDialogBoundary(Controller controller)
	{
		super(AppFrame.getInstance());
		this.controller = controller;
		draw();	
		registerListeners();
	}
	
	public JDialogBoundary(Controller controller,ItemFile file)
	{
		super(AppFrame.getInstance());
		this.controller = controller;
		draw(file);	
		registerListeners();
	}
	
	/**
	 * Method that when overridden is used to draw the UI
	 */
	public abstract void draw();
	
	public void display() {
		this.setVisible(true);
	}
	
	public void draw(ItemFile file){}
	
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
	public void close() {
		setVisible(false);
		dispose();
	}
	
	public abstract void registerListeners();
}
