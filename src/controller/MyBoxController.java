 package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import model.Item;
import model.ItemFile;
import model.ItemFolder;
import model.User;
import boundary.FilePopUpMenu_GUI;
import boundary.FileTreeModel;
import boundary.MyBox_GUI;
import callback.CanEditFileCallback;
import callback.ChangeFolderNameCallback;
import callback.CreateNewFolderCallback;
import callback.FileDeleteCallback;
import callback.GetFilesCallback;
import callback.RemoveFolderCallback;
import callback.UpdateFileLocationCallback;
import client.Client;

import common.Boundary;
import common.Controller;
import common.Message;
import common.MessageType;
import common.MyBoxException;

public class MyBoxController extends Controller implements Observer {

	MyBox_GUI gui;
	
	public MyBoxController() {

		// listen to broadcast messages.
		Client.getInstance().addObserver(this); 
		this.gui = (MyBox_GUI)super.gui;

	}

	public ItemFolder getCurrentFolder() {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)gui.getTree().getLastSelectedPathComponent();
		if (node != null) 
			return (ItemFolder)node.getUserObject();
		return null;
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
			if (!e.isPopupTrigger() && e.getClickCount() == 2 && !e.isConsumed()) { 
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

	
	public void clickedOnTreeNode() {
		
		showFilesOfSelectedFolder();
		
	}
	
	/**
	 * Show the files in the selected folder.
	 */
	private void showFilesOfSelectedFolder() {
		
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)gui.getTree().getLastSelectedPathComponent(); 
		if (selectedNode != null) {
			
			if (selectedNode.getUserObject() != null) {
				if (selectedNode.getUserObject() instanceof ItemFolder) {
					ItemFolder folder = (ItemFolder)selectedNode.getUserObject();
					
					if (folder != null) {
						
						ArrayList<Item> files = new ArrayList<Item>();
						Set<Item> items = folder.getContents(); 
						if (items != null) {
							
							for (Item item : items) {
								if (item instanceof ItemFile) {
									files.add(item);
								}
							}
							
							
						}
						gui.refreshTable(files);
					}

				}
			}
			
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
			Message logout = new Message(Client.getInstance().getUser(), MessageType.LOGOUT);
			client.sendMessage(logout, null);
		} catch (IOException e) {
			e.printStackTrace();
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
			final ItemFile file = (ItemFile)gui.tableGetFile(row);				

			try {
				Message canUpdateFile = new Message(file.getID(), MessageType.CAN_EDIT_FILE);
				Client.getInstance().sendMessage(canUpdateFile, new CanEditFileCallback() {
					
					@Override
					public void canEditFile(ItemFile fileDB, MyBoxException exception) {
						
						if (exception == null) {
							file.setIsEdited(true);
							new FileUpdateController(file);  
						} else {
							gui.showMessage(exception.getMessage());
						}
						gui.getTable().clearSelection();
					}
				});
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}



	@Override
	public void updateBoundary() {

		try {
			Message getFiles = new Message(Client.getInstance().getUser(), MessageType.GET_FILES);
			Client.getInstance().sendMessage(getFiles, new GetFilesCallback() {
				
				@Override
				protected void done(ItemFolder folder, MyBoxException exception) {
					if (exception == null) {
						Client.getInstance().getUser().setRootFolder(folder);
						ItemFolder f = getCurrentFolder(); // the current selected folder
						initViewWithItemsFromDB();
						if (f != null) {
							gui.getTree().showFolder(f);
				    		showFilesOfSelectedFolder();
						}							
					} else {
						getGui().showMessage(exception.getMessage());
					}
				}
			}); 
		} catch (IOException e) {}
		
	}
	
	public void handleUpdateFileCallback(ItemFile file, MyBoxException exception) {
		if (exception == null) {
			
			updateView();
			showFilesOfSelectedFolder();
			
		} else {
			getGui().showMessage(exception.getMessage());
		}
	}
	
	public void handleUploadedFileCallback(ItemFile file, MyBoxException exception) {
		
		if (exception == null) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode)gui.getTree().getLastSelectedPathComponent();
			ItemFolder f = (ItemFolder)node.getUserObject();
			f.addItem(file);
			updateView();
			showFilesOfSelectedFolder();
			
		} else {
			getGui().showMessage(exception.getMessage());
		}
	}
	
