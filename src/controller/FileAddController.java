package controller;

import model.ItemFile;
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


	@Override
	protected Boundary initBoundary(ItemFile file) {
		// TODO Auto-generated method stub
		return null;
	}

}
