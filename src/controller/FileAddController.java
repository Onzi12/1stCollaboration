package controller;

import java.util.HashMap;

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
	
	public FileAddController() {
		gui = (FileAdd_GUI)super.gui;
		Message msg = new Message(null,MessageType.GET_ADD_FILES);
		try{
			Client.getInstance().sendMessage(msg,new GetFilesCallback() {
				
				
				@Override
				protected void done(HashMap<String, Item> items, MyBoxException exception) {
					System.out.println("123132");
					if (exception == null){
						files = items;
						for (Item x : files.values())
							if ( x instanceof ItemFile )
									gui.addListValue((ItemFile)x);
					}
					else getGui().showMessage(exception.getMessage());
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
		
		
	}

	public void btnCloseClicked() {
		gui.close();
	}



	

}
