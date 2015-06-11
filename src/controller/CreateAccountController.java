package controller;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JButton;

import model.User;
import boundary.CreateAccount_GUI;
import boundary.Login_GUI;
import boundary.MyBox_GUI;
import callback.LoginCallback;
import client.Client;
import common.Controller;
import common.Message;
import common.MessageType;

public class CreateAccountController extends Controller {
	
	public CreateAccountController(CreateAccount_GUI gui) {
		super(gui);
		
		gui.registerCreateAccountListener(this);
		gui.registerShowSingInListener(this);
	}	

	private void btnCreateAccountClicked() {
		
		CreateAccount_GUI gui = (CreateAccount_GUI)getPanel();
		
		if( gui.getUsernameText().equals("") ||
			gui.getPasswordText().length()==0 || 
			gui.getFirstNameText().equals("") ||
			gui.getLastNameText().equals("") ||
			gui.getEmailText().equals("") ) 
		{
			
			gui.showMessage("Please fill all fields!");
			return; 
			
		} 
		
		if (initClient()) {
			try {
				User user = new User(gui.getUsernameText(), gui.getPasswordText());
				user.setEmail(gui.getEmailText());
				user.setFirstName(gui.getFirstNameText());
				user.setLastName(gui.getLastNameText());
				Message msg = new Message(user, MessageType.CREATE_ACCOUNT);
				Client.getInstance().sendMessage(msg, new LoginCallback() {
					
					@Override
					public void userSignIn(User user) {
						handleUserSignIn(user);
					}
					
					@Override
					public void error(String message) {
						((CreateAccount_GUI)getPanel()).showMessage(message);
						Client.getInstance().deleteObservers();
					}
				});
			} catch (IOException e) {
				gui.showMessage("Failed to connect!");
			}
		}
	}
	
	private boolean initClient() {
		Client client = Client.getInstance();
		client.deleteObservers();
		try {
			client.connect();
		} catch (IOException e) {
			((Login_GUI)getPanel()).showMessage("Failed to connect!");
			client.disconnect();
			return false;
		}
		return true;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		JButton btn = (JButton)e.getSource();
		String actionCommand = btn.getActionCommand();
		
		switch (actionCommand) {
		
		case CreateAccount_GUI.ACTION_COMMAND_CREATE_ACCOUNT:
			btnCreateAccountClicked();
			break;
			
		case CreateAccount_GUI.ACTION_COMMAND_SHOW_SIGN_IN:

			Login_GUI loginPanel = new Login_GUI();
			LoginController loginController = new LoginController(loginPanel);
			getNavigationManager().replaceController(loginController);
			
			break;
			
		default:
			break;
		}
		
	}
	
	private void handleUserSignIn(User user) {
		if (user != null) {

			//here user has logged in successfully					
			//show MyBox Window
			getNavigationManager().getFrame().setSize(742, 579);
			MyBox_GUI myBox = new MyBox_GUI();
			MyBoxController controller = new MyBoxController(myBox, user);
			Client.getInstance().addObserver(controller);
			getNavigationManager().replaceController(controller);
			
		} 
	}

	@Override
	public void viewWillAppear() {}

	@Override
	protected void initBoundary() {
		// TODO Auto-generated method stub
		
	}

}
