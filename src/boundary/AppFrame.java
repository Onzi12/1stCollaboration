package boundary;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import common.Boundary;
import common.JPanelBoundary;
import client.Client;
import controller.LoginController;
import controller.NavigationManager;

public class AppFrame extends JFrame{

	private static final long serialVersionUID = 351110661931287206L;
	private static AppFrame instance = null;
	

	
	final public void presentView(JPanelBoundary panel) {
		getContentPane().add(panel);
		getContentPane().revalidate();
		getContentPane().repaint();
	}

	final public void dismissView(JPanelBoundary panel) {
		getContentPane().remove(panel);
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		AppFrame frame = new AppFrame();
		frame.setVisible(true);
	}

	/**
	 * Create the frame.
	 */
	private AppFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 326, 419);
		setLocationRelativeTo(null);
		setResizable(false);
		
		//nav = NavigationManager.getInstance();
		
		//Login_GUI loginPanel = new Login_GUI();
		//LoginController loginController = new LoginController(loginPanel);
		//nav.pushController(loginController);
		
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Client.getInstance().disconnect();
			}
		});
		//NavigationManager.getInstance().pushController(new LoginController());
	}

	
	@Override
	public void setSize(int width, int height) {
		super.setSize(width, height);
		setLocationRelativeTo(null);
	}

	public static AppFrame getInstance() {
		if(instance == null)
		{
			instance = new AppFrame();
		}
		return instance;
	}
}
