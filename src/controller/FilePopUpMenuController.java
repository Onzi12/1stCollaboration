package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.ItemFile;
import boundary.FilePopUpMenu_GUI;
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
	private class btnEditFileActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			btnEditClicked();
			
		}
		
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
	
	public void btnUpdateClicked() {

		new FileUpdateController(file);
		((MyBox_GUI)myboxController.getGui()).getTable().clearSelection();
	}
	
	public void btnEditClicked() {
		 new FileEditController(file);
		 ((MyBox_GUI)myboxController.getGui()).getTable().clearSelection();
	}

	public void btnReadClicked() {
		new FileReadController(file);
	}
	
}
