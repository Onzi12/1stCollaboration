package controller;

import boundary.MyBoxAdministrator_GUI;
import model.User;
import common.Boundary;

public class MyBoxAdministratorController extends MyBoxController {

	public MyBoxAdministratorController(User user) {
		super(user);
	}

	@Override
	public Boundary initBoundary() {
		return new MyBoxAdministrator_GUI(this);
	}
	

}
