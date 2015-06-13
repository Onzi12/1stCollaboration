package boundary;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import common.Controller;
import common.JDialogBoundary;
import controller.FileUpdateController;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JSeparator;

import java.awt.Color;

import javax.swing.UIManager;
import javax.swing.JTextField;


public class FileUpdate_GUI extends JDialogBoundary{

	private static final long serialVersionUID = -2841064597688335514L;
	private JButton btnSave;
	private JButton btnCancel;
	private JLabel lblUpdateFile;
	private boolean isSelected = true ;
	private JTextField tfFilename;
	private JLabel lblFilePath;
	private JTextField tfPath;
	private JButton btnPath;
	private JLabel lblSaveLocation;
	private JTextField tfSaveLocation;
	private JButton btnSaveLocation;
	private JLabel lblDescription;
	private JTextField textField;

	
	public FileUpdate_GUI(Controller controller) {
		super(controller);
		
		
	}
	
	@Override
	public void draw() {
			setTitle("Read File");
			setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			
			setBounds(100, 100, 601, 311);
			getContentPane().setBackground(UIManager.getColor("text"));
			getContentPane().setLayout(null);
			lblUpdateFile = new JLabel("Update File");
			lblUpdateFile.setBounds(10, 3, 209, 42);
			lblUpdateFile.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 30));
			getContentPane().add(lblUpdateFile);
			
			JSeparator separator = new JSeparator();
			separator.setBounds(10, 42, 522, 11);
			separator.setForeground(Color.BLUE);
			getContentPane().add(separator);
			
			JPanel contentsPanel = new JPanel();
			contentsPanel.setBounds(10, 56, 565, 173);
			contentsPanel.setBackground(UIManager.getColor("text"));
			getContentPane().add(contentsPanel);
			contentsPanel.setLayout(null);
			
			JLabel label = new JLabel("Filename: ");
			label.setBounds(10, 14, 49, 14);
			label.setForeground(Color.BLACK);
			contentsPanel.add(label);
			
			tfFilename = new JTextField(30);
			tfFilename.setBounds(95, 11, 460, 20);
			tfFilename.setEditable(false);
			tfFilename.setBackground(Color.WHITE);
			contentsPanel.add(tfFilename);
			
			lblFilePath = new JLabel("File Path:");
			lblFilePath.setBounds(10, 42, 47, 14);
			contentsPanel.add(lblFilePath);
			
			tfPath = new JTextField(30);
			tfPath.setBounds(95, 39, 417, 20);
			tfPath.setBackground(Color.WHITE);
			tfPath.setEditable(false);
			contentsPanel.add(tfPath);
			
			btnPath = new JButton("");
			btnPath.setBounds(522, 39, 33, 20);
			contentsPanel.add(btnPath);
			
			lblSaveLocation = new JLabel("Save Location:");
			lblSaveLocation.setBounds(10, 67, 71, 14);
			contentsPanel.add(lblSaveLocation);
			
			tfSaveLocation = new JTextField();
			tfSaveLocation.setBounds(95, 64, 417, 20);
			contentsPanel.add(tfSaveLocation);
			tfSaveLocation.setColumns(10);
			
			btnSaveLocation = new JButton("");
			btnSaveLocation.setBounds(522, 63, 33, 21);
			contentsPanel.add(btnSaveLocation);
			
			lblDescription = new JLabel("Description:");
			lblDescription.setForeground(Color.BLACK);
			lblDescription.setBounds(10, 92, 71, 14);
			contentsPanel.add(lblDescription);
			
			textField = new JTextField(30);
			textField.setEditable(false);
			textField.setBackground(Color.WHITE);
			textField.setBounds(95, 92, 460, 20);
			contentsPanel.add(textField);
			
			JPanel buttonPanel = new JPanel();
			buttonPanel.setBounds(10, 226, 565, 36);
			buttonPanel.setBackground(Color.WHITE);
			getContentPane().add(buttonPanel);
			buttonPanel.setLayout(null);
			
			btnCancel = new JButton("Cancel");
			btnCancel.setBounds(481, 11, 74, 23);
			buttonPanel.add(btnCancel);
			
			btnSave = new JButton("Save");
			btnSave.setBounds(406, 11, 65, 23);
			buttonPanel.add(btnSave);
			
			
	//	} else {
			
			
		//}
		// TODO Auto-generated method stub
		
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
			public void actionPerformed(ActionEvent arg0) {
				control.btnPathClicked();
			}
		});
	}

	public void setFilename(String string) {
		// TODO Auto-generated method stub
		
	}

	public void setLocation(String string) {
		// TODO Auto-generated method stub
		
	}

	public String getFilenameText() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
}
