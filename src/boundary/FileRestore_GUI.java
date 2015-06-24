package boundary;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;

import model.ItemFile;
import common.Controller;
import common.JDialogBoundary;
import controller.FileRestoreController;

/**
 * Graphical User Interface that allows a user to Restore files that he has virtually deleted and he is their owner
  *
 */
@SuppressWarnings("serial")
public class FileRestore_GUI extends JDialogBoundary {
	
	/**
	 * a list that contains all files that the owner owns and has virtually deleted
	 */
	private JList<ItemFile> listFiles;
	
	/**
	 * a model to design the list
	 */
	private DefaultListModel<ItemFile> listModel;
	
	/**
	 * Close Window button
	 */
	private JButton btnClose;
	
	/**
	 * Restores the selected File
	 */
	private JButton btnRestore;

	public FileRestore_GUI(Controller controller) {
		super(controller);
		setBounds(new Rectangle(150, 200, 500, 400));
	}

	@Override
	public void draw() {
		
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 484, 293);
		getContentPane().add(panel);
		panel.setLayout(null);
				
		JLabel lblRestoreFile = new JLabel("Restore File");
		lblRestoreFile.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 30));
		lblRestoreFile.setBounds(10, 11, 425, 42);
		panel.add(lblRestoreFile);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.BLUE);
		separator.setBounds(10, 50, 425, 11);
		panel.add(separator);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 61, 464, 232);
		panel.add(scrollPane);
		
		listModel = new DefaultListModel<ItemFile>();
		
		listFiles = new JList<ItemFile>();
		listFiles.setModel(listModel);
		listFiles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		scrollPane.setViewportView(listFiles);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(10, 293, 474, 57);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		btnClose = new JButton("Close");
		btnClose.setBounds(375, 11, 89, 23);
		panel_1.add(btnClose);
		
		btnRestore = new JButton("Restore");
		btnRestore.setBounds(286, 11, 79, 23);
		panel_1.add(btnRestore);
		
	}

	@Override
	public void registerListeners() {
		
		final FileRestoreController control = (FileRestoreController)controller;
		
		btnRestore.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				control.btnRestoreClicked();
				
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
	 * adds a file to the list
	 * @param file
	 */
	public void addListValue(ItemFile file) {
		listModel.addElement(file);
	}
	
	/**
	 * Returns the selected file from the list
	 * @return
	 */
	public ItemFile getSelectedFile() {
		return listModel.getElementAt(listFiles.getSelectedIndex());
	}
}
