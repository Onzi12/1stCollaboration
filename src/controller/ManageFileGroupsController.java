package controller;

import boundary.ManageFileGroups_GUI;
import common.Boundary;
import common.Controller;

public class ManageFileGroupsController extends Controller {
	

	@Override
	protected Boundary initBoundary() {
		return new ManageFileGroups_GUI(this);
	}

	public void btnCloseClicked() {
		gui.close();
		
	}

	public void btnAssociateClicked() {
		// TODO Auto-generated method stub
		
	}

	public void btnConfirmClicked() {
		// TODO Auto-generated method stub
		
	}

	public void btnExcludeClicked() {
		// TODO Auto-generated method stub
		
	}

}
