package controller;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JButton;

import model.User;
import boundary.AppFrame;
import boundary.CreateAccount_GUI;
import boundary.Login_GUI;
import boundary.MyBox_GUI;
import boundary.PortAndIP_GUI;
import callback.LoginCallback;
import client.Client;
import common.Boundary;
import common.Controller;
import common.Message;
import common.MessageType;

public class LoginController extends Controller {

		
	private void handleUserSignIn(User user) {
		if (user != null) {

			//here user has logged in successfully					
			//show MyBox Window
//			getNavigationManager().getFrame().setSize(742, 579);
//			MyBox_GUI myBox = new MyBox_GUI();
//			Controller controller = new MyBoxController(myBox, user);
//			//Client.getInstance().addObserver(controller);
//			getNavigationManager().replaceController(controller);
			new MyBoxController();
			
		} 
	}
	
	public void btnSetPortAndIPClicked() {
		new PortAndIPController();
		
	}
	
	public void btnSignInClicked() {
		Login_GUI gui = (Login_GUI)getGui();
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
						((Login_GUI)getGui()).showMessage(message);
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
			((Login_GUI)getGui()).showMessage("Failed to connect!");
			client.disconnect();
			return false;
		}
		return true;
	}


/*	public void actionPerformed(ActionEvent e) {
		
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
			new CreateAccountController();
			
			break;
			
		default:
			break;
		}
		
	}*/


	public void btnCreateAnAccountclicked() {
		new CreateAccountController();
	}




	@Override
	protected Boundary initBoundary() {
		return new Login_GUI(this);
	}
	
}
