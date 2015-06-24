package controller;

import java.io.IOException;
import java.util.Set;

import model.ItemFile;
import boundary.FileRestore_GUI;
import callback.FileRestoreCallback;
import callback.GetRestoreFilesCallback;
import client.Client;

import common.Boundary;
import common.Controller;
import common.Message;
import common.MessageType;
import common.MyBoxException;

public class FileRestoreController extends Controller {

	private FileRestore_GUI gui;
	
	@Override
	protected Boundary initBoundary() {
		return new FileRestore_GUI(this);
	}

	public void btnRestoreClicked() {
		
		Message msg = new Message(gui.getSelectedFile(), MessageType.RESTORE_FILE);
		try {
			Client.getInstance().sendMessage(msg, new FileRestoreCallback() {
				
				@Override
				protected void done(MyBoxException exception) {
					MyBoxController control = (MyBoxController) nav.getCurrentController();
					control.handleRestoreFileCallback(exception);
					gui.close();
				}
				
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void btnCloseClicked() {
		gui.close();		
	}
	
	public FileRestoreController() {
		
		gui = (FileRestore_GUI)super.gui;
		try {
			Message msg = new Message(Client.getInstance().getUser(), MessageType.GET_RESTORE_FILES);
			Client.getInstance().sendMessage(msg, new GetRestoreFilesCallback() {
				
				@Override
				protected void done(Set<ItemFile> files, MyBoxException exception) {
					
					if (exception == null) {
						for (ItemFile file : files) {
							gui.addListValue(file);
						}							
					} else {
						gui.showMessage(exception.getMessage());
					}
					
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
