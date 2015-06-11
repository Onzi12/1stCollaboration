import controller.LoginController;
import controller.NavigationManager;
import boundary.AppFrame;


public class ClientMain {

	public static void main(String[] args) {
		AppFrame frame = AppFrame.getInstance();
		NavigationManager.getInstance().pushController(new LoginController());
		frame.setVisible(true);
	}

}
