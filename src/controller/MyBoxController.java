package controller;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import model.Item;
import model.ItemFile;
import model.ItemFolder;
import model.User;
import boundary.FilePopUpMenu_GUI;
import boundary.FileRead_GUI;
import boundary.FileTreeModel;
import boundary.FileTreeModelListenter;
import boundary.FileUpdate_GUI;
import boundary.Login_GUI;
import boundary.MyBox_GUI;
import client.Client;
import common.Boundary;
import common.Controller;
import common.Message;
import common.MessageType;

public class MyBoxController extends Controller implements Observer {

	private User user;
	
	public MyBoxController(User user) {
		super();
		this.user = user;

		gui.registerTableMouseListener(new TableMouseListener());
		gui.registerMouseListener(new MyBoxMouseListener());
		gui.registerTreeModeListener(new FileTreeModelListenter());
	}
	
	public MyBoxController() {
		// TODO Auto-generated constructor stub
	}

	private class MyBoxMouseListener extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			MyBox_GUI gui = (MyBox_GUI)getGui();
			gui.getTable().clearSelection();
			gui.getTree().clearSelection();
		}
	}
	
	private class TableMouseListener extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			MyBox_GUI gui = (MyBox_GUI)getGui();
			if (!e.isPopupTrigger() && e.getClickCount() == 2 && !e.isConsumed()) {
				int row = gui.getTable().rowAtPoint(e.getPoint());
		        if (row < 0)
		            return;
				ItemFile file = (ItemFile)gui.tableGetFile(row);
				FileRead_GUI read = new FileRead_GUI(getNavigationManager().getFrame());
				new FileReadController(read, file);
				read.setVisible(true);
	        	gui.getTable().clearSelection();
                e.consume();
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			openMenu(e);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			openMenu(e);
		}
		
		private void openMenu(MouseEvent e) {
			MyBox_GUI gui = (MyBox_GUI)getGui();
	        int row = gui.getTable().rowAtPoint(e.getPoint());
	        if (row >= 0 && row < gui.getTable().getRowCount()) {
	        	gui.getTable().setRowSelectionInterval(row, row);
	        } else {
	        	gui.getTable().clearSelection();
	        }

	        int rowindex = gui.getTable().getSelectedRow();
	        if (rowindex < 0)
	            return;
	        if (e.isPopupTrigger() && (e.getComponent() instanceof JTable)) {
				ItemFile file = (ItemFile)gui.tableGetFile(row);
	    		FilePopUpMenu_GUI menu = new FilePopUpMenu_GUI(file.getName());
	    		new FilePopUpMenuController(MyBoxController.this, menu, file);
				menu.show(e.getComponent(), e.getX(), e.getY());
	        }
		}
		
	}

	@Override
	public void update(Observable o, Object arg) {
	
		if (arg instanceof Message) {
			MyBox_GUI gui = (MyBox_GUI)getGui();

			Message msg = (Message) arg;
			MessageType type = msg.getType();
			
			switch (type) {
			case GET_FILES:
			{
				@SuppressWarnings("unchecked")
				HashMap<String, Item> items = (HashMap<String, Item>)msg.getData();
				HashMap<String, ItemFile> filesDB = new HashMap<String, ItemFile>();
				for (Item item : items.values()) {
					if (item instanceof ItemFile) {
						filesDB.put(item.getStringID(), (ItemFile) item);
					}
				}
				user.setFiles(filesDB);
				processTreeHierarchy(items);
				ArrayList<Item> files = new ArrayList<Item>();
				files.addAll(user.getFiles().values());
				gui.refreshTable(files);
			}
				break;
				
			case UPDATE_FILE:
			{
				ItemFile file = (ItemFile)msg.getData();
				user.getFiles().put(file.getStringID(), file);
				ArrayList<Item> files = new ArrayList<Item>();
				files.addAll(user.getFiles().values());
				gui.refreshTable(files);
			}
				break;
				
			case ADD_FILE:
			{
				ItemFile file = (ItemFile)msg.getData();
				user.getFiles().put(file.getStringID(), file);
				ArrayList<Item> files = new ArrayList<Item>();
				files.addAll(user.getFiles().values());
				gui.refreshTable(files);
			}
				break;

			case DELETE_FILE: 
				break;
				
			case ERROR_MESSAGE:
				
				String str = (String)msg.getData();
				if (str.equals(Client.CONNECTION_EXCEPTION)) {
					((MyBox_GUI)getGui()).showMessage("Please try to connect again later.");
					logout();
				}
				
				break;
				
			default:
				break;
			}
		}
		
	}

	public void processTreeHierarchy(HashMap<String, Item> items) {
		MyBox_GUI gui = (MyBox_GUI)getGui();

		DefaultMutableTreeNode root = (DefaultMutableTreeNode)gui.getTree().getModel().getRoot();
		
		for (Item item : items.values()) {
			
			if (item instanceof ItemFolder) {
				
				addObject(item);
				
			}
			
		}
		
		// expand the root folder
		TreePath path = new TreePath(root);
		gui.getTree().expandPath(path);
		
	}
	
	// Add a child by a selection path
	public DefaultMutableTreeNode addObject(Object child) {
		MyBox_GUI gui = (MyBox_GUI)getGui();

	    DefaultMutableTreeNode parentNode = null;
	    TreePath parentPath = gui.getTree().getSelectionPath();
		DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode)gui.getTree().getModel().getRoot();

	    if (parentPath == null) {
	        //There is no selection. Default to the root node.
	        parentNode = rootNode;
	    } else {
	        parentNode = (DefaultMutableTreeNode)parentPath.getLastPathComponent();
	    }

	    return addObject(parentNode, child, true);
	}
	
	// Add a child to a specific parent
	public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child, boolean shouldBeVisible) {
		MyBox_GUI gui = (MyBox_GUI)getGui();

		DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);
		FileTreeModel model = (FileTreeModel)gui.getTree().getModel();
		model.insertNodeInto(childNode, parent, parent.getChildCount());
		
		if (shouldBeVisible) {
			gui.getTree().scrollPathToVisible(new TreePath(childNode.getPath()));
		}
		
		
		return childNode;
	}
	
	// Remove a child by a selection path
	public void removeObject() {
		MyBox_GUI gui = (MyBox_GUI)getGui();

	    TreePath path = gui.getTree().getSelectionPath();
	    if (path != null) {
			DefaultMutableTreeNode nodeToRemove = (DefaultMutableTreeNode)path.getLastPathComponent();	    
			FileTreeModel model = (FileTreeModel)gui.getTree().getModel();
			model.removeNodeFromParent(nodeToRemove);
	    }
	}
	
	public void btnLogoutClicked() {
		int answer = JOptionPane.showConfirmDialog((MyBox_GUI)getGui(), "Are you sure you want to logout?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (answer == JOptionPane.YES_OPTION) {
			logout();
		}
	}
	
	private void logout() {
		Client client = Client.getInstance();
		
		try {
			Message logout = new Message(null, MessageType.LOGOUT);
			client.sendMessage(logout);
		} catch (IOException e) {}
		
		client.deleteObservers();
		client.disconnect();
		
		// show login panel
		new LoginController();		
	}
	
	public void btnUpdateFileClicked() {
		MyBox_GUI gui = (MyBox_GUI)getGui();
		int row = gui.getTable().getSelectedRow();
		
		if (row < 0) {
			
			FileUpdate_GUI create = new FileUpdate_GUI(nav.getFrame(), true);
			new FileUpdateController(create, null);
			create.setVisible(true);
			
		} else {
			
			ItemFile file = (ItemFile)gui.tableGetFile(row);				
			FileUpdate_GUI update = new FileUpdate_GUI(nav.getFrame(), false);
			new FileUpdateController(update, file);
			update.setVisible(true);
			gui.getTable().clearSelection();
			
		}
	}



	
	public void viewWillAppear() {

		try {
			Message getFiles = new Message(null, MessageType.GET_FILES);
			Client.getInstance().sendMessage(getFiles);
		} catch (IOException e) {}
		
	}

	public void btnAddFileClicked() {
		// TODO Auto-generated method stub
		
	}

	public void btnDeleteFileClicked() {
		// TODO Auto-generated method stub
		
	}

	public void btnGroupsClicked() {
		// TODO Auto-generated method stub
		
	}

	public void btnNewFolderClicked() {
		// TODO Auto-generated method stub
		
	}

	public void btnRestoreFileClicked() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Boundary initBoundary() {
		return new MyBox_GUI(this);
	}
}
