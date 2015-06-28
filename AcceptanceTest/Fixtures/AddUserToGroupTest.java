package Fixtures;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import callback.SendRequestsCallback;
import common.MyBoxException;
import boundary.Login_GUI;
import controller.GroupsController;
import controller.LoginController;
import controller.MyBoxController;
import controller.NavigationManager;
import fit.ActionFixture;

public class AddUserToGroupTest extends ActionFixture {

	private LoginController loginController;
	private GroupsController groupsController;
	private MyBoxController myboxController;
	private int selectedGroupIndex;
	private Login_GUI loginGui;
	private CountDownLatch lock = new CountDownLatch(1);
	private String msg;
	
	public void selectGroup(int inedx) {
		selectedGroupIndex = inedx;
	}
	
	public String checkWarningMessage() {

		loginController = new LoginController();
		loginGui = (Login_GUI)loginController.getGui();
		loginGui.setUsername("user");
		loginGui.setPassword("1234");
		loginController.btnSignInClicked();
		
		try {
			lock.await(2000,TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		myboxController = (MyBoxController)NavigationManager.getInstance().getCurrentController();
		groupsController = new GroupsController();
		groupsController.joinGroupAtIndex(selectedGroupIndex);
		
		groupsController.sendRequests(new SendRequestsCallback() {

			@Override
			protected void messageReceived(String message, MyBoxException exception) {
				if (exception == null) {
					msg = message;
				} else {
					msg = exception.getMessage();
				}
			}
			
		});
		
		try {
			lock.await(2000,TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		myboxController.logout();
		
		try {
			lock.await(2000,TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return msg;
	}
	
}
