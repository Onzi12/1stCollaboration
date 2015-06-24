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


/**
 * 
 * Graphical User Interface that displays a window that allows to Read a file's name,Description and privilege
 *
 */
@SuppressWarnings("serial")
public class FileRead_GUI extends JDialogBoundary {

	/**
	 * Text Field to display the file name
	 */
	private JTextField tfFilename;
	
	/**
	 * Text Field to display the file Virtual Location
	 */
	private JTextField tfLocation;
	
	/**
	 * Close window button
	 */
	private JButton btnClose;
	
	/**
	 * Text Area to display file's Description
	 */
	private JTextArea taDescription;
	
	/**
	 * Selection box that Shows the file's privilege level
	 */
	private JComboBox<String> cbPrivilege;
	
	/**
	 * Download file button
	 */
	private JButton btnDownload;
	
	/**
	 * Constructs the File Read window
	 * @param controller
	 */
	public FileRead_GUI(Controller controller) {
		super(controller);
	}
	
	@Override
	public void draw() {
		setTitle("Read File");
		setBounds(100, 100, 647, 263);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		JPanel contents = new JPanel();
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
		
		JLabel lblDescription = new JLabel("Description:");
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
		
		JLabel lblPrivilege = new JLabel("Privilege:");
		lblPrivilege.setBounds(7, 59, 58, 23);
		center.add(lblPrivilege);
		
		cbPrivilege = new JComboBox<String>();
		cbPrivilege.setBackground(SystemColor.controlHighlight);
		cbPrivilege.setBounds(83, 57, 85, 24);
		cbPrivilege.addItem("Private");
		cbPrivilege.addItem("Group");
		cbPrivilege.addItem("Public");
		cbPrivilege.setEnabled(false);
		cbPrivilege.setEditable(false);
		center.add(cbPrivilege);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(UIManager.getColor("text"));
		buttonPanel.setBounds(10, 186, 616, 33);
		contents.add(buttonPanel);
		
		buttonPanel.setLayout(null);
		btnClose = new JButton("Close");
		btnClose.setBounds(523, 5, 88, 23);
		buttonPanel.add(btnClose);
		
		btnDownload = new JButton("Download");
		btnDownload.setBounds(412, 5, 101, 23);
		buttonPanel.add(btnDownload);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.BLUE);
		separator.setBounds(10, 45, 522, 11);
		contents.add(separator);
		
		JLabel lblReadFile = new JLabel("Read File");
		lblReadFile.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 30));
		lblReadFile.setBounds(10, 6, 209, 42);
		contents.add(lblReadFile);
	}
	
	/**
	 * inserts File name to the text field for display
	 * @param name
	 */
	public void setFilename(String name) {
		tfFilename.setText(name);
	}
	
	/**
	 * inserts the File Virtual location into the Text field for display
	 * @param location
	 */
	public void setLocation(String location) {
		tfLocation.setText(location);
	}
	
	
	/**
	 * inserts the File's Description to the Text Area for display
	 * @return
	 */
	public void setDescription(String des) {
		this.taDescription.setText(des);
	}

	/**
	 * inserts the File's Privilege level in the Selection Box
	 * @param index
	 */
	public void setCbPrivilegeIndex(int index) {
		this.cbPrivilege.setSelectedIndex(index);
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
		
		btnDownload.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				control.btnDownloadClicked();
				
			}
		});
		
	}
}
