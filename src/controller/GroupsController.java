package controller;

import java.io.IOException;
import java.util.HashMap;

import model.Group;
import callback.GetGroupsCallback;
import client.Client;
import boundary.Groups_GUI;
import common.Boundary;
import common.Controller;
import common.Message;
import common.MessageType;
import common.MyBoxException;

public class GroupsController extends Controller {
	
	private Groups_GUI gui;
	
	public GroupsController() {
		gui = (Groups_GUI)super.gui;
		
		Message msg = new Message(Client.getInstance().getUser(), MessageType.GET_ALL_USER_IN_GROUP);
		try {
			Client.getInstance().sendMessage(msg, new GetGroupsCallback() {
				
				@Override
				protected void done(HashMap<String, Group> groups, MyBoxException exception) {
					if (exception == null)
						for (String key : groups.keySet())
							gui.addMyGroupElement(groups.get(key));
					else {
						gui.showMessage(exception.getMessage());
					}
				}
			});
		} catch (IOException e) {
			gui.showMessage(e.getMessage());
		}
		
		msg = new Message(Client.getInstance().getUser(), MessageType.GET_ALL_USER_NOT_IN_GROUP);
		try {
			Client.getInstance().sendMessage(msg, new GetGroupsCallback() {
				
				@Override
				protected void done(HashMap<String, Group> groups, MyBoxException exception) {
					if (exception == null)
						for (String key : groups.keySet())
							gui.addOtherGroupElement(groups.get(key));
					else {
						gui.showMessage(exception.getMessage());
					}
				}
				
				@Override
				protected MessageType getMessageType() {
					return MessageType.GET_ALL_USER_NOT_IN_GROUP;
				}
			});
			
		} catch (IOException e) {
			gui.showMessage(e.getMessage());
		}
	}


	
	@Override
	protected Boundary initBoundary() {
		return  new Groups_GUI(this);
	
	}

	public void btnSendRequestsClicked() {
		// TODO Auto-generated method stub
		
	}

	public void btnCloseClicked() {
		gui.close();
		
	}

	public void btnLeaveClicked() {
		// TODO Auto-generated method stub
		
	}

	public void btnJoinClicked() {
		// TODO Auto-generated method stub
		
	}

}
