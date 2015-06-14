package controller;


import model.ItemFile;
import boundary.FileRead_GUI;

import common.Boundary;
import common.Controller;

public class FileReadController extends Controller {

	public FileReadController(ItemFile file) {
	
		FileRead_GUI g = (FileRead_GUI)gui;
		g.setFilename(file.getName());
		g.setLocation(file.getFullPath());
	}
	
	public void btnCancelClicked() {
		gui.close();
	}

	@Override
	protected Boundary initBoundary() {
		return new FileRead_GUI(this);
	}
}
