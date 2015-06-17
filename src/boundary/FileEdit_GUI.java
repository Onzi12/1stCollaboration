package boundary;

import model.ItemFile;
import model.ItemFile.Privilege;
import common.Controller;
import common.JDialogBoundary;

import javax.swing.JPanel;

import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextArea;
import javax.swing.JComboBox;

import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import controller.FileEditController;

import java.awt.Rectangle;

public class FileEdit_GUI extends JDialogBoundary{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTextField tfFilename;
	private JTextArea taDescription;
	private JComboBox<Privilege> cbPrivilege;
	private JButton btnCancel;
	private JButton btnSave;
	
	public FileEdit_GUI(Controller controller) {
		super(controller);
	}

	@Override
	public void draw() { 
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
		cbPrivilege.addItem(Privilege.PUBLIC);
		cbPrivilege.addItem(Privilege.GROUP);
		cbPrivilege.addItem(Privilege.PRIVATE);
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
	}
	
	public String getFilename(){
		return tfFilename.getText();
	}
	
	public void setFilename(String filename) {
		tfFilename.setText(filename);
	}
	
	public String getDescriptionText() {
		return taDescription.getText();
	}

	public void setDescriptionText(String taDescription) {
		this.taDescription.setText(taDescription);
	}

	public Privilege getPrivilege() {
		return (Privilege)cbPrivilege.getSelectedItem();
	}

	public void setPrivilege(Privilege p) {
		this.cbPrivilege.setSelectedIndex(p.getValue());
	}

	
}
