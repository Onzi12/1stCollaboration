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
	private JButton btnCreateGroup;


	public MyBoxAdministrator_GUI(Controller controller) {
		super(controller);
		
		
	}
	
	public void draw(){
		super.draw();
		
		JPanel panelAdminButtons = new JPanel();
		panelAdminButtons.setBackground(Color.WHITE);
		panelAdminButtons.setBounds(0, 64, 718, 137);
		add(panelAdminButtons);
		panelAdminButtons.setLayout(null);
		
		btnManageFileGroups = new JButton("Manage File Groups");
		btnManageFileGroups.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnManageFileGroups.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnManageFileGroups.setBounds(103, 57, 173, 30);
		panelAdminButtons.add(btnManageFileGroups);
		
		btnManageGroupRequests = new JButton("Group Rquests");
		btnManageGroupRequests.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnManageGroupRequests.setBounds(555, 57, 133, 30);
		panelAdminButtons.add(btnManageGroupRequests);
		
		btnCreateGroup = new JButton("Create Group");
		btnCreateGroup.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnCreateGroup.setBounds(416, 57, 129, 31);
		panelAdminButtons.add(btnCreateGroup);
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
