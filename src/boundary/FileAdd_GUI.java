package boundary;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;

import model.ItemFile;
import common.Controller;
import common.JDialogBoundary;
import controller.FileAddController;

/**
 * Graphical User Interface that allows a user to Add an existing file to his own virtual space
 * @author Idan
 *
 */
@SuppressWarnings("serial")
public class FileAdd_GUI extends JDialogBoundary {


	/**
	 * Add Existing file to Private virtual space button
	 */
	private JButton btnAddFile;
	
	/**
	 * Close window button
	 */
	private JButton btnClose;
	
	/**
	 * Files list that is displayed in the Available files to add list
	 */
	private JList<ItemFile> listFiles;
	
	/**
	 * Model to design the file list
	 */
	private DefaultListModel<ItemFile> listModel;
	
	/**
	 * Constructs the window and lists a controller to operate all Listeners
	 * @param controller
	 */
	public FileAdd_GUI(Controller controller) {
		super(controller);
	}
	
	

	@Override
	public void draw() {
		
		JScrollPane scrollPane;
		
		setTitle("Add File");
		getContentPane().setBackground(UIManager.getColor("text"));
		setBounds(100, 100, 647, 500);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		getContentPane().setLayout(null);
				
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.BLUE);
		separator.setBounds(10, 50, 522, 11);
		getContentPane().add(separator);
		
		JPanel contents = new JPanel();
		contents.setBounds(10, 70, 606, 331);
		getContentPane().add(contents);
		contents.setBackground(UIManager.getColor("text"));
		contents.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 606, 331);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBackground(UIManager.getColor("text"));
		
		listModel = new DefaultListModel<ItemFile>();
		
		listFiles = new JList<ItemFile>();
		listFiles.setModel(listModel);
		listFiles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		scrollPane.setViewportView(listFiles);
		
		contents.add(scrollPane);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel = new JPanel();
		buttonPanel.setBounds(10, 412, 606, 30);
		getContentPane().add(buttonPanel);
		buttonPanel.setLayout(null);
		buttonPanel.setBackground(UIManager.getColor("text"));
		
		btnClose = new JButton("Close");
		btnClose.setBounds(517, 0, 89, 23);
		buttonPanel.add(btnClose);
		
		btnAddFile = new JButton("Add File");
		btnAddFile.setBounds(419, 0, 89, 23);
		buttonPanel.add(btnAddFile);
		
		JLabel lblAddFile = new JLabel("ADD File");
		lblAddFile.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 30));
		lblAddFile.setBounds(10, 11, 209, 42);
		getContentPane().add(lblAddFile);
		
	}


	
	@Override
	public void registerListeners() {
		
		final FileAddController control = (FileAddController)controller;
		
		btnAddFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				control.btnAddFileClicked();
			}
		});
		
		btnClose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				control.btnCloseClicked();
			}
		});
		

	}

	/**
	 * Method that adds a file to the available files to add list
	 * @param file
	 */
	public void addListValue(ItemFile file) {
		listModel.addElement(file);
	}
	
	/**
	 * returns the selected file to add from the list
	 * @return ItemFile object
	 */
	public ItemFile getSelectedFile() {
		return listModel.getElementAt(listFiles.getSelectedIndex());
	}
}
