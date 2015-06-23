package controller;

import java.io.IOException;
import java.util.HashSet;

import model.ItemFile;
import boundary.FileDelete_GUI;
import callback.FileDeleteCallback;
import callback.GetDeleteFilePhyisicalCallback;
import client.Client;

import common.Boundary;
import common.Controller;
import common.Message;
import common.MessageType;
import common.MyBoxException;

public class FileDeleteController extends Controller{

	protected FileDelete_GUI gui;
	protected HashSet<ItemFile> files;
	
	public FileDeleteController(){
		gui = (FileDelete_GUI)super.gui;
		
		Message msg = new Message(Client.getInstance().getUser(), MessageType.GET_DELETE_FILE_PHYSICAL);
		try {
			Client.getInstance().sendMessage(msg, new GetDeleteFilePhyisicalCallback() {
				
				@Override
				protected void done(HashSet<ItemFile> files, MyBoxException exception) {
					setFiles(files);
					for (ItemFile x : files)
						gui.addListValue(x);
					}
			});
		} catch (IOException e) {
			gui.showMessage(e.getMessage());

		}

		}
	
		
	@Override
	protected Boundary initBoundary() {
		return new FileDelete_GUI(this);
	}

	public void btnCloseClicked() {
		gui.close();
		
	}

	public void btnDeleteClicked() {
		ItemFile file = gui.getSelectedFile();  
		Message msg = new Message(file,MessageType.DELETE_FILE_PHYSICAL);
		try {
			Client.getInstance().sendMessage(msg,new FileDeleteCallback(){

				@Override
				protected void done(ItemFile file, MyBoxException exception) {
					MyBoxController controller = (MyBoxController)NavigationManager.getInstance().getCurrentController();
					controller.handleDeleteFileCallback(file, exception);	
				}
				
			});
		} catch (IOException e) {
			e.printStackTrace();
		} finally { 
			gui.close();
		}
	}


	public HashSet<ItemFile> getFiles() {
		return files;
	}


	public void setFiles(HashSet<ItemFile> files) {
		this.files = files;
	}

}
