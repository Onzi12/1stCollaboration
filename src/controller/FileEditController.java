package controller;

import java.io.IOException;

import callback.EditFileCallback;
import client.Client;
import boundary.FileEdit_GUI;
import model.ItemFile;
import common.Boundary;
import common.Controller;
import common.Message;
import common.MessageType;
import common.MyBoxException;

public class FileEditController extends Controller{
	
	private ItemFile file;
	private FileEdit_GUI gui;
	public FileEditController(ItemFile file) {
		super(file);
		System.out.println("fileid : " + file.getID());
		this.file = new ItemFile(file.getID());
		this.file.setFolder(file.getFolderID());
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
				
				System.out.println("send fileid : " + file.getID());

				
				Message msg = new Message(file, MessageType.FILE_EDIT);
				Client.getInstance().sendMessage(msg, new EditFileCallback() {
					
					@Override
					protected void done(ItemFile file, MyBoxException exception) {
						MyBoxController controller = (MyBoxController)NavigationManager.getInstance().getCurrentController();
						controller.handleEditFileCallback(file, exception);
					}
				}); 
				
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		gui.close();
	}

}
