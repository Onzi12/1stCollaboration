package controller;

import java.io.IOException;

import client.Client;
import boundary.FileEdit_GUI;
import model.ItemFile;
import common.Boundary;
import common.Controller;
import common.Message;
import common.MessageType;

public class FileEditController extends Controller{
	
	private ItemFile file;
	private FileEdit_GUI gui;
	public FileEditController(ItemFile file) {
		super(file);
		this.file = file;
		gui = (FileEdit_GUI)super.gui;
		gui.setDescriptionText(file.getDescription());
		gui.setFilename(file.getName());
		gui.setPrivilege(file.getPrivilege());
	}
	
	@Override
	protected Boundary initBoundary() {
				return null;
	}
	
	@Override
	protected Boundary initBoundary(ItemFile file) {
		return new FileEdit_GUI(this);
	}

	public void btnCancelClicked() {
		gui.close();
		
	}

	public void btnSaveClicked() {
		try {
			
			if(file != null){
				file.setDescription(gui.getDescriptionText());
				file.setName(gui.getFilename());
				System.out.println(file.getName());
				System.out.println(file.getDescription());
				file.setPrivilege(gui.getPrivilege());
			}
			
		Message msg = new Message(file, MessageType.FILE_EDIT);
		Client.getInstance().sendMessage(msg, null); // TODO GIL: implement FileEditCallback
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		gui.close();
	}

}
