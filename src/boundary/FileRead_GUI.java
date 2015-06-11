package boundary;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
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
	private JLabel tfDescription;
	private JTextField textField;
	private JTextArea textArea;
	
	public FileRead_GUI(Controller controller) {
		super(controller);
	}

	@Override
	public void draw() {
		setTitle("File");
		setBounds(100, 100, 647, 412);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		contents = new JPanel();
		contents.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contents);
		contents.setLayout(null);
		
		JPanel center = new JPanel();
		center.setBounds(5, 5, 616, 333);
		contents.add(center);
		center.setLayout(null);
		
		JLabel lblFilename = new JLabel("Filename: ");
		lblFilename.setForeground(Color.BLACK);
		lblFilename.setBounds(7, 10, 49, 14);
		center.add(lblFilename);
		
		tfFilename = new JTextField(30);
		tfFilename.setBounds(83, 7, 523, 20);
		tfFilename.setEditable(isEditable());
		center.add(tfFilename);
		
		JLabel lblLocation = new JLabel("Location: ");
		lblLocation.setBounds(7, 34, 47, 14);
		center.add(lblLocation);
		
		tfLocation = new JTextField(30);
		tfLocation.setBounds(83, 31, 523, 20);
		tfLocation.setEditable(isEditable());
		center.add(tfLocation);
		
		tfDescription = new JLabel("Description:");
		tfDescription.setBounds(7, 59, 64, 14);
		center.add(tfDescription);
		
		textField = new JTextField();
		textField.setBackground(UIManager.getColor("Button.background"));
		textField.setBounds(83, 55, 523, 23);
		center.add(textField);
		textField.setEditable(isEditable());
		textField.setColumns(10);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(7, 84, 599, 249);
		//scrollPane.getViewport().setBackground(Color.WHITE);
		center.add(scrollPane);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		textArea.setBackground(getBackground());
		
		buttonPanel = new JPanel();
		buttonPanel.setBounds(5, 341, 616, 33);
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
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
		return textArea.getText();
	}
	
	

	public String getFilenameText() {
		return tfFilename.getText();
	}
	
	public String getLocationText() {
		return tfLocation.getText();
	}
	
	public void addButtons(JPanel buttonPanel) {
		btnClose = new JButton("Close");
		buttonPanel.add(btnClose);
	}
	
	public void registerCloseListener(ActionListener listener) {
		btnClose.addActionListener(listener);
	}

	public boolean isEditable() {
		return false;
	}


	@Override
	public void registerListeners() {
		// TODO Auto-generated method stub
		
	}
	
}
