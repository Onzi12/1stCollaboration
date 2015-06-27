package unittests;

import static org.junit.Assert.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import controller.LoginController;
import boundary.Login_GUI;

public class LoginWrongPasswordTest {
	LoginController control;
	Login_GUI gui;
	private CountDownLatch lock = new CountDownLatch(1);
	
	@Test
	public void test() {
		control = new LoginController();
		gui = (Login_GUI) control.getGui();
		
		gui.setUsername("On01");
		gui.setPassword("123");
		
		control.btnSignInClicked();
		try {
			lock.await(2000,TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		assertFalse(control.getIsSuccess());

	}

}
