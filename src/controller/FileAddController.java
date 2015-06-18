package controller;

import java.io.IOException;
import java.util.HashMap;

import callback.AddFileCallback;
import callback.GetFilesCallback;
import client.Client;
import model.Item;
import model.ItemFile;
import model.User;
import boundary.FileAdd_GUI;
import boundary.FileDelete_GUI;
import common.Boundary;
import common.Controller;
import common.Message;
import common.MessageType;
import common.MyBoxException;

public class FileAddController extends Controller {
	
	private FileAdd_GUI gui;
	private HashMap<String,Item> files;
	private ItemFile file;
	
	public FileAddController() {
		gui = (FileAdd_GUI)super.gui;
		Message msg = new Message(Client.getInstance().getUser(),MessageType.GET_ADD_FILES);
		try{
			Client.getInstance().sendMessage(msg,new GetFilesCallback() {
				
				
				@Override
				protected void done(HashMap<String, Item> items, MyBoxException exception) {
					if (exception == null){
						files = items;
						for (Item x : files.values())
							if ( x instanceof ItemFile )
									gui.addListValue((ItemFile)x);
					}
					else exception.getStackTrace();
				}
				@Override
				protected MessageType getMessageType() {
					return MessageType.GET_ADD_FILES;
				}
			});			
		} catch (Exception e) { 
			getGui().showMessage(e.getMessage());
			} 			
	}



	@Override
	protected Boundary initBoundary() {
		return new FileAdd_GUI(this);
	}

	
	public void btnAddFileClicked() {
		file = gui.getSelectedFile();
		Message msg = new Message(file, MessageType.ADD_FILE);
		
		try {
			Client.getInstance().sendMessage(msg, new AddFileCallback() {
				
				@Override
				protected void done(ItemFile file, MyBoxException exception) {
					MyBoxController controller = (MyBoxController)NavigationManager.getInstance().getCurrentController();
					controller.handleAddFileCallback(file, exception);
					
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void btnCloseClicked() {
		gui.close();
	}



	

}
