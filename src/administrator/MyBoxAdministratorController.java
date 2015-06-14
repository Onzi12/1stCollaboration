package administrator;

import common.Boundary;

import model.User;
import controller.MyBoxController;

public class MyBoxAdministratorController extends MyBoxController {

	public MyBoxAdministratorController(User user) {
		super(user);
	}

	@Override
	public Boundary initBoundary() {
		return new MyBoxAdministrator_GUI(this);
	}
	

}
