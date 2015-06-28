package controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import model.Group;
import boundary.Groups_GUI;
import callback.GetGroupsCallback;
import callback.SendRequestsCallback;
import client.Client;
import common.Boundary;
import common.Controller;
import common.Message;
import common.MessageType;
import common.MessageWithUser;
import common.MyBoxException;

public class GroupsController extends Controller {
	
	private Groups_GUI gui;
	private Set<Group> userGroups;
	private Set<Group> otherGroups;
	
	public GroupsController() {
		gui = (Groups_GUI)super.gui;
		userGroups = new HashSet<Group>();
		otherGroups = new HashSet<Group>();
		getUserGroups();		
		getOtherGroups();
	}
	
	private void getOtherGroups(){
		
		Message msg = new Message(Client.getInstance().getUser(), MessageType.GET_ALL_OTHER_GROUPS);
		try {
			Client.getInstance().sendMessage(msg, new GetGroupsCallback() {
				
				@Override
				protected void done(Set<Group> groups, MyBoxException exception) {
					if (exception == null)
						for (Group g : groups){
							gui.addOtherGroupElement(g);
							otherGroups.add(g);
							}
					else {
						gui.showMessage(exception.getMessage());
					}
				}
				
				@Override
				protected MessageType getMessageType() {
					return MessageType.GET_ALL_OTHER_GROUPS;
				}
			});
			
		} catch (IOException e) {
			gui.showMessage(e.getMessage());
		}
	}
	
	private void getUserGroups(){
		Message msg = new Message(Client.getInstance().getUser(), MessageType.GET_USER_GROUPS);
		try {
			Client.getInstance().sendMessage(msg, new GetGroupsCallback() {
				
				@Override
				protected void done(Set<Group> groups, MyBoxException exception) {
					if (exception == null)
						for (Group g : groups) {
							gui.addMyGroupElement(g);
							userGroups.add(g);
						}
					else {
						gui.showMessage(exception.getMessage());
					}
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
		
		sendRequests(new SendRequestsCallback() {

			@Override
			protected void messageReceived(String message, MyBoxException exception) {
				if (exception == null) {
					gui.showHappyMessage(message);
					gui.close();
				} else {
					gui.showMessage(exception.getMessage());
				}
			}
			
		});
		

		
	}
	
	public void sendRequests(SendRequestsCallback callback) {
		Message msg = new MessageWithUser(userGroups, MessageType.SEND_NEW_GROUP_REQUESTS);
		try {
			Client.getInstance().sendMessage(msg, callback);
		} catch (IOException e) {
			gui.showMessage(e.getMessage());
		}
	}

	public void btnLeaveClicked() {
		Group marked = gui.getSelectedMyGroup();
		if(marked == null)
			return;
		userGroups.remove(marked);
		otherGroups.add(marked);
		gui.listOtherGroupsModel.addElement(marked);
		gui.listMyGroupsModel.removeElement(marked);
	}

	public void btnJoinClicked() {
		joinGroup();
	}
	
	public void joinGroup() {
		Group marked = gui.getSelectedOtherGroup();
		if(marked == null)
			return;
		otherGroups.remove(marked);
		gui.listOtherGroupsModel.removeElement(marked);
		userGroups.add(marked);
		gui.listMyGroupsModel.addElement(marked);
	}
	
	public void joinGroupAtIndex(int index) {
		gui.setSelectedOtherGroup(index);
		joinGroup();
	}
		
	public void btnCloseClicked() {
		gui.close();
	}
}
