package boundary;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import model.ItemFile.Privilege;
import common.Controller;
import common.JDialogBoundary;
import controller.FileEditController;

/**
 * Graphical User Interface that allows a user to edit a file's Name, Description,Privilege,and Content
 * @author Idan
 *
 */
@SuppressWarnings("serial")
public class FileEdit_GUI extends JDialogBoundary{
	
	/**
	 * A Text field that displays the selected file's name
	 */
	private JTextField tfFilename;
	
	/**
	 * A Text area that displays the selected file's Description
	 */
	private JTextArea taDescription;
	
	/**
	 * A selection box that allows to adjust the file's privilege level
	 */
	private JComboBox<Privilege> cbPrivilege;
	
	/**
	 * Window Close Button
	 */
	private JButton btnCancel;
	
	/**
	 * Saves the changes that were made to the file
	 */
	private JButton btnSave;
	
	/**
	 * Opens a window to adjust group's Access to the file
	 * this Button is available only if this window is opened by the file owner
	 */
	private JButton btnManageGroupsAccess;
	
	
	/**
	 * Constructs 
	 * @param controller
	 */
	public FileEdit_GUI(Controller controller) {
		super(controller);
	}

	@Override
	public void draw() { 
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		getContentPane().setBackground(Color.WHITE);
		setBounds(new Rectangle(150, 200, 570, 250));
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 549, 163);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblEditFile = new JLabel("Edit File");
		lblEditFile.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 30));
		lblEditFile.setBounds(10, 0, 209, 42);
		panel.add(lblEditFile);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.BLUE);
		separator.setBounds(10, 39, 458, 11);
		panel.add(separator);
		
		JLabel lblFilename = new JLabel("Filename:");
		lblFilename.setBounds(10, 50, 70, 14);
		panel.add(lblFilename);
		
		JLabel lblDescription = new JLabel("Description:");
		lblDescription.setBounds(10, 75, 70, 14);
		panel.add(lblDescription);
		
		tfFilename = new JTextField();
		tfFilename.setBounds(90, 47, 449, 20);
		panel.add(tfFilename);
		tfFilename.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(90, 75, 449, 53);
		panel.add(scrollPane);
		
		taDescription = new JTextArea();
		taDescription.setWrapStyleWord(true);
		scrollPane.setViewportView(taDescription);
		
		JLabel lblPrivilege = new JLabel("Privilege:");
		lblPrivilege.setBounds(10, 136, 80, 14);
		panel.add(lblPrivilege);
		
		cbPrivilege = new JComboBox<Privilege>();
		cbPrivilege.setEditable(true);
		cbPrivilege.setBackground(SystemColor.controlHighlight);
		cbPrivilege.setBounds(90, 133, 85, 24);
		cbPrivilege.addItem(Privilege.PRIVATE);
		cbPrivilege.addItem(Privilege.GROUP);
		cbPrivilege.addItem(Privilege.PUBLIC);
	
		panel.add(cbPrivilege);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(0, 166, 549, 36);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(450, 0, 89, 23);
		panel_1.add(btnCancel);
		
		btnSave = new JButton("Save");
		btnSave.setBounds(351, 0, 89, 23);
		panel_1.add(btnSave);	
		
		btnManageGroupsAccess = new JButton("Manage Groups Access");
		btnManageGroupsAccess.setBounds(10, 0, 183, 23);
		setBtnManageGroupsAccess(true);
		panel_1.add(btnManageGroupsAccess);
		
		addWindowListener(new WindowAdapter() { //window listener that initiates the isEdited flag in DB if window is closed
			
			@Override
			public void windowClosing(WindowEvent e){
						FileEditController control = (FileEditController) controller;
						control.close();
			}
		});
	}
	
	/**
	 * Sets the availability of the Manage Groups Access button to restrict access to owner alone
	 * @param arg
	 */
	public void setBtnManageGroupsAccess(boolean arg) {
		btnManageGroupsAccess.setEnabled(arg);
	}

	@Override
	public void registerListeners() {
		
		final FileEditController control = (FileEditController)controller;
		
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				control.btnCancelClicked();
				
			}
		});
		
		btnSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				control.btnSaveClicked();
				
			}
		});
		
		btnManageGroupsAccess.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				control.btnManageGroupsAccessClicked();
				
			}
		});
	}
	
	/**
	 * Withdraws the new filename 
	 * @return
	 */
	public String getFilename(){
		return tfFilename.getText();
	}
	
	/**
	 * sets the initial file name
	 * @param filename
	 */
	public void setFilename(String filename) {
		tfFilename.setText(filename);
	}
	
	/**
	 * Withdraws the new Description
	 * @return
	 */
	public String getDescriptionText() {
		return taDescription.getText();
	}

	/**
	 * sets the initial file Description
	 * @param taDescription
	 */
	public void setDescriptionText(String taDescription) {
		this.taDescription.setText(taDescription);
	}

	/**
	 * Withdraws the new file privilege
	 * @return
	 */
	public Privilege getPrivilege() {
		return (Privilege)cbPrivilege.getSelectedItem();
	}
	 /**
	  * sets the initial file privilege
	  * @param i
	  */
	public void setPrivilege(int i) {
		this.cbPrivilege.setSelectedIndex(i);
	}

	
	
}
