package controller;

import boundary.Groups_GUI;
import common.Boundary;
import common.Controller;

public class GroupsController extends Controller {

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
