package boundary;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import common.Boundary;
import common.Controller;
import common.JPanelBoundary;
import controller.MyBoxController;
import controller.NavigationManager;

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
	 * Create the frame and its functionality.
	 */
	private AppFrame() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
		addWindowListener(new WindowAdapter() { // window listener to MyBox that logs out and exits the applicatoin if window is closed
			
			@Override
			public void windowClosing(WindowEvent e){
				JFrame frame = (JFrame)e.getSource();
				
				int rs = JOptionPane.showConfirmDialog(frame, "Are you sure you wish to leave?","EXIT Application",JOptionPane.YES_NO_OPTION);
				if (rs == JOptionPane.YES_OPTION){
					
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					
					Controller controller = NavigationManager.getInstance().getCurrentController();
					if (controller instanceof MyBoxController) {
						((MyBoxController)controller).logout();
					}
					
			
				}
			}
		});
		setBounds(100, 100, 326, 419);
		setLocationRelativeTo(null);
		setResizable(false);
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
