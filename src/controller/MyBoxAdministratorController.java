package controller;

import java.io.IOException;

import javax.swing.JOptionPane;

import model.Group;
import model.User;
import boundary.MyBoxAdministrator_GUI;
import callback.CreateNewGroupCallback;
import client.Client;

import common.Boundary;
import common.Message;
import common.MessageType;
import common.MyBoxException;

public class MyBoxAdministratorController extends MyBoxController {
	

	MyBoxAdministrator_GUI gui;
	
	public MyBoxAdministratorController(User user) {

		gui = (MyBoxAdministrator_GUI)super.gui;
		
	}

	@Override
	public Boundary initBoundary() {
		return new MyBoxAdministrator_GUI(this);
	}

	public void btnManageFileGroupsClicked() {
		new AdminFileGroupsSelectController();
		
	}

	public void btnManageGroupRequestsClicked() {
		new ManageGroupRequestsController();
	}

	public void btnCreateGroupClicked() {
		String groupName = JOptionPane.showInputDialog("Enter New Group Name:");
		if(groupName == null)
			return;
		Group group = new Group();
		group.setGroupID(0);
		group.setName(groupName); 
		Message msg = new Message(group, MessageType.CREATE_NEW_GROUP);
		try {
			Client.getInstance().sendMessage(msg, new CreateNewGroupCallback() {
				
				@Override
				public void done(Group group, MyBoxException exception) {
						if (exception != null )gui.showMessage(exception.getMessage());
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
