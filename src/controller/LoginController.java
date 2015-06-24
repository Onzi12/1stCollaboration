package controller;


import java.io.IOException;

import model.User;
import boundary.AppFrame;
import boundary.Login_GUI;
import callback.LoginCallback;
import client.Client;

import common.Boundary;
import common.Controller;
import common.Message;
import common.MessageType;
import common.MyBoxException;

public class LoginController extends Controller {

		
	private void handleUserSignIn(User user) {

			Client.getInstance().setUser(user);
			if (user.isAdmin() == true )
				new MyBoxAdministratorController(user);
			else	
				new MyBoxController();
 
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
				User user = new User();
				user.setUserName(gui.getUsernameText());
				user.setPassword(gui.getPasswordText());
				Message msg = new Message(user, MessageType.LOGIN);
				Client.getInstance().sendMessage(msg, new LoginCallback() {
					
					@Override
					protected void done(User user, MyBoxException exception) {
						
						if (exception == null) {
							handleUserSignIn(user);
						} else {
							getGui().showMessage(exception.getMessage());
							boolean clearName = true;
							if(exception.getMessage().contains("Password is incorrect!") ) {
								clearName = false;
//								((Login_GUI)getGui()).fieldPassword.requestFocusInWindow();
								}
							((Login_GUI)getGui()).clearFields(clearName);
						}
						
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



	public void btnCreateAnAccountclicked() {
		new CreateAccountController();
	}


	@Override
	protected Boundary initBoundary() {
		return new Login_GUI(this);
	}
	
	@Override
	public void updateBoundary() {
		AppFrame.getInstance().setSize(326, 400);
		Login_GUI g = (Login_GUI)getGui();
		g.clearFields(false);
	}
	
}
