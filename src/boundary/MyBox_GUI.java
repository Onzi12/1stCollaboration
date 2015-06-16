package boundary;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import model.Item;
import model.ItemFile;
import common.Controller;
import common.JPanelBoundary;
import controller.MyBoxController;
import custom_gui.ImageRenderer;


public class MyBox_GUI extends JPanelBoundary {

	private static final long serialVersionUID = -3999897324978712720L;
	private JButton btnAddFile;
	private JButton btnUpdateFile;
	private JButton btnDeleteFile;
	private JButton btnRestoreFile;
	private JButton btnLogout;
	private JButton btnNewFolder;
	private JButton btnGroups;
	private JTree tree;
	private JTable table;


	/**
	 * Create the frame.
	 */
	public MyBox_GUI(Controller controller) {
		super(controller);
	}
	
	@Override
	public void draw() {
		
		AppFrame.getInstance().setSize(742, 579);
		setBackground(Color.WHITE);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.BLUE);
		separator.setBounds(0, 54, 522, 11);
		add(separator);
		
		JLabel myBoxLbl = new JLabel("MyBox");
		myBoxLbl.setFont(new Font("Tahoma", Font.BOLD, 30));
		myBoxLbl.setBounds(12, 13, 172, 37);
		add(myBoxLbl);
		
		btnAddFile = new JButton("Add File");
		btnAddFile.setToolTipText("Add a file from WorldBox ");
		btnAddFile.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnAddFile.setBounds(12, 78, 107, 31);
		add(btnAddFile);
		
		btnUpdateFile = new JButton("Update File");
		btnUpdateFile.setToolTipText("<html>To update file info or contact<br>\r\nmark a file and  press here</html>");
		btnUpdateFile.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnUpdateFile.setBounds(139, 78, 120, 31);
		add(btnUpdateFile);
		
		btnDeleteFile = new JButton("Delete File");
		btnDeleteFile.setToolTipText("Press a file and press here to Delete a file from MyBox");
		btnDeleteFile.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnDeleteFile.setBounds(279, 78, 120, 31);
		add(btnDeleteFile);
		
		btnRestoreFile = new JButton("Restore File");
		btnRestoreFile.setToolTipText("Restore a deleted file ");
		btnRestoreFile.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnRestoreFile.setBounds(419, 78, 128, 31);
		add(btnRestoreFile);
		
		btnLogout = new JButton("LogOut");
		//btnLogout.setToolTipText("Log out...");
		btnLogout.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnLogout.setBounds(567, 78, 120, 31);
		add(btnLogout);
		
		btnNewFolder = new JButton("New Folder");
		btnNewFolder.setToolTipText("Create a new folder in the current folder");
		btnNewFolder.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewFolder.setBounds(12, 122, 123, 31);
		add(btnNewFolder);
		
		btnGroups = new JButton("Groups");
		btnGroups.setToolTipText("Join Or Leave Groups");
		btnGroups.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnGroups.setBounds(149, 122, 107, 31);
		add(btnGroups);
		
		FileTreeCellRenderer renderer = new FileTreeCellRenderer();
		renderer.setIcon(UIManager.getIcon("FileChooser.newFolderIcon"));
		renderer.setLeafIcon(UIManager.getIcon("FileChooser.newFolderIcon"));
		
		tree = new JTree();
		tree.setVisibleRowCount(12);
		tree.setCellRenderer(renderer);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);        
        
		JScrollPane scrollBox = new JScrollPane(tree);
		scrollBox.setBorder(BorderFactory.createEmptyBorder());
        scrollBox.setBounds(20, 190, 150, 331);
		add(scrollBox);

		table = new JTable();
		table.setRowHeight(24);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setBackground(Color.WHITE);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(188, 190, 500, 331);
		scrollPane.getViewport().setBackground(Color.WHITE);
		add(scrollPane);
	}
	
	
	public void registerTableMouseListener(MouseListener listener) {
		table.addMouseListener(listener);
	}
	
	public void registerMouseListener(MouseListener listener) {
		addMouseListener(listener);
	}
	
	public void registerTreeModeListener(TreeModelListener listener) {
		getTree().getModel().addTreeModelListener(listener);
	}

	public JTree getTree() {
		return tree;
	}
	
	public DefaultMutableTreeNode getRoot() {
		DefaultMutableTreeNode root = (DefaultMutableTreeNode)getTree().getModel().getRoot();
		return root;
	}
	
	public JTable getTable() {
		return table;
	}
	
	public Item tableGetFile(int row) {
		return (Item) table.getValueAt(row, FileTableModel.OBJECT_COL);
	}
	
	public void refreshTable(ArrayList<Item> items) {
		
		ArrayList<ItemFile> files = new ArrayList<ItemFile>();
		
		for (Item item : items) {
			if (item instanceof ItemFile) {
				files.add((ItemFile)item);
			}
		}
		
		FileTableModel model = new FileTableModel(files);
		table.setModel(model);
	    table.getColumnModel().getColumn(0).setCellRenderer(new ImageRenderer());
	}
	

	@Override
	public void registerListeners() {
		
		final MyBoxController control = (MyBoxController)controller;
		
		btnAddFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				control.btnAddFileClicked();
			}
		});
		
		btnDeleteFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				((MyBoxController)controller).btnDeleteFileClicked();
			}
		});
		
		btnGroups.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			control.btnGroupsClicked();	
			}
		});
		
		btnLogout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				control.btnLogoutClicked();
			}
		});
		
		btnNewFolder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				control.btnNewFolderClicked();	
			}
		});
		
		btnRestoreFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				control.btnRestoreFileClicked();
			}
		});
		
		btnUpdateFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				control.btnUpdateFileClicked();
			}
		});
		
		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				control.clickedOnTreeNode();
			}
		});
	}
	
	public MyBoxController getController() {
		return (MyBoxController) controller;
	}
	
}

