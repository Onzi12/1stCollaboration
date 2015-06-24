package common;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import model.ItemFile;
import boundary.AppFrame;

/**
 * The {@link JDialogBoundary} class is an abstract class that all the dialogs in the application extend.
 * Holds all the common functionality for dialogs. 
 */
@SuppressWarnings("serial")
public abstract class JDialogBoundary extends JDialog implements Boundary{
	
	/**
	 * A reference to the controller.
	 */
	protected Controller controller;
	
	/**
	 * Constructs the {@link JDialogBoundary}.
	 * @param controller
	 */
	public JDialogBoundary(Controller controller)
	{
		super(AppFrame.getInstance());
		setResizable(false);
		this.controller = controller;
		draw();	
		registerListeners();
	}
	
	/**
	 * Constructs the {@link JDialogBoundary} with {@link ItemFile}.
	 * @param controller
	 * @param file
	 */
	public JDialogBoundary(Controller controller,ItemFile file)
	{
		super(AppFrame.getInstance());
		setResizable(false);
		this.controller = controller;
		draw(file);	
		registerListeners();
	}
	
	/**
	 * Method that when overridden is used to draw the UI
	 */
	public abstract void draw();
	
	/**
	 * Show the dialog.
	 */
	public void display() {
		this.setVisible(true);
	}
	
	/**
	 * Draw the GUI.
	 * @param file
	 */
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
	
	/**
	 * Register all needed listeners.
	 */
	public abstract void registerListeners();
}
