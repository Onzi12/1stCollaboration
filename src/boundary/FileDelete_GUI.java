package boundary;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

import model.ItemFile;
import common.Controller;
import common.JDialogBoundary;
import controller.FileDeleteController;

import java.awt.Rectangle;
import java.io.File;
import java.awt.Component;

public class FileDelete_GUI extends JDialogBoundary{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private DefaultListModel<ItemFile> listModel;
	private JButton btnDelete;
	private JButton btnClose;
	private JList<ItemFile> listFiles;

	public FileDelete_GUI(Controller controller) {
		super(controller);
		
	}

	@Override
	public void draw() {
		setBounds(new Rectangle(150, 200, 580, 380));
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		
		JPanel panelContents = new JPanel();
		panelContents.setBackground(Color.WHITE);
		panelContents.setBounds(0, 0, 548, 292);
		getContentPane().add(panelContents);
		panelContents.setLayout(null);
		
		JLabel lblDeletePhysicalFile = new JLabel("Delete Physical file");
		lblDeletePhysicalFile.setBounds(10, 11, 380, 42);
		lblDeletePhysicalFile.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 30));
		panelContents.add(lblDeletePhysicalFile);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 50, 483, 11);
		separator.setForeground(Color.BLUE);
		panelContents.add(separator);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 59, 528, 230);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panelContents.add(scrollPane);
		
		listModel = new DefaultListModel<ItemFile>();
		listFiles = new JList<ItemFile>();
		listFiles.setModel(listModel);
		listFiles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(listFiles);
		
		JPanel panelButtons = new JPanel();
		panelButtons.setBackground(Color.WHITE);
		panelButtons.setBounds(10, 286, 528, 43);
		getContentPane().add(panelButtons);
		panelButtons.setLayout(null);
		
		btnClose = new JButton("Close");
		btnClose.setBounds(439, 11, 89, 23);
		panelButtons.add(btnClose);
		
		btnDelete = new JButton("Delete");
		btnDelete.setBounds(337, 11, 89, 23);
		panelButtons.add(btnDelete);
		
	}

	@Override
	public void registerListeners() {
			final FileDeleteController control = (FileDeleteController)controller;
			
			btnClose.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					control.btnCloseClicked();
				}
			});
		
			btnDelete.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					control.btnDeleteClicked();
				}
			});
	}

	public ItemFile getListValue() {
		return listFiles.getSelectedValue();
	}

	public void addListValue(ItemFile x) {
		listModel.addElement(x);
	}
	
	
}
