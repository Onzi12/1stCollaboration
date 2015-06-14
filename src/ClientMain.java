import boundary.AppFrame;
import controller.LoginController;


public class ClientMain {

	public static void main(String[] args) {
		AppFrame frame = AppFrame.getInstance();
		new LoginController();
		frame.setVisible(true);
	}

}
