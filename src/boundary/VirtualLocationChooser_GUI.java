package boundary;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.UIManager;
import javax.swing.tree.TreeSelectionModel;

import common.Controller;
import common.JDialogBoundary;

import controller.VirtualLocationChooserController;
import custom_gui.MyBoxTree;
/**
 * The {@link VirtualLocationChooser_GUI} is a GUI class that show the tree of the user folders.
 * <br> The class allows the user to choose a virtual location for a file in the the MyBox application.
 */
@SuppressWarnings("serial")
public class VirtualLocationChooser_GUI extends JDialogBoundary {

	/**
	 * Close button.
	 */
	private JButton btnClose;
	/**
	 * OK button.
	 */
	private JButton btnOK;
	
	/**
	 * The tree.
	 */
	private MyBoxTree tree;
	
	/**
	 * Constructs the {@link VirtualLocationChooser_GUI}.
	 * @param controller
	 */
	public VirtualLocationChooser_GUI(Controller controller) {
		super(controller);
	}

	@Override
	public void draw() {
		setTitle("Choose Folder");
		getContentPane().setBackground(UIManager.getColor("text"));
		setBounds(100, 100, 223, 382);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		getContentPane().setLayout(null);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.BLUE);
		separator.setBounds(10, 50, 181, 11);
		getContentPane().add(separator);
		
		JPanel contents = new JPanel();
		contents.setBounds(10, 70, 187, 227);
		getContentPane().add(contents);
		contents.setBackground(UIManager.getColor("text"));
		contents.setLayout(null);
		
		FileTreeCellRenderer renderer = new FileTreeCellRenderer();
		renderer.setIcon(UIManager.getIcon("FileChooser.newFolderIcon"));
		renderer.setLeafIcon(UIManager.getIcon("FileChooser.newFolderIcon"));
		
		tree = new MyBoxTree();
		tree.setVisibleRowCount(12);
		tree.setCellRenderer(renderer);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);        
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 186, 227);
		scrollPane.setBackground(UIManager.getColor("text"));
		contents.add(scrollPane);
		
		scrollPane.setViewportView(tree);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBounds(10, 308, 187, 30);
		getContentPane().add(buttonPanel);
		buttonPanel.setLayout(null);
		buttonPanel.setBackground(UIManager.getColor("text"));
		
		btnClose = new JButton("Close");
		btnClose.setBounds(98, 0, 89, 23);
		buttonPanel.add(btnClose);
		
		btnOK = new JButton("OK");
		btnOK.setBounds(0, 0, 89, 23);
		buttonPanel.add(btnOK);
		
		JLabel lblAddFile = new JLabel("Choose folder");
		lblAddFile.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 22));
		lblAddFile.setBounds(10, 11, 396, 42);
		getContentPane().add(lblAddFile);
	}

	@Override
	public void registerListeners() {
		
		final VirtualLocationChooserController control = (VirtualLocationChooserController)controller;
		
		btnOK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				control.btnOKClicked();
			}
		});
		
		btnClose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				control.btnCloseClicked();
			}
		});
		
		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				control.clickedOnTreeNode();
			}
		});
		
	}
	
	/**
	 * Get the tree.
	 * @return {@link MyBoxTree}
	 */
	public MyBoxTree getTree() {
		return tree;
	}

}
