package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.ItemFile;
import boundary.FilePopUpMenu_GUI;
import boundary.FileRead_GUI;
import boundary.FileUpdate_GUI;
import boundary.MyBox_GUI;

public class FilePopUpMenuController {
	
	private ItemFile file;
	private MyBoxController myboxController;
	
	public FilePopUpMenuController(MyBoxController myboxController, FilePopUpMenu_GUI gui, ItemFile file) {
		this.file = file;
		this.myboxController = myboxController;
		gui.registerMenuItemDeleteListener(new BtnDeleteFileActionListener());
		gui.registerMenuItemUpdateListener(new BtnUpdateFileActionListener());
		gui.registerMenuItemReadListener(new BtnReadFileActionListener());
	}
	
	private class BtnReadFileActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			btnReadClicked();
		}	
	}
	
	private class BtnDeleteFileActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
		}
	
	}
	
	private class BtnUpdateFileActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			btnUpdateClicked();				
		}	
	}
	
	private void btnUpdateClicked() {
		FileUpdate_GUI update = new FileUpdate_GUI(myboxController.getNavigationManager().getFrame(), false);
		new FileUpdateController(update, file);
		update.setVisible(true);
		((MyBox_GUI)myboxController.getGui()).getTable().clearSelection();
	}
	
	private void btnReadClicked() {
		FileRead_GUI read = new FileRead_GUI(myboxController.getNavigationManager().getFrame());
		new FileReadController(read, file);
		read.setVisible(true);
	}
	
}
