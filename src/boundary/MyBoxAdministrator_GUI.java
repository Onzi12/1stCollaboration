package boundary;

import common.Controller;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class MyBoxAdministrator_GUI extends MyBox_GUI {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public MyBoxAdministrator_GUI(Controller controller) {
		super(controller);
		
		
	}
	
	public void draw(){
		super.draw();
		
		JPanel panelAdminButtons = new JPanel();
		panelAdminButtons.setBackground(Color.WHITE);
		panelAdminButtons.setBounds(261, 110, 430, 71);
		add(panelAdminButtons);
		panelAdminButtons.setLayout(null);
		
		JButton btnNewButton = new JButton("Manage File Groups");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton.setBounds(20, 11, 151, 30);
		panelAdminButtons.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Group Rquests");
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_1.setBounds(181, 11, 143, 30);
		panelAdminButtons.add(btnNewButton_1);
	}
			
	
	@Override
	public void registerListeners() {
		super.registerListeners();
	}

}
