package controller;

import java.io.IOException;

import callback.EditFileCallback;
import callback.FinishedEditingFileCallback;
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
		this.file = file;
		gui = (FileEdit_GUI)super.gui;
		if (file.getOwner().getID() != Client.getInstance().getUser().getID())
			gui.setBtnManageGroupsAccess(false);
		System.out.println("file owner "+file.getOwner().getID());
		System.out.println("current user: "+ Client.getInstance().getUser().getID());
		gui.setDescriptionText(file.getDescription());
		gui.setFilename(file.getName());
		gui.setPrivilege(file.getPrivilege().getValue());
		System.out.println("teh privilege is :"+file.getPrivilege().getValue());
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
		close();
		
	}
	
	public void close() {
		gui.close();
		
		try {
			Message setEditable = new Message(file, MessageType.FINISHED_EDITING_FILE);
			Client.getInstance().sendMessage(setEditable, new FinishedEditingFileCallback() {
				
				@Override
				public void finishedEditingFile(MyBoxException exception) {
					if (exception == null) {
						
					} else {
						gui.showMessage(exception.getMessage());
					}
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void btnSaveClicked() {
		try {
			
			if(file != null){
				file.setDescription(gui.getDescriptionText());
				file.setName(gui.getFilename());
				System.out.println(file.getName());
				System.out.println(file.getDescription());
				file.setPrivilege(gui.getPrivilege());
				file.setUserID(Client.getInstance().getUser().getID());
				
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
		
		close();
	}

	public void btnManageGroupsAccessClicked() {
		new FileGroupsSelectController(file);
		
	}

}
