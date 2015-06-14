package boundary;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import common.Controller;
import common.JDialogBoundary;

import controller.FileReadController;

public class FileRead_GUI extends JDialogBoundary {

	private static final long serialVersionUID = 3050895492014566356L;
	private JPanel contents, buttonPanel;
	private JTextField tfFilename, tfLocation;
	private JButton btnClose;
	private JLabel lblDescription;
	private JTextArea taDescription;
	private JLabel lblPrivilege;
	private JComboBox<String> cbPrivilege;
	
	public FileRead_GUI(Controller controller) {
		super(controller);
	}
	
	@Override
	public void draw() {
		setTitle("Read File");
		setBounds(100, 100, 647, 263);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		contents = new JPanel();
		contents.setBackground(UIManager.getColor("text"));
		contents.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contents);
		contents.setLayout(null);
		
		JPanel center = new JPanel();
		center.setBackground(UIManager.getColor("text"));
		center.setBounds(10, 54, 616, 134);
		contents.add(center);
		center.setLayout(null);
		
		JLabel lblFilename = new JLabel("Filename: ");
		lblFilename.setForeground(Color.BLACK);
		lblFilename.setBounds(7, 10, 66, 14);
		center.add(lblFilename);
		
		tfFilename = new JTextField(30);
		tfFilename.setBackground(SystemColor.controlHighlight);
		tfFilename.setBounds(83, 7, 523, 20);
		tfFilename.setEditable(false);
		center.add(tfFilename);
		
		JLabel lblPath = new JLabel("Path: ");
		lblPath.setBounds(7, 34, 47, 14);
		center.add(lblPath);
		
		tfLocation = new JTextField(30);
		tfLocation.setBackground(SystemColor.controlHighlight);
		tfLocation.setBounds(83, 31, 523, 20);
		tfLocation.setEditable(false);
		center.add(tfLocation);
		
		lblDescription = new JLabel("Description:");
		lblDescription.setBounds(7, 92, 79, 14);
		center.add(lblDescription);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );
		scrollPane.setBounds(83, 87, 523, 41);
		center.add(scrollPane);
		
		taDescription = new JTextArea();
		taDescription.setLineWrap(true);
		taDescription.setBorder(new LineBorder(new Color(0, 0, 0), 0));
		taDescription.setBackground(SystemColor.controlHighlight);
		taDescription.setEditable(false);
		scrollPane.setViewportView(taDescription);
		
		lblPrivilege = new JLabel("Privilege:");
		lblPrivilege.setBounds(7, 59, 58, 23);
		center.add(lblPrivilege);
		
		cbPrivilege = new JComboBox<String>();
		cbPrivilege.setBackground(SystemColor.controlHighlight);
		cbPrivilege.setBounds(83, 57, 85, 24);
		cbPrivilege.addItem("Public");
		cbPrivilege.addItem("Group");
		cbPrivilege.addItem("Private");
		cbPrivilege.setEnabled(false);
		cbPrivilege.setEditable(false);
		center.add(cbPrivilege);
		
		buttonPanel = new JPanel();
		buttonPanel.setBackground(UIManager.getColor("text"));
		buttonPanel.setBounds(10, 186, 616, 33);
		contents.add(buttonPanel);
		
		addButtons(buttonPanel);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.BLUE);
		separator.setBounds(10, 45, 522, 11);
		contents.add(separator);
		
		JLabel lblReadFile = new JLabel("Read File");
		lblReadFile.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 30));
		lblReadFile.setBounds(10, 6, 209, 42);
		contents.add(lblReadFile);
	}
	
	public void setFilename(String name) {
		tfFilename.setText(name);
	}
	
	public void setLocation(String location) {
		tfLocation.setText(location);
	}
	
	public String getTextAreaText() {
		return taDescription.getText();
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
