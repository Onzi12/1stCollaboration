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

/**
 * A Controller Class that handles the Admin's file selection to edit groups association operations from Class {@link ManageFileGroupsAssociation_GUI}
 *
 */
public class AdminFileGroupsSelectController extends Controller {

	/**
	 * Reference to the GUI Class it handles
	 */
	protected final ManageFileGroupsAssociation_GUI gui = (ManageFileGroupsAssociation_GUI)super.gui;
	
	public AdminFileGroupsSelectController() {
		Message msg = new Message(null,MessageType.GET_ALL_FILES);
		try {
			Client.getInstance().sendMessage(msg, new GetAllFilesCallback() {
				
				@Override
				protected void done(HashSet<ItemFile> files, MyBoxException exception) {

					if(exception == null){

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
