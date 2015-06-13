package boundary;

import common.Controller;
import common.JDialogBoundary;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JList;
import java.awt.Color;
import java.awt.event.ActionListener;
import javax.swing.JTable;
import java.awt.event.ActionEvent;
import javax.swing.ScrollPaneConstants;
import controller.FileAddController;
public class FileAdd_GUI extends JDialogBoundary {

	
	
	private static final long serialVersionUID = 1L;
	
	private JList list;
	private JPanel contents,buttonPanel;
	private JButton btnClose,btnAddFile;
	private JScrollPane scrollPane;
	private JTable table;
	
	public FileAdd_GUI(Controller controller) {
		super(controller);
	}
	
	

	@Override
	public void draw() {
		setTitle("Add File");
		
		setBounds(100, 100, 647, 500);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		getContentPane().setLayout(null);
		
		table = new JTable();
		table.setRowHeight(24);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setBackground(Color.WHITE);
		
		contents = new JPanel();
		contents.setBounds(10, 11, 606, 390);
		getContentPane().add(contents);
		contents.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(0, 0, 606, 390);
		contents.add(scrollPane);
		
		scrollPane.setViewportView(table);
		
		buttonPanel = new JPanel();
		buttonPanel.setBounds(10, 412, 606, 30);
		getContentPane().add(buttonPanel);
		buttonPanel.setLayout(null);
		
		btnClose = new JButton("Close");
		btnClose.setBounds(517, 0, 89, 23);
		buttonPanel.add(btnClose);
		
		btnAddFile = new JButton("Add File");
		btnAddFile.setBounds(419, 0, 89, 23);
		buttonPanel.add(btnAddFile);
			
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
	
}
