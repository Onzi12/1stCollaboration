package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import model.ItemFile;
import boundary.FilePopUpMenu_GUI;
import boundary.MyBox_GUI;
import callback.CanEditFileCallback;
import client.Client;

import common.Message;
import common.MessageType;
import common.MyBoxException;

public class FilePopUpMenuController {
	
	private final ItemFile file;
	private MyBoxController myboxController;
	
	public FilePopUpMenuController(MyBoxController myboxController, FilePopUpMenu_GUI gui, ItemFile file) {
		this.file = file;
		this.myboxController = myboxController;
		gui.registerMenuItemDeleteListener(new BtnDeleteFileActionListener());
		gui.registerMenuItemUpdateListener(new BtnUpdateFileActionListener());
		gui.registerMenuItemReadListener(new BtnReadFileActionListener());
		gui.registerMenuItemEditListener(new BtnEditFileActionListener());
		gui.registerMenuItemMovetoListener(new BtnMovetoActionListener());
	}
	private class BtnEditFileActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			btnEditClicked();
			
		}
		
	}
	private class BtnReadFileActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			btnReadClicked();
		}	
	}
	
	private class BtnDeleteFileActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			btnDeleteFileClicked();
		}
	
	}
	
	private class BtnUpdateFileActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			btnUpdateClicked();				
		}	
	}
	
	private class BtnMovetoActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			btnMovetoClicked();
			
		}

		private void btnMovetoClicked() {
			new VirtualLocationChooserController(myboxController, file);			
		}
		
	}
	public void btnUpdateClicked() {

		try {
			Message msg = new Message(file.getID(), MessageType.CAN_EDIT_FILE);
			Client.getInstance().sendMessage(msg, new CanEditFileCallback() {
				
				@Override
				public void canEditFile(ItemFile file, MyBoxException exception) {
					if (exception == null) {
						file.setIsEdited(true);
						new FileUpdateController(file);
					} else {
						((MyBox_GUI)myboxController.getGui()).showMessage(exception.getMessage());
					}
					((MyBox_GUI)myboxController.getGui()).getTable().clearSelection();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void btnDeleteFileClicked() {
		MyBoxController control = (MyBoxController)NavigationManager.getInstance().getCurrentController();
		control.FileDeleteVirtualControl(file);
		((MyBox_GUI)myboxController.getGui()).getTable().clearSelection();
		
	}

	public void btnEditClicked() {
		
		try {
			Message msg = new Message(file.getID(), MessageType.CAN_EDIT_FILE);
			Client.getInstance().sendMessage(msg, new CanEditFileCallback() {
				
				@Override
				public void canEditFile(ItemFile fileDB, MyBoxException exception) {
					if (exception == null) {
						file.setIsEdited(true);
						 new FileEditController(file);
					} else {
						((MyBox_GUI)myboxController.getGui()).showMessage(exception.getMessage());
					}
					 ((MyBox_GUI)myboxController.getGui()).getTable().clearSelection();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void btnReadClicked() {
		new FileReadController(file);
	}
	
}
