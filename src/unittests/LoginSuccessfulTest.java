package unittests;

import static org.junit.Assert.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import controller.LoginController;
import boundary.Login_GUI;

public class LoginSuccessfulTest {

	LoginController control;
	Login_GUI gui;
	private CountDownLatch lock = new CountDownLatch(1);
	
	@Test
	public void test() {
		control = new LoginController();
		gui = (Login_GUI) control.getGui();
		
		gui.setUsername("Gil01");
		gui.setPassword("1234");
		
		control.btnSignInClicked();
		
		try {
			lock.await(2000,TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		assertTrue(control.getIsSuccess());
		
	}

}
