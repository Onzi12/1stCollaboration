package boundary;

import common.Controller;

import javax.swing.JPanel;

import java.awt.Color;

import javax.swing.JButton;

import controller.MyBoxAdministratorController;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class MyBoxAdministrator_GUI extends MyBox_GUI {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnManageGroupRequests;
	private JButton btnManageFileGroups;


	public MyBoxAdministrator_GUI(Controller controller) {
		super(controller);
		
		
	}
	
	public void draw(){
		super.draw();
		
		JPanel panelAdminButtons = new JPanel();
		panelAdminButtons.setBackground(Color.WHITE);
		panelAdminButtons.setBounds(261, 113, 450, 68);
		add(panelAdminButtons);
		panelAdminButtons.setLayout(null);
		
		btnManageFileGroups = new JButton("Manage File Groups");
		btnManageFileGroups.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnManageFileGroups.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnManageFileGroups.setBounds(100, 11, 173, 30);
		panelAdminButtons.add(btnManageFileGroups);
		
		btnManageGroupRequests = new JButton("Group Rquests");
		btnManageGroupRequests.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnManageGroupRequests.setBounds(283, 11, 143, 30);
		panelAdminButtons.add(btnManageGroupRequests);
	}
			
	
	@Override
	public void registerListeners() {
		super.registerListeners();
		
		final MyBoxAdministratorController control = (MyBoxAdministratorController)controller;
		
		btnManageFileGroups.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				control.btnManageFileGroupsClicked();	
			}
		});
		
		btnManageGroupRequests.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				control.btnManageGroupRequestsClicked();
				
			}
		});
	}

}
