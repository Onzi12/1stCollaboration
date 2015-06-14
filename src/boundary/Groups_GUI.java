package boundary;

import common.Controller;
import common.JDialogBoundary;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JList;
import javax.swing.JButton;

public class Groups_GUI extends JDialogBoundary{

	private JList listMyGroups,list;
	private JButton btnJoin,btnLeave,btnSendRequests,btnClose;;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Groups_GUI(Controller controller) {
		super(controller);
	}

	@Override
	public void draw() {
		setBackground(Color.WHITE);
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 512, 325);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblGroups = new JLabel("Groups");
		lblGroups.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 30));
		lblGroups.setBounds(10, 11, 209, 42);
		panel.add(lblGroups);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.BLUE);
		separator.setBounds(10, 50, 484, 11);
		panel.add(separator);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(10, 88, 192, 233);
		panel.add(scrollPane);
		
		listMyGroups = new JList();
		scrollPane.setViewportView(listMyGroups);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.setBounds(306, 88, 192, 233);
		panel.add(scrollPane_1);
		
		list = new JList();
		scrollPane_1.setViewportView(list);
		
		btnJoin = new JButton("<< Join");
		btnJoin.setBounds(212, 130, 84, 23);
		panel.add(btnJoin);
		
		btnLeave = new JButton("Leave >>");
		btnLeave.setBounds(212, 189, 84, 23);
		panel.add(btnLeave);
		
		JLabel lblMyGroups = new JLabel("My Groups:");
		lblMyGroups.setFont(new Font("Tahoma", Font.ITALIC, 14));
		lblMyGroups.setBounds(10, 64, 114, 28);
		panel.add(lblMyGroups);
		
		JLabel lblOtherGroups = new JLabel("Other Groups:");
		lblOtherGroups.setFont(new Font("Tahoma", Font.ITALIC, 14));
		lblOtherGroups.setBounds(306, 65, 137, 27);
		panel.add(lblOtherGroups);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(0, 325, 512, 42);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		btnClose = new JButton("Close");
		btnClose.setBounds(413, 11, 89, 23);
		panel_1.add(btnClose);
		
		btnSendRequests = new JButton("Send Requests");
		btnSendRequests.setBounds(286, 11, 117, 23);
		panel_1.add(btnSendRequests);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerListeners() {
		
		// TODO Auto-generated method stub
		
	}
}
