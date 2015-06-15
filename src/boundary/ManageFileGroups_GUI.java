package boundary;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import common.Controller;
import common.JDialogBoundary;

import controller.ManageFileGroupsController;
import java.awt.Rectangle;

@SuppressWarnings("serial")
public class ManageFileGroups_GUI extends JDialogBoundary {
	
	private JTextField tfFilename;
	private JList listOtherGroups;
	private JButton btnAssociate,btnExclude,btnClose,btnConfirm;
	
	
	public ManageFileGroups_GUI(Controller controller) {
		super(controller);
		setBounds(new Rectangle(200, 150, 560, 420));
		getContentPane().setBackground(Color.WHITE);
		
	}

	@Override
	public void draw() {
getContentPane().setLayout(null);
		
		JPanel contents = new JPanel();
		contents.setBackground(Color.WHITE);
		contents.setBounds(0, 0, 542, 339);
		getContentPane().add(contents);
		contents.setLayout(null);
		
		JLabel lblManageFileGroups = new JLabel("Manage File Groups Association");
		lblManageFileGroups.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 30));
		lblManageFileGroups.setBounds(10, 11, 498, 42);
		contents.add(lblManageFileGroups);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.BLUE);
		separator.setBounds(10, 50, 522, 11);
		contents.add(separator);
		
		JLabel lblFilename = new JLabel("Filename:");
		lblFilename.setBounds(10, 64, 56, 14);
		contents.add(lblFilename);
		
		tfFilename = new JTextField();
		tfFilename.setEditable(false);
		tfFilename.setBounds(71, 61, 461, 20);
		contents.add(tfFilename);
		tfFilename.setColumns(10);
		
		JLabel lblGroupsAssociated = new JLabel("Groups Associated:");
		lblGroupsAssociated.setBounds(10, 89, 120, 14);
		contents.add(lblGroupsAssociated);
		
		JScrollPane spGroupsAssociated = new JScrollPane();
		spGroupsAssociated.setBounds(10, 107, 184, 221);
		contents.add(spGroupsAssociated);
		
		JList listGroupsAssociated = new JList();
		spGroupsAssociated.setViewportView(listGroupsAssociated);
		
		JLabel lblOtherGroups = new JLabel("Other Groups:");
		lblOtherGroups.setBounds(349, 92, 112, 14);
		contents.add(lblOtherGroups);
		
		JScrollPane spOtherGroups = new JScrollPane();
		spOtherGroups.setBounds(348, 107, 184, 221);
		contents.add(spOtherGroups);
		
		listOtherGroups = new JList();
		spOtherGroups.setViewportView(listOtherGroups);
		
		btnAssociate = new JButton("<< Associate");
		btnAssociate.setBounds(217, 136, 113, 23);
		contents.add(btnAssociate);
		
		btnExclude = new JButton("Exclude >>");
		btnExclude.setBounds(217, 261, 113, 23);
		contents.add(btnExclude);
		
		JPanel panelButtons = new JPanel();
		panelButtons.setBackground(Color.WHITE);
		panelButtons.setBounds(10, 328, 531, 41);
		getContentPane().add(panelButtons);
		panelButtons.setLayout(null);
		
		btnClose = new JButton("Close");
		btnClose.setBounds(432, 11, 89, 23);
		panelButtons.add(btnClose);
		
		btnConfirm = new JButton("Confirm");
		btnConfirm.setBounds(331, 11, 89, 23);
		panelButtons.add(btnConfirm);

	}

	@Override
	public void registerListeners() {
		final ManageFileGroupsController control = (ManageFileGroupsController)controller;
		
		btnClose.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				control.btnCloseClicked(); 
				
			}
		});
		
		btnAssociate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				control.btnAssociateClicked();
				
			}
		});
		
		btnConfirm.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				control.btnConfirmClicked();
				
			}
		});
		
		btnExclude.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				control.btnExcludeClicked();
				
			}
		});

	}
}
