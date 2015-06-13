package boundary;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;
import common.Boundary;


import common.Controller;
import common.JDialogBoundary;

import java.awt.Color;

import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.UIManager;

import controller.FileReadController;

public class FileRead_GUI extends JDialogBoundary {

	private static final long serialVersionUID = 3050895492014566356L;
	private JPanel contents, buttonPanel;
	private JTextField tfFilename, tfLocation;
	private JButton btnClose;
	private JLabel lblDescription;
	private JTextField tfDescription;
	private JTextArea taContent;
	private JLabel lblPrivilege;
	private JComboBox<String> cbPrivilege;
	
	public FileRead_GUI(Controller controller) {
		super(controller);
	}

	@Override
	public void draw() {
		setTitle("Read File");
		setBounds(100, 100, 647, 459);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		contents = new JPanel();
		contents.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contents);
		contents.setLayout(null);
		
		JPanel center = new JPanel();
		center.setBounds(5, 5, 616, 361);
		contents.add(center);
		center.setLayout(null);
		
		JLabel lblFilename = new JLabel("Filename: ");
		lblFilename.setForeground(Color.BLACK);
		lblFilename.setBounds(7, 10, 66, 14);
		center.add(lblFilename);
		
		tfFilename = new JTextField(30);
		tfFilename.setBounds(83, 7, 523, 20);
		tfFilename.setEditable(isEditable());
		center.add(tfFilename);
		
		JLabel lblPath = new JLabel("Path: ");
		lblPath.setBounds(7, 34, 47, 14);
		center.add(lblPath);
		
		tfLocation = new JTextField(30);
		tfLocation.setBounds(83, 31, 523, 20);
		tfLocation.setEditable(isEditable());
		center.add(tfLocation);
		
		lblDescription = new JLabel("Description:");
		lblDescription.setBounds(7, 59, 79, 14);
		center.add(lblDescription);
		
		tfDescription = new JTextField();
		tfDescription.setBackground(isEditable() ? Color.WHITE : getBackground() );
		tfDescription.setBounds(83, 55, 523, 23);
		tfDescription.setEditable(isEditable());
		tfDescription.setColumns(10);
		center.add(tfDescription);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );
		scrollPane.setBounds(7, 117, 599, 244);
		center.add(scrollPane);
		
		taContent = new JTextArea();
		taContent.setLineWrap(true);
		taContent.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		taContent.setBackground(isEditable() ? Color.WHITE : getBackground() );
		taContent.setEditable(isEditable());
		scrollPane.setViewportView(taContent);
		
		lblPrivilege = new JLabel("Privilege:");
		lblPrivilege.setBounds(7, 83, 58, 23);
		center.add(lblPrivilege);
		
		cbPrivilege = new JComboBox<String>();
		cbPrivilege.setBounds(83, 84, 85, 20);
		cbPrivilege.addItem("Private");
		cbPrivilege.addItem("Group");
		cbPrivilege.addItem("Public");
		cbPrivilege.setEnabled(isEditable());
		cbPrivilege.setEditable(isEditable());
		center.add(cbPrivilege);
		
		buttonPanel = new JPanel();
		buttonPanel.setBounds(5, 377, 616, 33);
		contents.add(buttonPanel);
		
		addButtons(buttonPanel);
	}
	
	public void setFilename(String name) {
		tfFilename.setText(name);
	}
	
	public void setLocation(String location) {
		tfLocation.setText(location);
	}
	
	public String getTextAreaText() {
		return taContent.getText();
	}
	
	

	public String getFilenameText() {
		return tfFilename.getText();
	}
	
	public String getLocationText() {
		return tfLocation.getText();
	}
	
	public void addButtons(JPanel buttonPanel) {
		buttonPanel.setLayout(null);
		btnClose = new JButton("Close");
		btnClose.setBounds(523, 5, 88, 23);
		buttonPanel.add(btnClose);
	}


	public boolean isEditable() {
		return false;
	}


	public int getCbPrivilegeIndex() {
		return cbPrivilege.getSelectedIndex();
	}

	public void setCbPrivilegeIndex(int index) {
		this.cbPrivilege.setSelectedIndex(index);
	}
	
	public String getCbPrivilege() {
		return cbPrivilege.getSelectedItem().toString();
	}
	
	public void setCbPrivilege(String privilege) {
		this.cbPrivilege.setSelectedItem(privilege);
	}
	
	

	@Override
	public void registerListeners() {
		
		final FileReadController control = (FileReadController)controller;
		
		btnClose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				control.btnCancelClicked();
			}
		});
		
	}
	
}
