package boundary;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

import custom_gui.ImageRenderer;
import model.Item;
import common.Boundary;
import common.Displayable;

public class MyBox_GUI extends Boundary {

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
	
	public final static String ACTION_COMMAND_UPDATE_FILE = "UpdateFile";
	public final static String ACTION_COMMAND_ADD_FILE = "AddFile";
	public final static String ACTION_COMMAND_DELETE_FILE = "DeleteFile";
	public final static String ACTION_COMMAND_RESTORE_FILE = "RestoreFile";
	public final static String ACTION_COMMAND_LOGOUT = "Logout";
	public final static String ACTION_COMMAND_NEW_FOLDER = "NewFolder";
	public final static String ACTION_COMMAND_GROUPS = "Groups";

	/**
	 * Create the frame.
	 */
	public MyBox_GUI() {
		displayWindow();
	}
	
	@Override
	public void displayWindow() {
		
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
		btnAddFile.setActionCommand(ACTION_COMMAND_ADD_FILE);
		btnAddFile.setToolTipText("Add a file from WorldBox ");
		btnAddFile.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnAddFile.setBounds(12, 78, 107, 31);
		add(btnAddFile);
		
		btnUpdateFile = new JButton("Update File");
		btnUpdateFile.setActionCommand(ACTION_COMMAND_UPDATE_FILE);
		btnUpdateFile.setToolTipText("<html>To update file info or contact<br>\r\nmark a file and  press here</html>");
		btnUpdateFile.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnUpdateFile.setBounds(139, 78, 120, 31);
		add(btnUpdateFile);
		
		btnDeleteFile = new JButton("Delete File");
		btnDeleteFile.setActionCommand(ACTION_COMMAND_DELETE_FILE);
		btnDeleteFile.setToolTipText("Press a file and press here to Delete a file from MyBox");
		btnDeleteFile.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnDeleteFile.setBounds(279, 78, 120, 31);
		add(btnDeleteFile);
		
		btnRestoreFile = new JButton("Restore File");
		btnRestoreFile.setActionCommand(ACTION_COMMAND_RESTORE_FILE);
		btnRestoreFile.setToolTipText("Add a file from WorldBox ");
		btnRestoreFile.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnRestoreFile.setBounds(419, 78, 128, 31);
		add(btnRestoreFile);
		
		btnLogout = new JButton("LogOut");
		btnLogout.setActionCommand(ACTION_COMMAND_LOGOUT);
		btnLogout.setToolTipText("Add a file from WorldBox ");
		btnLogout.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnLogout.setBounds(567, 78, 120, 31);
		add(btnLogout);
		
		btnNewFolder = new JButton("New Folder");
		btnNewFolder.setActionCommand(ACTION_COMMAND_NEW_FOLDER);
		btnNewFolder.setToolTipText("Add a file from WorldBox ");
		btnNewFolder.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewFolder.setBounds(12, 122, 123, 31);
		add(btnNewFolder);
		
		btnGroups = new JButton("Groups");
		btnGroups.setActionCommand(ACTION_COMMAND_GROUPS);
		btnGroups.setToolTipText("Add a file from WorldBox ");
		btnGroups.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnGroups.setBounds(149, 122, 107, 31);
		add(btnGroups);
		
		DefaultMutableTreeNode fileSystem = new DefaultMutableTreeNode("Root");
		FileTreeCellRenderer renderer = new FileTreeCellRenderer();
		renderer.setIcon(UIManager.getIcon("FileChooser.newFolderIcon"));
		renderer.setLeafIcon(UIManager.getIcon("FileChooser.newFolderIcon"));
		
		FileTreeModel model = new FileTreeModel(fileSystem);
		tree = new JTree(model);
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
	
	public void registerAddFileListener(ActionListener listener) {
		btnAddFile.addActionListener(listener);
	}
	
	public void registerUpdateFileListener(ActionListener listener) {
		btnUpdateFile.addActionListener(listener);
	}
	
	public void registerDeleteFileListener(ActionListener listener) {
		btnDeleteFile.addActionListener(listener);
	}
	
	public void registerRestoreFileListener(ActionListener listener) {
		btnRestoreFile.addActionListener(listener);
	}
	
	public void registerLogoutListener(ActionListener listener) {
		btnLogout.addActionListener(listener);
	}
	
	public void registerNewFolderListener(ActionListener listener) {
		btnNewFolder.addActionListener(listener);
	}

	public void registerGroupsListener(ActionListener listener) {
		btnGroups.addActionListener(listener);
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
	
	public JTable getTable() {
		return table;
	}
	
	public Item tableGetFile(int row) {
		return (Item) table.getValueAt(row, FileTableModel.OBJECT_COL);
	}
	
	public void refreshTable(ArrayList<Item> files) {
		FileTableModel model = new FileTableModel(files);
		table.setModel(model);
	    table.getColumnModel().getColumn(0).setCellRenderer(new ImageRenderer());
	}
	
	@Override
	public void showMessage(String str) {
		JOptionPane.showMessageDialog(this, str, "ERROR", JOptionPane.ERROR_MESSAGE);
	}

	
	@Override
	public void closeWindow() {}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerListeners() {
		// TODO Auto-generated method stub
		
	}
	
}

