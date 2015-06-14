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
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import java.awt.Font;


public class FileAdd_GUI extends JDialogBoundary {

	
	
	private static final long serialVersionUID = 1L;
	
	private JList list;
	private JPanel contents,buttonPanel;
	private JButton btnClose,btnAddFile;
	private JScrollPane scrollPane;
	private JTable table;
	
	public FileAdd_GUI(Controller controller) {
		super(controller);
		getContentPane().setBackground(UIManager.getColor("text"));
		setVisible(true);
	}
	
	

	@Override
	public void draw() {
		setTitle("Add File");
		
		setBounds(100, 100, 647, 500);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		getContentPane().setLayout(null);
		
		table = new JTable();
		table.setBackground(UIManager.getColor("text"));
		table.setRowHeight(24);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setBackground(Color.BLACK);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.BLUE);
		separator.setBounds(10, 50, 522, 11);
		getContentPane().add(separator);
		
		contents = new JPanel();
		contents.setBounds(10, 70, 606, 331);
		getContentPane().add(contents);
		contents.setBackground(UIManager.getColor("text"));
		contents.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 606, 331);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBackground(UIManager.getColor("text"));
		contents.add(scrollPane);
		
		scrollPane.setViewportView(table);
		
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
}
