package common;

import java.awt.event.ActionListener;

import javax.swing.JPanel;

import common.Displayable;
import controller.NavigationManager;



public abstract class Controller{

	private NavigationManager nav;
	private Boundary panel;
	
	public Controller() {
		initBoundary();
		
	}
	
	
	protected abstract void initBoundary();


	public NavigationManager getNavigationManager() {
		return nav;
	}
	
	public void setNavigationManager(NavigationManager nav) {
		this.nav = nav;
	}

	public Boundary getPanel() {
		return panel;
	}
	
	public abstract void viewWillAppear();
}
