package controller;

import java.util.HashMap;

import client.Client;
import model.Item;
import model.ItemFile;
import boundary.FileDelete_GUI;
import common.Boundary;
import common.Controller;

public class FileDeleteController extends Controller{

	private FileDelete_GUI gui;
	private HashMap<String,Item> files;
	
	public FileDeleteController(){
		gui = (FileDelete_GUI)super.gui;
		files = Client.getInstance().getUser().getFiles();
		for (Item x : files.values())
			if ( x instanceof ItemFile)
				if (((ItemFile) x).getOwner() == Client.getInstance().getUser().getID())
					gui.addListValue((ItemFile)x);
			
	}
		
	@Override
	protected Boundary initBoundary() {
		return new FileDelete_GUI(this);
	}

	public void btnCloseClicked() {
		gui.close();
		
	}

	public void btnDeleteClicked() {
		
		
	}

}
