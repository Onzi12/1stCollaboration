package boundary;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class MyFrame extends JFrame {

	private static final long serialVersionUID = -2122161377842820073L;
	
	final public void presentView(JPanel panel) {
		getContentPane().add(panel);
		getContentPane().revalidate();
		getContentPane().repaint();
	}

	final public void dismissView(JPanel panel) {
		getContentPane().remove(panel);
	}
}
