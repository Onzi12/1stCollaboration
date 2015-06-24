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
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import model.Item;
import model.ItemFile;
import model.ItemFolder;
import common.Controller;
import common.JPanelBoundary;
import controller.MyBoxController;
import custom_gui.ImageRenderer;
import custom_gui.MyBoxTree;

/**
 * The {@link MyBox_GUI} class is the main GUI in the application. <br> 
 * Shows the user all the options in the application.
 */
public class MyBox_GUI extends JPanelBoundary {

	private static final long serialVersionUID = -3999897324978712720L;
	
	/**
	 * Add file button.
	 */
	private JButton btnAddFile;
	/**
	 * Update file button.
	 */
	private JButton btnUpdateFile;
	/**
	 * Delete file button.
	 */
	private JButton btnDeleteFile;
	/**
	 * Restore file button.
	 */
	private JButton btnRestoreFile;
	/**
	 * Logout button.
	 */
	private JButton btnLogout;
	/**
	 * Create new folder button.
	 */
	private JButton btnNewFolder;
	/**
	 * Show groups button.
	 */
	private JButton btnGroups;
	/**
	 * Delete folder button.
	 */
	private JButton btnRemoveFolder;
	/**
	 * The folder tree.
	 */
	private MyBoxTree tree;
	/**
	 * The file tabel.
	 */
	private JTable table;

	/**
	 * Construct {@link MyBox_GUI}.
	 * @param controller
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
		btnLogout.setBounds(568, 78, 120, 31);
		add(btnLogout);
		
		btnNewFolder = new JButton("+");
		btnNewFolder.setToolTipText("Create a new folder in the current folder");
		btnNewFolder.setFont(new Font("Tahoma", Font.BOLD, 9));
		btnNewFolder.setBounds(10, 154, 45, 25);
		add(btnNewFolder);
		
		btnGroups = new JButton("Groups");
		btnGroups.setToolTipText("Join Or Leave Groups");
		btnGroups.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnGroups.setBounds(289, 120, 107, 31);
		add(btnGroups);
		
		FileTreeCellRenderer renderer = new FileTreeCellRenderer();
		renderer.setIcon(UIManager.getIcon("FileChooser.newFolderIcon"));
		renderer.setLeafIcon(UIManager.getIcon("FileChooser.newFolderIcon"));
		
		tree = new MyBoxTree();
		tree.setVisibleRowCount(12);
		tree.setCellRenderer(renderer);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);    
        tree.setEditable(true);
        
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
		
		btnRemoveFolder = new JButton("-");
		btnRemoveFolder.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnRemoveFolder.setBounds(65, 154, 43, 25);
		add(btnRemoveFolder);
	}
	
	/**
	 * Add {@link MouseListener} to the file table.
	 * @param listener
	 */
	public void registerTableMouseListener(MouseListener listener) {
		table.addMouseListener(listener);
	}
	
	/**
	 * Add {@link MouseListener} to the view.
	 * @param listener
	 */
	public void registerMouseListener(MouseListener listener) {
		addMouseListener(listener);
	}
	
	/**
	 * Get the folder tree.
	 * @return {@link JTree}
	 */
	public MyBoxTree getTree() {
		return tree;
	}
	
	/**
	 * Get the file table.
	 * @return {@link JTable}
	 */
	public JTable getTable() {
		return table;
	}
	
	/**
	 * Get the selected file in the table.
	 * @param row
	 * @return {@link Item}
	 */
	public Item tableGetFile(int row) {
		return (Item) table.getValueAt(row, FileTableModel.OBJECT_COL);
	}
	
	/**
	 * Refresh the table.
	 * @param items
	 */
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
				control.btnDeleteFileClicked();
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
			public void mouseReleased(MouseEvent e) {
				if (!e.isConsumed()) {
					control.clickedOnTreeNode();
					e.consume();
				}
			}
		});
		
		btnRemoveFolder.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				control.btnRemoveFolderClicked();
				
			}
		});
		
		tree.getCellEditor().addCellEditorListener(new CellEditorListener() {
			
			@Override
			public void editingStopped(ChangeEvent e) {
				
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)getTree().getLastSelectedPathComponent();
				ItemFolder folder = (ItemFolder)node.getUserObject();
				folder.setName((String)getTree().getCellEditor().getCellEditorValue());
				control.finishedEditingFolderName(folder);
				
			}
			
			@Override
			public void editingCanceled(ChangeEvent e) {}
			
		});
		
	}
	
	/**
	 * Get the {@link MyBoxController}.
	 * @return
	 */
	public MyBoxController getController() {
		return (MyBoxController) controller;
	}
	
	/**
	 * Disable the create new folder and delete folder buttons.
	 */
	public void disableBtnNewFolder() {
		btnNewFolder.setEnabled(false);
		btnRemoveFolder.setEnabled(false);
	}
	
	/**
	 * Enable the craete new folder and delete folder buttons.
	 */
	public void enableBtnNewFolder() {
		btnNewFolder.setEnabled(true);
		btnRemoveFolder.setEnabled(true);
	}
}

