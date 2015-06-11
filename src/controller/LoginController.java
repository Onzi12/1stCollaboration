package controller;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JButton;

import model.User;
import boundary.CreateAccount_GUI;
import boundary.Login_GUI;
import boundary.MyBox_GUI;
import boundary.PortAndIP_GUI;
import callback.LoginCallback;
import client.Client;

import common.Message;
import common.MessageType;

public class LoginController extends MyController {
	
	public LoginController(Login_GUI gui) {
		super(gui);
		
		gui.registerSignInListener(this);
		gui.registerIPAndPortListener(this);
		gui.registerShowCreateAccountListener(this);
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
	
	private void btnSetPortAndIPClicked() {
		PortAndIP_GUI portAndIP_GUI = new PortAndIP_GUI(getNavigationManager().getFrame());
		new PortAndIPController(portAndIP_GUI);
		portAndIP_GUI.setVisible(true);
	}
	
	private void btnSignInClicked() {
		Login_GUI gui = (Login_GUI)getPanel();
		if( gui.getUsernameText().equals("") || gui.getPasswordText().length()==0 ) { 
			gui.showMessage("Please fill all fields!");
			return; 
		} 

		if (initClient()) {
			try {
				User user = new User(gui.getUsernameText(), gui.getPasswordText());
				Message msg = new Message(user, MessageType.LOGIN);
				Client.getInstance().sendMessage(msg, new LoginCallback() {
					
					@Override
					public void userSignIn(User user) {
						handleUserSignIn(user);
					}

					@Override
					public void error(String message) {
						((Login_GUI)getPanel()).showMessage(message);
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
		case Login_GUI.ACTION_COMMAND_SIGN_IN:
			btnSignInClicked();
			break;
			
		case Login_GUI.ACTION_COMMAND_IP_PORT:
			btnSetPortAndIPClicked();
			break;
			
		case Login_GUI.ACTION_COMMAND_SHOW_CREATE_ACCOUNT:
			
			CreateAccount_GUI createAccount = new CreateAccount_GUI();
			CreateAccountController controller = new CreateAccountController(createAccount);
			getNavigationManager().replaceController(controller);
			
			break;
			
		default:
			break;
		}
		
	}

	@Override
	public void viewWillAppear() {}
	
}
