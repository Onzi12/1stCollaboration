package controller;

import java.io.IOException;

import model.ItemFile;
import boundary.FileUpdate_GUI;
import client.Client;

import common.Boundary;
import common.Controller;
import common.Message;
import common.MessageType;

public class FileUpdateController extends Controller{

	FileUpdate_GUI gui;
	private ItemFile file;
	
	public FileUpdateController(ItemFile file) {
		this.gui = (FileUpdate_GUI)super.gui;
		this.file = file;
		
		if (file == null) {
			gui.setSelected(false);
			gui.setFilename("");
			gui.setLocation("");
		} else {
			gui.setSelected(true);
			gui.setFilename(file.getName());
			gui.setLocation(file.getFullPath());
		}

	}
	
	
	
	public void btnSaveClicked() {
		
		if (file == null) {
			
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
		
		gui.close();
	}
	
	public void btnCancelClicked() {
		gui.close();
	}

	@Override
	protected Boundary initBoundary() {
		return new FileUpdate_GUI(this);
	}


	public void btnPathClicked() {
		// TODO Auto-generated method stub
		
	}

	public void btnSaveLocationClicked() {
		// TODO Auto-generated method stub
		
	}
	
}
