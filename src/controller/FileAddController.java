package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.tree.DefaultMutableTreeNode;

import model.Item;
import model.ItemFile;
import model.ItemFolder;
import boundary.FileAdd_GUI;
import boundary.MyBox_GUI;
import callback.AddFileCallback;
import callback.GetAddFilesCallback;
import client.Client;
import common.Boundary;
import common.Controller;
import common.Message;
import common.MessageType;
import common.MyBoxException;

public class FileAddController extends Controller {
	
	private FileAdd_GUI gui;
	private HashSet<ItemFile> files;
	private ItemFile file;
	
	public FileAddController() {
		gui = (FileAdd_GUI)super.gui;
		MyBoxController control = (MyBoxController) nav.getCurrentController();
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)((MyBox_GUI)control.getGui()).getTree().getLastSelectedPathComponent();
		ItemFolder folder = (ItemFolder)node.getUserObject();
		Message msg = new Message(folder,MessageType.GET_ADD_FILES);
		try{
			Client.getInstance().sendMessage(msg,new GetAddFilesCallback() {
				
				@Override
				protected void done(HashSet<ItemFile> items, MyBoxException exception) {
					if (exception == null){
						files = items;
						for (Item x : files)
							if ( x instanceof ItemFile )
									gui.addListValue((ItemFile)x);
							
					}
					else {
						gui.showMessage(exception.getMessage());
					}
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
		file.setUserID(Client.getInstance().getUser().getID());
		MyBoxController control = (MyBoxController) nav.getCurrentController();
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)((MyBox_GUI)control.getGui()).getTree().getLastSelectedPathComponent();
		ItemFolder folder = (ItemFolder)node.getUserObject();
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("file", file);
		data.put("parentID", new Integer(folder.getID()));
		data.put("userID", new Integer(Client.getInstance().getUser().getID()));
		try {
			Message msg = new Message(data, MessageType.ADD_FILE);
			Client.getInstance().sendMessage(msg, new AddFileCallback() {
				
				@Override
				protected void done(ItemFile file, MyBoxException exception) {
					
					MyBoxController control = (MyBoxController) nav.getCurrentController();
					control.handleAddFileCallback(file, exception);
					
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			gui.close();
		}
		
	}

	public void btnCloseClicked() {
		gui.close();
	}



	

}
