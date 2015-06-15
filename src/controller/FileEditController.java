package controller;

import boundary.FileEdit_GUI;
import model.ItemFile;
import common.Boundary;
import common.Controller;

public class FileEditController extends Controller{
	
	public FileEditController(ItemFile file) {
		super(file);
	}
	
	@Override
	protected Boundary initBoundary() {
				return null;
	}
	
	@Override
	protected Boundary initBoundary(ItemFile file) {
		return new FileEdit_GUI(this, file);
	}

}
