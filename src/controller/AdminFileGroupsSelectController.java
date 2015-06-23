package controller;

import java.io.IOException;
import java.util.HashSet;

import model.ItemFile;
import boundary.ManageFileGroupsAssociation_GUI;
import callback.GetAllFilesCallback;
import client.Client;

import common.Boundary;
import common.Controller;
import common.Message;
import common.MessageType;
import common.MyBoxException;

public class AdminFileGroupsSelectController extends Controller {

	protected final ManageFileGroupsAssociation_GUI gui = (ManageFileGroupsAssociation_GUI)super.gui;
	
	public AdminFileGroupsSelectController() {
		Message msg = new Message(null,MessageType.GET_ALL_FILES);
		try {
			Client.getInstance().sendMessage(msg, new GetAllFilesCallback() {
				
				@Override
				protected void done(HashSet<ItemFile> files, MyBoxException exception) {
					System.out.println("done method AdminFileGroupSelectController");
					if(exception == null){
						System.out.println("exception in AdminFileGroupSelectController is null");
						if (files == null) System.out.println("files is null");
						if (files.size() == 0) System.out.println("files is empty");
						for(ItemFile file : files)
							gui.addListItem(file);
					}
					else { exception.printStackTrace(); }
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
		
	
	@Override
	protected Boundary initBoundary() {
		return new ManageFileGroupsAssociation_GUI(this);
	}

	public void btnSelectClicked(ItemFile file) {
		new FileGroupsSelectController(file);
		
	}

	public void btnCloseClicked() {
		gui.close();
		
	}

}
