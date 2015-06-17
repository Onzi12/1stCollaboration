package controller;

import model.ItemFile;
import boundary.FileDelete_GUI;
import common.Boundary;
import common.Controller;

public class FileDeleteController extends Controller{

	private FileDelete_GUI gui;
	private ItemFile file;
	
	public FileDeleteController(ItemFile file){
		super(file);
		this.file = file;
		gui = (FileDelete_GUI)super.gui;
	}
	
	@Override
	protected Boundary initBoundary(ItemFile file) {
		return new FileDelete_GUI(this);
	}
	
	@Override
	protected Boundary initBoundary() {
		return new FileDelete_GUI(this);
	}

	public void btnCloseClicked() {
		gui.close();
		
	}

	public void btnSaveClicked() {
		// TODO Auto-generated method stub
		
	}

}
