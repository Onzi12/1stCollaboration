package controller;

import boundary.FileRestore_GUI;
import common.Boundary;
import common.Controller;

public class FileRestoreController extends Controller {

	@Override
	protected Boundary initBoundary() {
		return new FileRestore_GUI(this);
	}

	public void btnRestoreClicked() {
		// TODO Auto-generated method stub
		
	}

	public void btnCloseClicked() {
		gui.close();
		
	}

}
