package controller;

import boundary.MyBoxAdministrator_GUI;
import model.User;
import common.Boundary;

public class MyBoxAdministratorController extends MyBoxController {
	
	private User user;
	MyBoxAdministrator_GUI gui;
	
	public MyBoxAdministratorController(User user) {
		super(user);
		this.user = user;
		gui = (MyBoxAdministrator_GUI)super.gui;
		
	}

	@Override
	public Boundary initBoundary() {
		return new MyBoxAdministrator_GUI(this);
	}

	public void btnManageFileGroupsClicked() {
		new ManageFileGroupsController();
		
	}

	public void btnManageGroupRequestsClicked() {
		new ManageGroupRequestsController();
	}
	
}
