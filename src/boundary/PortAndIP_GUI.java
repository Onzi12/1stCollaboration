package boundary;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

import common.Controller;
import common.JDialogBoundary;

import controller.PortAndIPController;

/**
 * Graphical User Interface that allows a user to set the details of the host.
 */
public class PortAndIP_GUI extends JDialogBoundary {


	private static final long serialVersionUID = 1357093255522087260L;
	
	/**
	 * The port text field.
	 */
	private JTextField tfPort;
	/**
	 * The IP text field.
	 */
	private JTextField tfIP;
	/**
	 * Save button.
	 */
	private JButton btnSave;
	/**
	 * Cancel button.
	 */
	private JButton btnCancel;

	/**
	 * Constructs the {@link PortAndIP_GUI}.
	 * @param controller
	 */
	public PortAndIP_GUI(Controller controller) {
		super(controller);
	}
	
	@Override
	public void draw() {
		setBounds(100, 100, 300, 150);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setAlwaysOnTop(true);
		
		JPanel contents = new JPanel();
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
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		contents.add(buttonPanel, BorderLayout.SOUTH);
		
		btnSave = new JButton("Save");
		buttonPanel.add(btnSave);
		
		btnCancel = new JButton("Cancel");
		buttonPanel.add(btnCancel);
	}
	
	/**
	 * Get the port text.
	 * @return {@link String}
	 */
	public int getPort() {
		return Integer.parseInt(tfPort.getText());
	}
	
	/**
	 * Get the IP text.
	 * @return {@link String}
	 */
	public String getHost() {
		return tfIP.getText();
	}
	
	/**
	 * Set the port text.
	 * @param port
	 */
	public void setPort(int port) {
		tfPort.setText(Integer.toString(port));
	}
	
	/**
	 * Set the IP text.
	 * @param ip
	 */
	public void setIP(String ip) {
		tfIP.setText(ip);
	}
	

	
	@Override
	public void registerListeners() {
		final PortAndIPController control = (PortAndIPController)controller;
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				control.btnSaveClicked();
			}
		});
		
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				control.btnCancelClicked();
			}
		});
			
	}
	
}
