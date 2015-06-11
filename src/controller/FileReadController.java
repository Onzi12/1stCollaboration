package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.ItemFile;
import boundary.FileRead_GUI;

public class FileReadController {

	private FileRead_GUI gui;
	
	public FileReadController(FileRead_GUI gui, ItemFile file) {
		this.gui = gui;		
		gui.setFilename(file.getName());
		gui.setLocation(file.getFullPath());
		gui.registerCloseListener(new BtnCloseActionListener());
	}
	
	private class BtnCloseActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			btnCancelClicked();
		}
	}
	
	private void btnCancelClicked() {
		gui.closeWindow();
	}
}
