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
import javax.swing.ListSelectionModel;

import model.ItemFile;

import common.Controller;
import common.JDialogBoundary;

import controller.AdminFileGroupsSelectController;

/**
 * Graphical User Interface that allows an administrator to change all file's groups association level according to their owner's groups association
  *
 */
@SuppressWarnings("serial")
public class ManageFileGroupsAssociation_GUI extends JDialogBoundary {
	
	/**
	 * Displays all items in the system
	 */
	private JList<ItemFile> listFiles;
	
	/**
	 * A model to design the list display
	 */
	private DefaultListModel<ItemFile> listModel;
	
	/**
	 * Close Window button
	 */
	private JButton btnClose;
	
	/**
	 * opens a new window to edit the file's groups association
	 */
	private JButton btnSelect;

	/**
	 * Constructs the window 
	 * @param controller
	 */
	public ManageFileGroupsAssociation_GUI(Controller controller) {
		super(controller);
		}

	@Override
	public void draw() {
		setBounds(new Rectangle(200, 150, 553, 357));
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
	
	/**
	 * Withdraws the selected file from the list
	 * @return
	 */
	public ItemFile getSelectedFile(){
		return listModel.getElementAt(listFiles.getSelectedIndex());
	}
	
	/**
	 * Adds files to the list
	 * @param file
	 */
	public void addListItem(ItemFile file){
		listModel.addElement(file);
	}
}
