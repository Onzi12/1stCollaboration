package boundary;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;
import common.Displayable;

public class PortAndIP_GUI extends JDialog implements Displayable{

	private static final long serialVersionUID = 1357093255522087260L;
	private JPanel contents, buttonPanel;
	private JTextField tfPort, tfIP;
	private JButton btnSave, btnCancel;
	
	public PortAndIP_GUI(JFrame parent) {
		super(parent);
		displayWindow();
	}
	
	@Override
	public void displayWindow() {
		setBounds(100, 100, 300, 150);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setAlwaysOnTop(true);
		
		contents = new JPanel();
		contents.setBorder(new EmptyBorder(5, 5, 5, 5));
		contents.setLayout(new BorderLayout());
		setContentPane(contents);
		
		JPanel center = new JPanel();
		center.setLayout(new MigLayout());
		contents.add(center, BorderLayout.CENTER);
		
		JLabel lblPort = new JLabel("PORT: ");
		center.add(lblPort);
		
		tfPort = new JTextField(30);
		tfPort.setText("");
		center.add(tfPort, "wrap, span");
		
		JLabel lblIP = new JLabel("IP: ");
		center.add(lblIP);
		
		tfIP = new JTextField(30);
		tfIP.setText("");
		center.add(tfIP, "wrap, span");
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		contents.add(buttonPanel, BorderLayout.SOUTH);
		
		btnSave = new JButton("Save");
		buttonPanel.add(btnSave);
		
		btnCancel = new JButton("Cancel");
		buttonPanel.add(btnCancel);
	}
	
	public void registerSaveListener(ActionListener listener) {
		btnSave.addActionListener(listener);
	}
	
	public void registerCancelListener(ActionListener listener) {
		btnCancel.addActionListener(listener);
	}
	
	public int getPort() {
		return Integer.parseInt(tfPort.getText());
	}
	
	public String getHost() {
		return tfIP.getText();
	}
	
	public void setPort(int port) {
		tfPort.setText(Integer.toString(port));
	}
	
	public void setIP(String ip) {
		tfIP.setText(ip);
	}
	
	@Override
	public void showMessage(String str) {}
	
	@Override
	public void closeWindow() {
		setVisible(false);
		dispose();
	}
	
	public static void main(String args[])
	{
		new PortAndIP_GUI(null).setVisible(true);
	}
	
}
