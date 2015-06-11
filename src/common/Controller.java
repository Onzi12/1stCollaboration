package common;

import java.awt.event.ActionListener;

import javax.swing.JPanel;


import controller.NavigationManager;



public abstract class Controller{

	protected NavigationManager nav;
	protected Boundary gui;
	
	public Controller() {
		nav = NavigationManager.getInstance();
		gui = initBoundary();
		gui.display();
		if(gui instanceof JPanelBoundary)
			nav.replaceController(this);
		//else  (maybe needed for JDialogBoundary)
			
	}
	
	/** Create a Boundary for this Controller */ 
	protected abstract Boundary initBoundary();

	public void updateBoundary() {
		
	}
	
	public JPanelBoundary getGui() {
			return (JPanelBoundary)gui;
	}
	
	//public abstract void viewWillAppear();
}
