package boundary;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import common.Controller;

import controller.MyBoxAdministratorController;

/**
 * The {@link MyBoxAdministrator_GUI} class extends {@link MyBox_GUI} to show more options for administrator.
 */
public class MyBoxAdministrator_GUI extends MyBox_GUI {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Manage group requests button.
	 */
	private JButton btnManageGroupRequests;
	/**
	 * Manage group files button.
	 */
	private JButton btnManageFileGroups;
	
	/**
	 * Create group button.
	 */
	private JButton btnCreateGroup;


	/**
	 * Constructs the {@link MyBoxAdministrator_GUI}.
	 * @param controller
	 */
	public MyBoxAdministrator_GUI(Controller controller) {
		super(controller);
		
		
	}
	
	@Override
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
		
		btnCreateGroup.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				control.btnCreateGroupClicked();
				
			}
		});
	}
}
