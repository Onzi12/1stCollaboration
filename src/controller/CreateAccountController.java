package controller;


import java.io.IOException;
import model.User;
import boundary.CreateAccount_GUI;
import callback.LoginCallback;
import client.Client;
import common.Boundary;
import common.Controller;
import common.Message;
import common.MessageType;

public class CreateAccountController extends Controller {
	


	public void btnCreateAccountClicked() {
		
		final CreateAccount_GUI gui = (CreateAccount_GUI)getGui();
		
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
						gui.showMessage(message);
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
			gui.showMessage("Failed to connect!");
			client.disconnect();
			return false;
		}
		return true;
	}
	

	
	private void handleUserSignIn(User user) {
		if (user != null) {

			//here user has logged in successfully					
			//show MyBox Window
//			nav.getFrame().setSize(742, 579);
//			MyBox_GUI myBox = new MyBox_GUI();
//			MyBoxController controller = new MyBoxController(myBox, user);
			Client.getInstance().setUser(user);
			new MyBoxController(user);
//			Client.getInstance().addObserver(controller);
//			nav.replaceController(controller);
			
			
		} /* else?? */
	}

	@Override
	protected Boundary initBoundary() {
		return new CreateAccount_GUI(this);
	}

	public void btnShowSignInClicked() {
		NavigationManager.getInstance().popController();
	}




}
