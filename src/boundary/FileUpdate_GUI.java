package boundary;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;

import common.Controller;
import common.JDialogBoundary;

import controller.FileUpdateController;
import java.awt.SystemColor;


public class FileUpdate_GUI extends JDialogBoundary{

	private static final long serialVersionUID = -2841064597688335514L;
	private JButton btnSave;
	private JButton btnCancel;
	private boolean isSelected = true ;
	private JTextField tfFilename;
	private JTextField tfPath;
	private JButton btnPath;
	private JTextField tfSaveLocation;
	private JButton btnSaveLocation;
	private JTextArea taDescription;
	
	public FileUpdate_GUI(Controller controller) {
		super(controller);
	}
	
	@Override
	public void draw() {
		setTitle("Update File");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		if (isSelected)
			drawSelectedRow();
		else 
			drawNotSelectedRow();
	}
	
	private void drawSelectedRow(){
		setBounds(100, 100, 601, 211);
		getContentPane().setBackground(UIManager.getColor("text"));
		getContentPane().setLayout(null);
		
		JLabel lblUpdateFile = new JLabel("Update File");
		lblUpdateFile.setBounds(10, 3, 209, 42);
		lblUpdateFile.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 30));
		getContentPane().add(lblUpdateFile);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 42, 522, 11);
		separator.setForeground(Color.BLUE);
		getContentPane().add(separator);
		
		JPanel contentsPanel = new JPanel();
		contentsPanel.setBounds(10, 64, 565, 65);
		contentsPanel.setBackground(UIManager.getColor("text"));
		getContentPane().add(contentsPanel);
		contentsPanel.setLayout(null);
		
		JLabel label = new JLabel("Filename: ");
		label.setBounds(10, 14, 49, 14);
		contentsPanel.add(label);
		
		tfFilename = new JTextField(30);
		tfFilename.setBounds(64, 11, 491, 20);
		tfFilename.setEditable(false);
		tfFilename.setBackground(SystemColor.controlHighlight);
		contentsPanel.add(tfFilename);
		
		JLabel lblFilePath = new JLabel("File Path: ");
		lblFilePath.setBounds(10, 42, 49, 14);
		contentsPanel.add(lblFilePath);
		
		tfPath = new JTextField(30);
		tfPath.setBackground(Color.WHITE);
		tfPath.setEditable(false);
		tfPath.setBounds(64, 39, 448, 20);
		contentsPanel.add(tfPath);
		
		btnPath = new JButton("");
		btnPath.setBounds(522, 39, 33, 20);
		contentsPanel.add(btnPath);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBounds(10, 126, 565, 36);
		buttonPanel.setBackground(Color.WHITE);
		getContentPane().add(buttonPanel);
		buttonPanel.setLayout(null);
		
		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(481, 11, 74, 23);
		buttonPanel.add(btnCancel);
		
		btnSave = new JButton("Save");
		btnSave.setBounds(406, 11, 65, 23);
		buttonPanel.add(btnSave);
	}
	
	private void drawNotSelectedRow(){

		setBounds(100, 100, 601, 278);
		getContentPane().setBackground(UIManager.getColor("text"));
		getContentPane().setLayout(null);
		JLabel lblUpdateFile = new JLabel("Upload New File");
		lblUpdateFile.setBounds(10, 3, 256, 42);
		lblUpdateFile.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 30));
		getContentPane().add(lblUpdateFile);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 42, 522, 11);
		separator.setForeground(Color.BLUE);
		getContentPane().add(separator);
		
		JPanel contentsPanel = new JPanel();
		contentsPanel.setBounds(10, 56, 565, 141);
		contentsPanel.setBackground(UIManager.getColor("text"));
		getContentPane().add(contentsPanel);
		contentsPanel.setLayout(null);
		
		JLabel lblFilename = new JLabel("Filename: ");
		lblFilename.setBounds(0, 14, 75, 14);
		lblFilename.setForeground(Color.BLACK);
		contentsPanel.add(lblFilename);
		
		tfFilename = new JTextField(30);
		tfFilename.setBounds(95, 11, 460, 20);
		tfFilename.setBackground(Color.WHITE);
		contentsPanel.add(tfFilename);
		
		JLabel lblFilePath = new JLabel("File Path:");
		lblFilePath.setBounds(0, 42, 75, 14);
		contentsPanel.add(lblFilePath);
		
		tfPath = new JTextField(30);
		tfPath.setBounds(95, 39, 417, 20);
		tfPath.setBackground(SystemColor.controlHighlight);
		tfPath.setEditable(false);
		contentsPanel.add(tfPath);
		
		btnPath = new JButton("");
		btnPath.setBounds(522, 39, 33, 20);
		contentsPanel.add(btnPath);
		
		JLabel lblSaveLocation = new JLabel("Save Location:");
		lblSaveLocation.setBounds(0, 67, 85, 14);
		contentsPanel.add(lblSaveLocation);
		
		tfSaveLocation = new JTextField();
		tfSaveLocation.setBackground(SystemColor.controlHighlight);
		tfSaveLocation.setBounds(95, 64, 417, 20);
		tfSaveLocation.setEditable(false);
		contentsPanel.add(tfSaveLocation);
		tfSaveLocation.setColumns(10);
		
		btnSaveLocation = new JButton("");
		btnSaveLocation.setBounds(522, 63, 33, 21);
		contentsPanel.add(btnSaveLocation);
		
		JLabel lblDescription = new JLabel("Description:");
		lblDescription.setBounds(0, 92, 71, 14);
		lblDescription.setForeground(Color.BLACK);
		contentsPanel.add(lblDescription);
		
		JScrollPane spDescription = new JScrollPane();
		spDescription.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		spDescription.setBounds(95, 92, 460, 38);
		contentsPanel.add(spDescription);
		
		taDescription = new JTextArea();
		taDescription.setBackground(Color.WHITE);
		taDescription.setLineWrap(true);
		spDescription.setViewportView(taDescription);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBounds(10, 193, 575, 36);
		buttonPanel.setBackground(Color.WHITE);
		getContentPane().add(buttonPanel);
		buttonPanel.setLayout(null);
		
		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(471, 11, 74, 23);
		buttonPanel.add(btnCancel);
		
		btnSave = new JButton("Save");
		btnSave.setBounds(396, 11, 65, 23);
		buttonPanel.add(btnSave);
	}
	
	@Override
	public void registerListeners() {
		
		final FileUpdateController control = (FileUpdateController)controller;
		
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
		
		btnPath.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				control.btnPathClicked();
			}
		});
		if (btnSaveLocation != null){
		btnSaveLocation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				control.btnSaveLocationClicked();
			}
		});
		}
	}

	public void setFilename(String string) {
		tfFilename.setText(string);
		
	}

	public void setLocation(String string) {
		tfPath.setText(string);
	}

	public String getFilenameText() {
		return tfFilename.getText();
	}
	
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
}
