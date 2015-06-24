package controller;


import java.io.IOException;

import model.User;
import model.User.Status;
import boundary.CreateAccount_GUI;
import callback.Callback;
import client.Client;

import common.Boundary;
import common.Controller;
import common.Message;
import common.MessageType;
import common.MyBoxException;

public class CreateAccountController extends Controller {
	


	public void btnCreateAccountClicked() {
		
		final CreateAccount_GUI gui = (CreateAccount_GUI)getGui();
		
		if( gui.getUsernameText().equals("") ||
			gui.getPasswordText().length()==0 )
		{
			
			gui.showMessage("Please fill all fields!");
			return; 
			
		} 
		
		if (initClient()) {
			try {
				
				User user = new User();
				user.setUserName(gui.getUsernameText()); 
				user.setPassword(gui.getPasswordText());
				user.setAdmin(false);
				user.setCounter(0);
				user.setStatus(Status.NOTCONNECTED);
				Message msg = new Message(user, MessageType.CREATE_ACCOUNT);
				Client.getInstance().sendMessage(msg, new Callback<Void>() {

					@Override
					protected void messageReceived(Void obj, MyBoxException exception) {
						if (exception == null) {
							//show Login again
							NavigationManager.getInstance().popController();
						} else {
							getGui().showMessage(exception.getMessage());
						}
					}

					@Override
					protected MessageType getMessageType() {
						return MessageType.CREATE_ACCOUNT;
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
			gui.showMessage("Failed to connect!");
			client.disconnect();
			return false;
		}
		return true;
	}

	@Override
	protected Boundary initBoundary() {
		return new CreateAccount_GUI(this);
	}

	public void btnShowSignInClicked() {
		NavigationManager.getInstance().popController();
	}
}
