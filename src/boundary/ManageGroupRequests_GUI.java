package boundary;
import common.Controller;
import common.JDialogBoundary;
import javax.swing.JPanel;
import java.awt.SystemColor;
import javax.swing.JSeparator;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class ManageGroupRequests_GUI extends JDialogBoundary {
	private JTable table;


	public ManageGroupRequests_GUI(Controller controller) {
		super(controller);
		getContentPane().setBackground(SystemColor.window);
		getContentPane().setLayout(null);
		
		JPanel panelContent = new JPanel();
		panelContent.setBackground(SystemColor.window);
		panelContent.setBounds(0, 0, 550, 316);
		getContentPane().add(panelContent);
		panelContent.setLayout(null);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.BLUE);
		separator.setBounds(10, 50, 522, 11);
		panelContent.add(separator);
		
		JLabel lblManageGroupRequests = new JLabel("Manage Group Requests");
		lblManageGroupRequests.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 30));
		lblManageGroupRequests.setBounds(10, 11, 413, 42);
		panelContent.add(lblManageGroupRequests);
		
		JScrollPane spRequests = new JScrollPane();
		spRequests.setBounds(10, 59, 530, 253);
		panelContent.add(spRequests);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setCellSelectionEnabled(true);
		spRequests.setViewportView(table);
		
		JPanel panelButtons = new JPanel();
		panelButtons.setBackground(SystemColor.window);
		panelButtons.setBounds(10, 317, 530, 29);
		getContentPane().add(panelButtons);
		panelButtons.setLayout(null);
		
		JButton btnClose = new JButton("Close");
		btnClose.setBounds(441, 0, 89, 23);
		panelButtons.add(btnClose);
		
		JButton btnAccept = new JButton("Accept");
		btnAccept.setBounds(344, 0, 89, 23);
		panelButtons.add(btnAccept);
		
		JButton btnDecline = new JButton("Decline");
		btnDecline.setBounds(245, 0, 89, 23);
		panelButtons.add(btnDecline);
	}

	@Override
	public void draw() {
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerListeners() {
		// TODO Auto-generated method stub
		
	}
}
