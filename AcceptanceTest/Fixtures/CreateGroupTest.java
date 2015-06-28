package Fixtures;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import callback.CreateNewGroupCallback;
import common.MyBoxException;
import boundary.Login_GUI;
import model.Group;
import controller.LoginController;
import controller.MyBoxAdministratorController;
import controller.NavigationManager;
import fit.ActionFixture;

public class CreateGroupTest extends ActionFixture{

	LoginController loginController;
	MyBoxAdministratorController myboxController;
	Login_GUI loginGui;
	private CountDownLatch lock = new CountDownLatch(1);
	String name;
	boolean isCreated;
	public void startGroup()
	{

	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public boolean createGroup() {

		
		loginController = new LoginController();
		loginGui = (Login_GUI)loginController.getGui();
		loginGui.setUsername("admin");
		loginGui.setPassword("0000");
		loginController.btnSignInClicked();
		
		try {
			lock.await(2000,TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		myboxController = (MyBoxAdministratorController)NavigationManager.getInstance().getCurrentController();
		
		

		myboxController.createGroup(name, new CreateNewGroupCallback() {
			@Override
			public void done(Group group, MyBoxException exception) {
					
					if (exception == null) {
						isCreated = name.equals(group.getName());
						
					} else 
					{
						isCreated = false;
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

		return isCreated;
	}
}
