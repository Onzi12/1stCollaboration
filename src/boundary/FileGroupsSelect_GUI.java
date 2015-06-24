package boundary;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Group;
import model.ItemFile;

import common.Controller;
import common.JDialogBoundary;

import controller.FileGroupsSelectController;
import javax.swing.ListSelectionModel;

/**
 * Graphical User Interface that allows a user to Edit a file access level to all groups he is associated with
 *
 */
@SuppressWarnings("serial")
public class FileGroupsSelect_GUI extends JDialogBoundary{
	
	/**
	 * a model to design the group list
	 */
	private DefaultListModel<Group> listModel;
	
	/**
	 * group list to display all groups that are eligible to obtain a level of access regarding the selected file
	 */
	private JList<Group> listGroups;
	
	/**
	 * Close Window Button
	 */
	private JButton btnFinish;
	
	/**
	 * A Selection box that allows 
	 */
	private JComboBox<Access> cbAccess;
	
	/**
	 * Constant levels of Access
	 */
	private enum Access {None,Read,Update};
	
	/**
	 * data structure holding the groups and files access level localy
	 */
	private HashMap<Integer,Integer> groupAccess;
	
	/**
	 * The file for which the group's association is being edited
	 */
	private ItemFile file;
	
	/**
	 * Constructs the window and registers the controller and selected file
	 * @param controller
	 * @param file
	 */
	public FileGroupsSelect_GUI(Controller controller,ItemFile file) {
		super(controller);	
		groupAccess = new HashMap<Integer,Integer>();
		this.file = file;
	}

	@Override
	public void draw() {
		setBounds(new Rectangle(200, 200, 549, 377));
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		
		JPanel contentsPanel = new JPanel();
		contentsPanel.setBackground(Color.WHITE);
		contentsPanel.setBounds(0, 0, 533, 295);
		getContentPane().add(contentsPanel);
		contentsPanel.setLayout(null);
		
		JLabel lblAssociateFileTo = new JLabel("Associate File to Groups");
		lblAssociateFileTo.setBounds(10, 11, 427, 42);
		lblAssociateFileTo.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 28));
		contentsPanel.add(lblAssociateFileTo);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 50, 458, 11);
		separator.setForeground(Color.BLUE);
		contentsPanel.add(separator);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 59, 513, 235);
		contentsPanel.add(scrollPane);
		
		listModel = new DefaultListModel<Group>();
		listGroups = new JList<Group>();
		listGroups.setValueIsAdjusting(true);
		listGroups.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listGroups.setModel(listModel);
		scrollPane.setViewportView(listGroups);
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBackground(Color.WHITE);
		buttonsPanel.setBounds(0, 294, 533, 45);
		getContentPane().add(buttonsPanel);
		buttonsPanel.setLayout(null);
		
		btnFinish = new JButton("Finish");
		btnFinish.setBounds(434, 7, 89, 23);
		buttonsPanel.add(btnFinish);
		
		cbAccess = new JComboBox<Access>();
		cbAccess.addItem(Access.None);
		cbAccess.addItem(Access.Read);
		cbAccess.addItem(Access.Update);
		cbAccess.setBounds(97, 8, 102, 20);
		buttonsPanel.add(cbAccess);
		
		JLabel lblAccessLevel = new JLabel("Access Level:");
		lblAccessLevel.setBounds(10, 11, 102, 14);
		buttonsPanel.add(lblAccessLevel);
	}

	@Override
	public void registerListeners() {
		final FileGroupsSelectController control = (FileGroupsSelectController)controller;
		
		btnFinish.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				control.btnFinishClicked();
			}
		});
		
		cbAccess.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				groupAccess.put(listModel.getElementAt(listGroups.getSelectedIndex()).getGroupID(),cbAccess.getSelectedIndex());
				control.cbAccessChanged(listModel.getElementAt(listGroups.getSelectedIndex()),cbAccess.getSelectedIndex());
			}
		});
		
		listGroups.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				
				if (groupAccess.size() != 0)
					setCB(groupAccess.get(listGroups.getSelectedValue().getGroupID()));
			}
		});
		
	}
	
	/**
	 * adds a group to the groups list
	 * @param group
	 */
	public void addGroup(Group group){
		listModel.addElement(group);
	}
	
	/**
	 * returns the selected group at specified index
	 * @param index
	 * @return
	 */
	public Group getGroup(int index){
		return listModel.get(index);
	}
	
	/**
	 * sets the existing Access level of a group in the selection box
	 * @param access
	 */
	public void setCB(int access){
		cbAccess.setSelectedIndex(access);
	}
	
	/**
	 * withdraws the selected Access level that was determined to a selected group
	 * @return
	 */
	public int getCB(){
		int access = cbAccess.getSelectedIndex();
		return access;
	}
	
	/**
	 * inserts groups access of the selected file to a local data structure
	 * @param groupId
	 * @param access
	 */
	public void addGroupAccess(int groupId,int access){
		groupAccess.put(groupId, access);
	}
	
	/**
	 * returns the selected file
	 * @return ItemFile
	 */
	public ItemFile getFile(){
		return file;
	}
}
	