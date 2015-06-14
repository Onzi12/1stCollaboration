package boundary;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import client.Client;

import common.Boundary;
import common.JPanelBoundary;


public class AppFrame extends JFrame {


	private static final long serialVersionUID = 351110661931287206L;
	private static AppFrame instance = null;
	

	
	final public void presentView(Boundary panel) {
		
		getContentPane().add((JPanelBoundary)panel);
		getContentPane().revalidate();
		getContentPane().repaint();
	}

	final public void dismissView(Boundary panel) {
		getContentPane().remove((JPanelBoundary)panel);
	}


	/**
	 * Create the frame.
	 */
	private AppFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 326, 419);
		setLocationRelativeTo(null);
		setResizable(false);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Client.getInstance().disconnect();
			}
		});
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
