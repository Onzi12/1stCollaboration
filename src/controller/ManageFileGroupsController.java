package controller;

import boundary.ManageFileGroups_GUI;
import common.Boundary;
import common.Controller;

public class ManageFileGroupsController extends Controller {
	

	@Override
	protected Boundary initBoundary() {
		return new ManageFileGroups_GUI(this);
	}

}
