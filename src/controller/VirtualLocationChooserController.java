package controller;

import javax.swing.tree.DefaultMutableTreeNode;

import model.ItemFolder;
import boundary.FileTreeModel;
import boundary.VirtualLocationChooser_GUI;
import common.Boundary;
import common.Controller;

public class VirtualLocationChooserController extends Controller {

	private Controller guiController;
	private VirtualLocationChooser_GUI gui;
	private ItemFolder folder;
	
	public VirtualLocationChooserController(Controller guiController) {
		this.guiController = guiController;
		this.gui = (VirtualLocationChooser_GUI)super.gui;
		
		MyBoxController myBoxController = (MyBoxController)NavigationManager.getInstance().getCurrentController();
		gui.getTree().setModel(new FileTreeModel(myBoxController.gui.getTree().getRoot()));

	}
	
	@Override
	protected Boundary initBoundary() {
		return new VirtualLocationChooser_GUI(this);
	}

	public void btnOKClicked() {
		if (guiController instanceof FileUpdateController) {
			((FileUpdateController)guiController).setVirtualSaveLocation(folder);
		} else if (guiController instanceof MyBoxController) {
			((MyBoxController)guiController).moveFileToFolder(folder);
		}
		gui.close();
	}

	public void btnCloseClicked() {
		gui.close();
	}

	public void clickedOnTreeNode() {
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)gui.getTree().getLastSelectedPathComponent(); 
		if (selectedNode != null) {
			
			if (selectedNode.getUserObject() instanceof ItemFolder) {
				ItemFolder folder = (ItemFolder)selectedNode.getUserObject();
				this.folder = folder;
			}
			
		}
	}

}
