package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import common.Boundary;
import common.Controller;
import model.ItemFile;
import boundary.FileRead_GUI;

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
