package boundary;
import common.Controller;
import common.JDialogBoundary;

import javax.swing.JPanel;

import java.awt.SystemColor;

import javax.swing.JSeparator;

import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;

import controller.FileAddController;
import controller.ManageGroupRequestsController;

@SuppressWarnings("serial")
public class ManageGroupRequests_GUI extends JDialogBoundary {
	
	
	private JTable table;
	private JButton btnClose,btnAccept,btnDecline;

	public ManageGroupRequests_GUI(Controller controller) {
		super(controller);
	}

	@Override
	public void draw() {
		getContentPane().setBackground(SystemColor.window);
		getContentPane().setLayout(null);
		
		JPanel panelContent = new JPanel();
		panelContent.setBackground(SystemColor.window);
		panelContent.setBounds(0, 0, 550, 316);
		getContentPane().add(panelContent);
		panelContent.setLayout(null);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.BLUE);
		separator.setBounds(10, 50, 522, 11);
		panelContent.add(separator);
		
		JLabel lblManageGroupRequests = new JLabel("Manage Group Requests");
		lblManageGroupRequests.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 30));
		lblManageGroupRequests.setBounds(10, 11, 413, 42);
		panelContent.add(lblManageGroupRequests);
		
		JScrollPane spRequests = new JScrollPane();
		spRequests.setBounds(10, 59, 530, 253);
		panelContent.add(spRequests);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setCellSelectionEnabled(true);
		spRequests.setViewportView(table);
		
		JPanel panelButtons = new JPanel();
		panelButtons.setBackground(SystemColor.window);
		panelButtons.setBounds(10, 317, 530, 29);
		getContentPane().add(panelButtons);
		panelButtons.setLayout(null);
		
		btnClose = new JButton("Close");
		btnClose.setBounds(441, 0, 89, 23);
		panelButtons.add(btnClose);
		
		btnAccept = new JButton("Accept");
		btnAccept.setBounds(344, 0, 89, 23);
		panelButtons.add(btnAccept);
		
		btnDecline = new JButton("Decline");
		btnDecline.setBounds(245, 0, 89, 23);
		panelButtons.add(btnDecline);
	}

	@Override
	public void registerListeners() {
		final ManageGroupRequestsController control = (ManageGroupRequestsController)controller;
		
		btnClose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				control.btnCloseClicked();	
			}
		});
		
		btnAccept.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				control.btnAcceptClicked();
				
			}
		});
		
		btnDecline.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				control.btnDeclineClicked();
				
			}
		});
	}
}
