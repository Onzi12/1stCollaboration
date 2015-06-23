package controller;

import model.User;
import boundary.MyBoxAdministrator_GUI;

import common.Boundary;

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
	
}
