package boundary;

import common.Controller;
import common.JDialogBoundary;
import javax.swing.JPanel;

public class ManageFileGroups_GUI extends JDialogBoundary {

	public ManageFileGroups_GUI(Controller controller) {
		super(controller);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 543, 339);
		getContentPane().add(panel);
		panel.setLayout(null);
	}

	@Override
	public void draw() {
		
		// TODO Auto-generated method stub

	}

	@Override
	public void registerListeners() {
		// TODO Auto-generated method stub

	}
}
