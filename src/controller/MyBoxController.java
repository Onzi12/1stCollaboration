package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import model.Item;
import model.ItemFile;
import model.ItemFolder;
import model.User;
import boundary.AppFrame;
import boundary.FilePopUpMenu_GUI;
import boundary.FileTreeModel;
import boundary.FileTreeModelListenter;
import boundary.MyBox_GUI;
import client.Client;

import common.Boundary;
import common.Controller;
import common.Message;
import common.MessageType;

public class MyBoxController extends Controller implements Observer {

	private User user;
	MyBox_GUI gui;
	
	public MyBoxController(User user) {

		this.user = user;
		this.gui = (MyBox_GUI)super.gui;

	}



	private class MyBoxMouseListener extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			gui.getTable().clearSelection();
			gui.getTree().clearSelection();
		}
	}
	
	private class TableMouseListener extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (!e.isPopupTrigger() && e.getClickCount() == 2 && !e.isConsumed()) { /*Gil, what are all this checks?*/
				int row = gui.getTable().rowAtPoint(e.getPoint());
		        if (row < 0)
		            return;
		        
				ItemFile file = (ItemFile)gui.tableGetFile(row);
				new FileReadController(file);
				
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

			Message msg = (Message) arg;
			MessageType type = msg.getType();
			
			switch (type) {
			case GET_FILES:
			{				
				@SuppressWarnings("unchecked")
				HashMap<String, Item> items = (HashMap<String, Item>)msg.getData();
				

				ArrayList<Item> files = new ArrayList<Item>();
				for (Item item : items.values()) {
					
					ItemFolder directory = (ItemFolder) items.get("folder" + Integer.toString(item.getFolderID()));
					if (directory != null) {
						directory.addFile(item);
					}
					
					if (item instanceof ItemFile) {
						files.add(item);
					}
				}
				
				user.setFiles(items);

				// update the tree 
				processTreeHierarchy(user.getFiles());
				
				// update the table
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
					getGui().showMessage("Please try to connect again later.");
					logout();
				}
				
				break;
				
			case UPLOAD_FILE: 
				
				System.out.println("UPLOAD_FILE");
				
				ItemFile file = (ItemFile)msg.getData();

				user.getFiles().put(file.getStringID(), file);
				
				ArrayList<Item> files = new ArrayList<Item>();
				files.addAll(user.getFiles().values());
				
				gui.refreshTable(files);
				
				break;
				
			default:
				break;
			}
		}
		
	}

	public void processTreeHierarchy(HashMap<String, Item> items) {

		DefaultMutableTreeNode root = (DefaultMutableTreeNode)gui.getTree().getModel().getRoot();
		
		for (Item item : items.values()) {
			
			if (item instanceof ItemFolder) {
				
				if (item.getFolderID() == 0) {
					addObject(root, item, false);
				} else {
					
					
					
				}
				
			}
			
		}
		
		// expand the root folder
		TreePath path = new TreePath(root);
		gui.getTree().expandPath(path);
		
	}
	
	// Add a child by a selection path
	public DefaultMutableTreeNode addObject(Object child) {

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
	    TreePath path = gui.getTree().getSelectionPath();
	    if (path != null) {
			DefaultMutableTreeNode nodeToRemove = (DefaultMutableTreeNode)path.getLastPathComponent();	    
			FileTreeModel model = (FileTreeModel)gui.getTree().getModel();
			model.removeNodeFromParent(nodeToRemove);
	    }
	}
	
	public void btnLogoutClicked() {
		int answer = JOptionPane.showConfirmDialog(gui, "Are you sure you want to logout?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (answer == JOptionPane.YES_OPTION) {
			logout();
		}
	}
	
	public void logout() {
		Client client = Client.getInstance();
		
		try {
			Message logout = new Message(user, MessageType.LOGOUT);
			client.sendMessage(logout);
		} catch (IOException e) {
			System.out.println("ERROR " + e.getMessage());
		}
		
		client.setUser(null);
		client.deleteObservers();
		client.disconnect();
		
		java.awt.Window win[] = java.awt.Window.getWindows(); // close all windows and return to Login window
		for (int i = 0; i< win.length; i++) 
			if (!(win[i] instanceof boundary.AppFrame))
				win[i].dispose();
		
		new LoginController();		
		
	}
	
	public void btnUpdateFileClicked() {

		int row = gui.getTable().getSelectedRow();
		
		if (row < 0) 
			new FileUpdateController(null);  //no File is pressed == createNewFile
												   
		 else {
			ItemFile file = (ItemFile)gui.tableGetFile(row);				
			new FileUpdateController(file);  
			gui.getTable().clearSelection();
			
		}
	}



	@Override
	public void updateBoundary() {
		Client.getInstance().addObserver(this);

		try {
			Message getFiles = new Message(Client.getInstance().getUser(), MessageType.GET_FILES);
			Client.getInstance().sendMessage(getFiles);
		} catch (IOException e) {}
		
	}

	public void btnAddFileClicked() {
		new FileAddController();	
	}

	public void btnDeleteFileClicked() {
		// TODO Auto-generated method stub
		
	}

	public void btnGroupsClicked() {
		new GroupsController();	
	}

	public void btnNewFolderClicked() {
		// TODO Auto-generated method stub
		
	}

	public void btnRestoreFileClicked() {
		new FileRestoreController();
		
	}

	@Override
	protected void registerMoreListeners() {
		((MyBox_GUI)getGui()).registerTableMouseListener(new TableMouseListener());
		((MyBox_GUI)getGui()).registerMouseListener(new MyBoxMouseListener());
		((MyBox_GUI)getGui()).registerTreeModeListener(new FileTreeModelListenter());
	}
	
	
	@Override
	protected Boundary initBoundary() {
		return new MyBox_GUI(this);
	}
}
