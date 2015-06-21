package boundary;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ScrollPaneConstants;

import model.Group;

import common.Controller;
import common.JDialogBoundary;

import controller.GroupsController;

public class Groups_GUI extends JDialogBoundary{

	private JList<Group> listMyGroups,listOtherGroups;
	private JButton btnJoin,btnLeave,btnSendRequests,btnClose;
	private DefaultListModel<Group> listMyGroupsModel,listOtherGroupsModel;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Groups_GUI(Controller controller) {
		super(controller);
		setBounds(new Rectangle(200, 150, 540, 415));
	}

	@Override
	public void draw() {
		getContentPane().setBounds(0, 0, 542, 339);
		setBackground(Color.WHITE);
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 512, 325);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblGroups = new JLabel("Groups");
		lblGroups.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 30));
		lblGroups.setBounds(10, 11, 209, 42);
		panel.add(lblGroups);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.BLUE);
		separator.setBounds(10, 50, 484, 11);
		panel.add(separator);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(10, 88, 182, 233);
		panel.add(scrollPane);
		
		listMyGroups = new JList<Group>();
		listMyGroupsModel = new DefaultListModel<Group>();
		listMyGroups.setModel(listMyGroupsModel);
		
		scrollPane.setViewportView(listMyGroups);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.setBounds(316, 88, 182, 233);
		panel.add(scrollPane_1);
		
		listOtherGroups = new JList<Group>();
		listOtherGroupsModel = new DefaultListModel<Group>();
		listOtherGroups.setModel(listOtherGroupsModel);
		
		scrollPane_1.setViewportView(listOtherGroups);
		
		btnJoin = new JButton("<< Join");
		btnJoin.setBounds(202, 129, 104, 23);
		panel.add(btnJoin);
		
		btnLeave = new JButton("Leave >>");
		btnLeave.setBounds(202, 192, 104, 23);
		panel.add(btnLeave);
		
		JLabel lblMyGroups = new JLabel("My Groups:");
		lblMyGroups.setFont(new Font("Tahoma", Font.ITALIC, 14));
		lblMyGroups.setBounds(10, 64, 114, 28);
		panel.add(lblMyGroups);
		
		JLabel lblOtherGroups = new JLabel("Other Groups:");
		lblOtherGroups.setFont(new Font("Tahoma", Font.ITALIC, 14));
		lblOtherGroups.setBounds(306, 65, 137, 27);
		panel.add(lblOtherGroups);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(0, 325, 512, 42);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		btnClose = new JButton("Close");
		btnClose.setBounds(413, 11, 89, 23);
		panel_1.add(btnClose);
		
		btnSendRequests = new JButton("Send Requests");
		btnSendRequests.setBounds(273, 11, 130, 23);
		panel_1.add(btnSendRequests);
	}

	@Override
	public void registerListeners() {
		
		final GroupsController control = (GroupsController)controller;
		
		btnSendRequests.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				control.btnSendRequestsClicked();
				
			}
		});
		
		btnClose.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				control.btnCloseClicked();
				
			}
		});
		
		btnLeave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				control.btnLeaveClicked();
				
			}
		});
		
		btnJoin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				control.btnJoinClicked();
				
			}
		});
		
	}
	
	public void addMyGroupElement(Group group){
		listMyGroupsModel.addElement(group);
	}
	
	public void addOtherGroupElement(Group group){
		listOtherGroupsModel.addElement(group);
	}
	
	public Group getSelectedMyGroup(){
		return listMyGroupsModel.getElementAt(listMyGroups.getSelectedIndex());
	}
	
	public Group getSelectedOtherGroup(){
		return listOtherGroupsModel.getElementAt(listOtherGroups.getSelectedIndex());
	}
}
