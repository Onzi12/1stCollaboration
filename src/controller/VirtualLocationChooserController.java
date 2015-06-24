package controller;

import javax.swing.tree.DefaultMutableTreeNode;

import model.ItemFile;
import model.ItemFolder;
import boundary.FileTreeModel;
import boundary.VirtualLocationChooser_GUI;

import common.Boundary;
import common.Controller;

/**
 * The {@link VirtualLocationChooserController} class enables user interaction with the {@link VirtualLocationChooser_GUI}.
 *
 */
public class VirtualLocationChooserController extends Controller {

	/**
	 * The controller of the GUI class that created the {@link VirtualLocationChooserController}.
	 */
	private Controller guiController;
	/**
	 * A reference the the {@link VirtualLocationChooser_GUI}.
	 */
	private VirtualLocationChooser_GUI gui;
	/**
	 * A reference to the selected folder.
	 */
	private ItemFolder folder;
	/**
	 * A reference to the file.
	 */
	private ItemFile file;
	
	/**
	 * Construct the {@link VirtualLocationChooserController}.
	 * @param guiController
	 * @param file
	 */
	public VirtualLocationChooserController(Controller guiController, ItemFile file) {
		this.guiController = guiController;
		this.gui = (VirtualLocationChooser_GUI)super.gui;
		this.file = file;
		
		MyBoxController myBoxController = (MyBoxController)NavigationManager.getInstance().getCurrentController();
		gui.getTree().setModel(new FileTreeModel(myBoxController.gui.getTree().getRoot()));

	}
	
	@Override
	protected Boundary initBoundary() {
		return new VirtualLocationChooser_GUI(this);
	}

	/**
	 * A handler method for the OK button.
	 */
	public void btnOKClicked() {
		if (guiController instanceof FileUpdateController) {
			((FileUpdateController)guiController).setVirtualSaveLocation(folder);
		} else if (guiController instanceof MyBoxController) {
			((MyBoxController)guiController).moveFileToFolder(folder, file);
		}
		gui.close();
	}

	/**
	 * A handler method for the close button.
	 */
	public void btnCloseClicked() {
		gui.close();
	}

	/**
	 * A handler method called on a tree node is clicked.
	 */
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
