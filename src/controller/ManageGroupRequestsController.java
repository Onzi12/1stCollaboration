package controller;

import boundary.ManageGroupRequests_GUI;
import common.Boundary;
import common.Controller;

public class ManageGroupRequestsController extends Controller {

	@Override
	protected Boundary initBoundary() {
		return new ManageGroupRequests_GUI(this);
	}

}
