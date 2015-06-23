package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import model.Group;
import model.ItemFile;
import model.User;
import boundary.FileGroupsSelect_GUI;
import callback.GetGroupAccessCallback;
import callback.GetGroupsCallback;
import callback.UpdateFileGroupsCallback;
import client.Client;

import common.Boundary;
import common.Controller;
import common.Message;
import common.MessageType;
import common.MyBoxException;

public class FileGroupsSelectController extends Controller{
	private User user;
	private FileGroupsSelect_GUI gui;
	private ItemFile file;
	public FileGroupsSelectController(ItemFile file) {
		super(file);
		this.file = file;
		this.gui = (FileGroupsSelect_GUI)super.gui;
		
		user = file.getOwner();
		Message grpMsg = new Message(user,MessageType.GET_USER_GROUPS);
		try {
			Client.getInstance().sendMessage(grpMsg, new GetGroupsCallback() {
				
				@Override
				protected void done(Set<Group> groups, MyBoxException exception) {
					System.out.println("done method filegroupselectcontroller");
					if (exception == null){
						for (Group group : groups)
							gui.addGroup(group);
					}else { exception.printStackTrace();}
					
				}
			});
		} catch (IOException e1) {
			e1.printStackTrace();
		}
				
		Message msg = new Message(gui.getFile(), MessageType.GET_FILE_GROUPS_ACCESS);
		try {
			Client.getInstance().sendMessage(msg, new GetGroupAccessCallback() {
				
				@Override
				protected void done(HashMap<Integer, Integer> groupsAccess,MyBoxException exception) {
					System.out.println("groupAccess Callback ");
					if (exception == null){
						if (groupsAccess == null) System.out.println("groupsAccess is null");
						if (groupsAccess.size() == 0) System.out.println("groupAccess is empty");
						for (int groupId :groupsAccess.keySet()){
							gui.addGroupAccess(groupId,groupsAccess.get(groupId).intValue());
							System.out.println("groupId:"+groupId);
						}
					
					} else{
						gui.showMessage(exception.getMessage());
					}
				}
			});
		} catch (IOException e) {
			gui.showMessage(e.getMessage());
		}
	}
	
	
	@Override
	protected Boundary initBoundary(ItemFile file) {
		return new FileGroupsSelect_GUI(this,file);
	}

	public void btnFinishClicked() {
		gui.close();
	}
	
	public void setOwnerGroups(Set<Group> groups){
		user.setGroups(groups);
	}

	public void cbAccessChanged(Group group, int access) {
		HashMap<String,Integer> groupAccess = new HashMap<String,Integer>();
		groupAccess.put("groupId", group.getGroupID());
		groupAccess.put("fileId", file.getID() );
		groupAccess.put("access", access);
		Message msg = new Message(groupAccess, MessageType.UPDATE_FILE_GROUPS_ACCESS);
		try {
			Client.getInstance().sendMessage(msg, new UpdateFileGroupsCallback() {
				
				@Override
				protected void done(HashMap<Group, Integer> groupsAccess,MyBoxException exception) {
					if (exception != null){
						
					}
					
				}
			});
		} catch (IOException e) {
			gui.showMessage(e.getMessage());
		}
		
	}


	@Override
	protected Boundary initBoundary() { //Unused implemented method
		return null;
	}
		
}
	

