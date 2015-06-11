import boundary.AppFrame;
import boundary.Server_GUI;
import controller.LoginController;
import controller.NavigationManager;
import controller.ServerController;


public class ServerMain {

	public static void main(String[] args) {
		Server_GUI server_gui = new Server_GUI();
		new ServerController(server_gui);
		server_gui.setVisible(true);
	}

}
