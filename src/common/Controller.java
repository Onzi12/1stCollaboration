package common;


import model.ItemFile;
import controller.NavigationManager;



public abstract class Controller{

	protected NavigationManager nav;
	protected Boundary gui;
	
	public Controller() {
		nav = NavigationManager.getInstance();
		gui = initBoundary();
		registerMoreListeners();
		gui.display();
	}
	public Controller(ItemFile file){
		nav = NavigationManager.getInstance();
		gui = initBoundary(file);
		registerMoreListeners();
		gui.display();
	}
	
	/** <<hook>>
	 *  Here Add Listeners for Objects other then buttons
	 */
	protected void registerMoreListeners() {
	}

	/** Create a Boundary for this Controller */ 
	protected abstract Boundary initBoundary();
	
	protected Boundary initBoundary(ItemFile file) { return null; }

	/** <<hook>>
	 *  Called when this Controller get the control again 
	 *  This happens after a pushController & popController subsequent calls
	 */
	public void updateBoundary() {
	}
	
	public Boundary getGui() {
			return gui;
	}
	
}
