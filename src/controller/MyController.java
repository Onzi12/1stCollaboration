package controller;

import java.awt.event.ActionListener;

import javax.swing.JPanel;



public abstract class MyController implements ActionListener {

	private NavigationManager nav;
	private JPanel panel;
	
	public MyController(JPanel panel) {
		this.panel = panel;
	}
	
	public NavigationManager getNavigationManager() {
		return nav;
	}
	
	public void setNavigationManager(NavigationManager nav) {
		this.nav = nav;
	}

	public JPanel getPanel() {
		return panel;
	}
	
	public abstract void viewWillAppear();
}
