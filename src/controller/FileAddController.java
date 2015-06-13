package controller;

import boundary.FileAdd_GUI;
import common.Boundary;
import common.Controller;

public class FileAddController extends Controller {

	
	@Override
	protected Boundary initBoundary() {
		return new FileAdd_GUI(this);
	}

	
	public void btnAddFileClicked() {
		// TODO Auto-generated method stub
		
	}

	public void btnCloseClicked() {
		gui.close();
	}

}