	private void initViewWithItemsFromDB() {

		// update the tree 
		ItemFolder rootFolder = Client.getInstance().getUser().getRootFolder();
		rootFolder.setTreeNode(new DefaultMutableTreeNode(rootFolder));
		gui.getTree().setModel(new FileTreeModel(rootFolder.getTreeNode()));
		gui.getTree().processTreeHierarchy(rootFolder);
		
		// expand the root folder
		TreePath path = new TreePath(gui.getTree().getRoot());
		gui.getTree().expandPath(path);
		gui.getTree().setSelectionPath(path);
		
		// update the table
		showFilesOfSelectedFolder();
	}

	public void btnAddFileClicked() {
		new FileAddController();	
	}

	public void btnDeleteFileClicked() {
		new FileDeleteController();
	}
	

	public void btnGroupsClicked() {
		new GroupsController();	
	}
	


	/**
	 * Create new Tree node and make it editable.
	 */
	public void btnNewFolderClicked() {
		gui.disableBtnNewFolder();
		DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode)gui.getTree().getLastSelectedPathComponent();
	    
	    if (parentNode == null) {
	    	parentNode = gui.getTree().getRoot();
	    } 
	    
	    if (parentNode != null) {
	    					
	    	ItemFolder parentFolder = (ItemFolder)parentNode.getUserObject();
	    	ItemFolder newFolder = new ItemFolder();
	    	newFolder.setID(0);
	    	newFolder.setParentID(parentFolder.getID());
	    	newFolder.setUserID(Client.getInstance().getUser().getID());
	    	
	    	DefaultMutableTreeNode newTreeNode = gui.getTree().addObject(parentNode, newFolder, true);
			newFolder.setTreeNode(newTreeNode);
			
			TreePath path = new TreePath(newTreeNode.getPath());

			if (path != null) {
				gui.getTree().startEditingAtPath(path);
				showFilesOfSelectedFolder();
			}
	    }

	}
	
	/**
	 * Called after user finishes entering new folder's name.
	 * @param folder
	 */
	public void finishedEditingFolderName(final ItemFolder folder) {


		// create new folder
		if (folder.getID() == 0) {
			
			try {
				Message msg = new Message(folder, MessageType.CREATE_NEW_FOLDER);
				Client.getInstance().sendMessage(msg, new CreateNewFolderCallback() {
					
					@Override
					protected void done(ItemFolder f, MyBoxException exception) {
						
						if (exception == null) {
							
							updateView();

						} else {
						    gui.getTree().removeObject();
							getGui().showMessage(exception.getMessage());
						}
						gui.enableBtnNewFolder();
					}
				});
			} catch (IOException e) {}
			
		} else { // rename folder
						
			try {
				Message msg = new Message(folder, MessageType.CHANGE_FOLDER_NAME);
				Client.getInstance().sendMessage(msg, new ChangeFolderNameCallback() {
					
					@Override
					protected void done(ItemFolder folder, MyBoxException exception) {

					}
				});
			} catch (IOException e) {}
			
		}

	}

	public void btnRestoreFileClicked() {
		new FileRestoreController();
		
	}

	@Override
	protected void registerMoreListeners() {
		((MyBox_GUI)getGui()).registerTableMouseListener(new TableMouseListener());
		((MyBox_GUI)getGui()).registerMouseListener(new MyBoxMouseListener());

	}
	
	
	@Override
	protected Boundary initBoundary() {
		return new MyBox_GUI(this);
	}

	/**
	 * Called from FileEditController after file edit
	 * @param file
	 * @param exception
	 */
	public void handleEditFileCallback(ItemFile file, MyBoxException exception) {
	
		if (exception == null) {
//			Client.getInstance().getUser().getFiles().put("file" + file.getStringID(), file);	
//			ItemFolder folder = (ItemFolder) Client.getInstance().getUser().getFiles().get("folder" + Integer.toString(file.getFolderID()));
//			folder.addFile(file);
			showFilesOfSelectedFolder();
		} else {
			getGui().showMessage(exception.getMessage());
		}
		
	}

	/**
	 * Move file from folder to folder 
	 * @param folder
	 * @param file
	 */
	public void moveFileToFolder(ItemFolder folder, ItemFile file) {

		if (file != null) {
			
		    DefaultMutableTreeNode node = (DefaultMutableTreeNode)gui.getTree().getLastSelectedPathComponent();
		    ItemFolder oldFolder = (ItemFolder)node.getUserObject();

			if (folder.getID() != oldFolder.getID()) {
				
				oldFolder.removeItem(file);
				folder.addItem(file);
				file.setParentID(folder.getID());
								
				HashMap<String, Object> data = new HashMap<String, Object>();
				data.put("oldParentID", oldFolder.getID());
				data.put("file", file);
				data.put("userID", Client.getInstance().getUser().getID());

				try {
					Message msg = new Message(data, MessageType.UPDATE_FILE_LOCATION);
					Client.getInstance().sendMessage(msg, new UpdateFileLocationCallback() {
						
						@Override
						protected void done(ItemFile file, MyBoxException exception) {
							if (exception == null) {
								
								showFilesOfSelectedFolder();
								
							} else {
								getGui().showMessage(exception.getMessage());
							}
						}
					});
				} catch (IOException e) {} 
				
			}
						
		}

	}


	public void handleDeleteFileCallback(ItemFile file, MyBoxException exception) {
		

		if (exception == null){
	
		    DefaultMutableTreeNode node = (DefaultMutableTreeNode)gui.getTree().getLastSelectedPathComponent();
		    ItemFolder folder = (ItemFolder)node.getUserObject();
			folder.removeItem(file);
			updateView();
			showFilesOfSelectedFolder();	
		}else {
			getGui().showMessage(exception.getMessage());			
		}
		
	}

	public void FileDeleteVirtualControl(ItemFile file) {
		try{
		    DefaultMutableTreeNode node = (DefaultMutableTreeNode)gui.getTree().getLastSelectedPathComponent();
		    ItemFolder folder = (ItemFolder)node.getUserObject();
			HashMap<String, Object> data = new HashMap<String, Object>();
			data.put("file", file);
			data.put("userID", Client.getInstance().getUser().getID());
			data.put("parentID", folder.getID());
			Message msg = new Message(data, MessageType.DELETE_FILE_VIRTUAL);
			Client.getInstance().sendMessage(msg, new FileDeleteCallback() {
				
				@Override
				protected void done(ItemFile file, MyBoxException exception) {
					handleDeleteFileCallback(file, exception);		
					
				}
				
				@Override
				protected MessageType getMessageType() {
					return MessageType.DELETE_FILE_VIRTUAL;
				}
				
			});		
		} catch(IOException e) {
			e.printStackTrace();
			
		}
		
	}

	public void handleAddFileCallback(ItemFile file, MyBoxException exception) {
		if (exception == null){

			updateView();
			
		} else {
			getGui().showMessage(exception.getMessage());
		}
		
	}
	
	public void handleRestoreFileCallback(MyBoxException exception) {
		if (exception == null){
			updateView();
		} else {
			getGui().showMessage(exception.getMessage());
		}
		
	}
	
	public void updateView() {
		updateBoundary();
	}
	
	@Override
	public void update(Observable o, Object arg) {
		
		if (arg instanceof Message) {

			Message msg = (Message) arg;
			MessageType type = msg.getType();
			
			switch (type) {
			
			case BROADCAST_FILE_STATE_CHANGE:
			{

				@SuppressWarnings("unchecked")
				Set<User> users = (Set<User>)msg.getData();
				if ( users.contains(Client.getInstance().getUser()) ) {
					updateView();
				}
				
			}
				break;

			case ERROR_MESSAGE:
				
				String str = (String)msg.getData();
				if (str.equals(Client.CONNECTION_EXCEPTION)) {
					getGui().showMessage("Please try to connect again later.");
					logout();
				}
				
				break;
				
			default:
				break;
			}
		}
		
	}

	public void btnRemoveFolderClicked() {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) gui.getTree().getLastSelectedPathComponent();
		DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode)node.getParent();
		
		ItemFolder folder = ((ItemFolder) node.getUserObject());
		if(parentNode == null) {
			gui.showMessage("root folder cannot be deleted!");
			return;	
		}
		final ItemFolder pfolder = ((ItemFolder) parentNode.getUserObject());
		
		if ((folder.getContents() == null || folder.getContents().size() == 0) && node != null )
		{	
				Message msg = new Message(folder, MessageType.REMOVE_FOLDER);
				try {
					Client.getInstance().sendMessage(msg, new RemoveFolderCallback() {
						
						@Override
						protected void done(ItemFolder folder, MyBoxException exception) {
							 if (exception == null){
								 gui.getTree().removeObject();
								 pfolder.removeItem(folder);
							 } else exception.printStackTrace();
						}
					});
				} catch (IOException e) {
					e.printStackTrace();
				}
		}else {
			gui.showMessage("Folder must be empty to be Removed");
		}
		
	}
}
