package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import model.ItemFile;
import boundary.FileUpdate_GUI;
import client.Client;
import common.Message;
import common.MessageType;

public class FileUpdateController {

	private FileUpdate_GUI gui;
	private ItemFile file;
	
	public FileUpdateController(FileUpdate_GUI gui, ItemFile file) {
		this.gui = gui;		
		this.file = file;
		if (gui.isCreateFile()) {
			gui.setFilename("");
			gui.setLocation("");
		} else {
			gui.setFilename(file.getName());
			gui.setLocation(file.getFullPath());
		}
		gui.registerSaveListener(new BtnSaveActionListener());
		gui.registerCancelListener(new BtnCancelActionListener());
	}
	
	private class BtnSaveActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			btnSaveClicked();
		}
	}
	
	private class BtnCancelActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			btnCancelClicked();
		}
	}
	
	private void btnSaveClicked() {
		
		if (gui.isCreateFile()) {
			
			ItemFile newFile = new ItemFile();
			newFile.setName(gui.getFilenameText());
//			newFile.setLocation(gui.getLocationText());
			
			try {
				Message msg = new Message(newFile, MessageType.ADD_FILE);
				Client.getInstance().sendMessage(msg);
			} catch (IOException e1) {
				gui.showMessage("Exception: " + e1.getMessage());
			}
			
		} else {
			
			ItemFile newFile = new ItemFile(file.getID());
			newFile.setName(gui.getFilenameText());
//			newFile.setLocation(gui.getLocationText());
			
			try {
				Message msg = new Message(newFile, MessageType.UPDATE_FILE);
				Client.getInstance().sendMessage(msg);
			} catch (IOException e1) {
				gui.showMessage("Exception: " + e1.getMessage());
			}
			
		}
		
		gui.closeWindow();
	}
	
	private void btnCancelClicked() {
		gui.closeWindow();
	}
	
}
