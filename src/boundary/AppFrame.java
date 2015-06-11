package boundary;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import common.Boundary;

import client.Client;
import controller.LoginController;
import controller.NavigationManager;

public class AppFrame extends JFrame{

	private static final long serialVersionUID = 351110661931287206L;
	private NavigationManager nav;

	
	final public void presentView(Boundary panel) {
		getContentPane().add(panel);
		getContentPane().revalidate();
		getContentPane().repaint();
	}

	final public void dismissView(Boundary panel) {
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
	public AppFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 326, 419);
		setLocationRelativeTo(null);
		setResizable(false);
		
		nav = new NavigationManager(this);
		
		Login_GUI loginPanel = new Login_GUI();
		LoginController loginController = new LoginController(loginPanel);
		nav.pushController(loginController);
		
		addWindowListener(new WindowListener());

	}
	
	private class WindowListener extends WindowAdapter {

		@Override
		public void windowClosing(WindowEvent e) {
			Client.getInstance().disconnect();
		}
		
	}
	
	@Override
	public void setSize(int width, int height) {
		super.setSize(width, height);
		setLocationRelativeTo(null);
	}
}
