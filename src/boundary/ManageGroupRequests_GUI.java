package boundary;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import model.Request;

import common.Controller;
import common.JDialogBoundary;

import controller.ManageGroupRequestsController;
import custom_gui.RequestTableModel;

/**
 * Graphical User Interface that allows an administrator to answer all user's group requests
 *
 */
@SuppressWarnings("serial")
public class ManageGroupRequests_GUI extends JDialogBoundary {
	
	/**
	 * Displays the requests of the users
	 */
	private JTable table;
	/**
	 * Close Window Button
	 */
	private JButton btnClose;
	/**
	 * Accepts the user's request to join/leave a group
	 */
	private JButton btnAccept;
	/**
	 * Declines the user's request to join/leave a group
	 */
	private JButton btnDecline;

	public ManageGroupRequests_GUI(Controller controller) {
		super(controller);
		setBounds(new Rectangle(200, 150, 570, 405));
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
	
	/** 
	 * Returns the Requests table
	 * @return
	 */
	public JTable getTable() {
		return table;
	}
	
	/**
	 * Sets the table model 
	 * @param model
	 */
	public void setTableModel(RequestTableModel model) {
		table.setModel(model);
	}
	
	/**
	 * Withdraws all Requests 
	 * @return
	 */
	public Request getSelectedRequest() {
		return (Request) table.getModel().getValueAt(table.getSelectedRow(), RequestTableModel.OBJECT_COL);
	}
}
