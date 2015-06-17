package controller;

import java.io.IOException;
import java.util.HashMap;

import callback.EditFileCallback;
import callback.PhysicalDeleteFileCallback;
import client.Client;
import model.Item;
import model.ItemFile;
import boundary.FileDelete_GUI;
import common.Boundary;
import common.Controller;
import common.Message;
import common.MessageType;
import common.MyBoxException;

public class FileDeleteController extends Controller{

	private FileDelete_GUI gui;
	private HashMap<String,Item> files;
	
	public FileDeleteController(){
		gui = (FileDelete_GUI)super.gui;
		files = Client.getInstance().getUser().getFiles();
		for (Item x : files.values())
			if ( x instanceof ItemFile)
				if (((ItemFile) x).getOwner() == Client.getInstance().getUser().getID())
					gui.addListValue((ItemFile)x);
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
			Client.getInstance().sendMessage(msg,new PhysicalDeleteFileCallback(){

				@Override
				protected void done(ItemFile file, MyBoxException exception) {
					MyBoxController controller = (MyBoxController)NavigationManager.getInstance().getCurrentController();
					controller.handleDeleteFileCallback(file, exception);	
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
