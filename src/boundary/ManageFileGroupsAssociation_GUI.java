package boundary;

import common.Controller;
import common.JDialogBoundary;

import java.awt.Color;

import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JSeparator;

import controller.AdminFileGroupsSelectController;
import model.ItemFile;
import java.awt.Rectangle;
import javax.swing.ListSelectionModel;

@SuppressWarnings("serial")
public class ManageFileGroupsAssociation_GUI extends JDialogBoundary {
	
	private JList<ItemFile> listFiles;
	private DefaultListModel<ItemFile> listModel;
	private JButton btnClose;
	private JButton btnSelect;

	public ManageFileGroupsAssociation_GUI(Controller controller) {
		super(controller);
		setBounds(new Rectangle(200, 150, 553, 357));
		
		}

	@Override
	public void draw() {
		JPanel contentsPanel = new JPanel();
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		contentsPanel.setBackground(Color.WHITE);
		contentsPanel.setBounds(0, 0, 547, 272);
		getContentPane().add(contentsPanel);
		contentsPanel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 71, 527, 201);
		contentsPanel.add(scrollPane);
		listModel = new DefaultListModel<ItemFile>();
		listFiles = new JList<ItemFile>();
		listFiles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listFiles.setModel(listModel);
		scrollPane.setViewportView(listFiles);
		
		JLabel lblManageFileGroups = new JLabel("Manage File Groups Association");
		lblManageFileGroups.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 25));
		lblManageFileGroups.setBounds(10, 11, 516, 42);
		contentsPanel.add(lblManageFileGroups);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.BLUE);
		separator.setBounds(10, 50, 490, 11);
		contentsPanel.add(separator);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(93, 283, 444, 40);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		btnClose = new JButton("Close");
		btnClose.setBounds(345, 11, 89, 23);
		panel.add(btnClose);
		
		btnSelect = new JButton("Select");
		btnSelect.setBounds(236, 11, 89, 23);
		panel.add(btnSelect);
		

	}

	@Override
	public void registerListeners() {
		
		final AdminFileGroupsSelectController control = (AdminFileGroupsSelectController) controller;
		
		btnClose.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				control.btnCloseClicked();
				
			}
		});
		
		btnSelect.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				control.btnSelectClicked(getSelectedFile());
				
			}
		});

	}
	
	public ItemFile getSelectedFile(){
		return listModel.getElementAt(listFiles.getSelectedIndex());
	}
	
	public void addListItem(ItemFile file){
		listModel.addElement(file);
	}
}
