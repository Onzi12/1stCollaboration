package controller;


import java.io.IOException;

import model.User;
import boundary.Login_GUI;
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
			Client.getInstance().setUser(user);
			if (user.getAdmin() == 1)
				new MyBoxAdministratorController(user);
			else	
				new MyBoxController(user);
		} else {
			System.out.println("WHY??????");
		}
		//else ?? 
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
						getGui().showMessage(message);
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



	public void btnCreateAnAccountclicked() {
		new CreateAccountController();
	}


	@Override
	protected Boundary initBoundary() {
		return new Login_GUI(this);
	}
	
}
