package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import model.Item;
import model.Request;
import callback.GetGroupRequestsCallback;
import callback.GroupRequestAnswerCallback;
import client.Client;
import boundary.ManageGroupRequests_GUI;
import common.Boundary;
import common.Controller;
import common.Message;
import common.MessageType;
import common.MyBoxException;
import custom_gui.RequestTableModel;

public class ManageGroupRequestsController extends Controller {

	private ManageGroupRequests_GUI gui;
	
	public ManageGroupRequestsController() {
	
		this.gui = (ManageGroupRequests_GUI)super.gui;
		try {
			Message msg = new Message(null, MessageType.GET_GROUP_REQUESTS);
			Client.getInstance().sendMessage(msg, new GetGroupRequestsCallback() {
				
				@Override
				protected void done(Set<Request> requests, MyBoxException exception) {
					ArrayList<Request> requestsList = new ArrayList<Request>();
					for (Request r : requests) {
						requestsList.add(r);
					}
					gui.setTableModel(new RequestTableModel(requestsList));
				}
				
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	protected Boundary initBoundary() {
		return new ManageGroupRequests_GUI(this);
	}

	public void btnCloseClicked() {
		gui.close();
	}

	public void btnAcceptClicked() {
		
		Request r = gui.getSelectedRequest();
		System.err.println(r.getUser().getUserName() + "   "+r.getGroup().getName());
		try {
			Message accept = new Message(r, MessageType.REQUEST_ANSWER_ACCEPT);
			Client.getInstance().sendMessage(accept, new GroupRequestAnswerCallback() {
				
				@Override
				protected void done(MyBoxException exception) {
					
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void btnDeclineClicked() {
		
		Request r = gui.getSelectedRequest();
		try {
			Message accept = new Message(r, MessageType.REQUEST_ANSWER_REJECT);
			Client.getInstance().sendMessage(accept, new GroupRequestAnswerCallback() {
				
				@Override
				protected void done(MyBoxException exception) {
					
				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
