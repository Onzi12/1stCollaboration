package boundary;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;


import controller.ServerController;

public class Server_GUI extends JFrame {

	private static final long serialVersionUID = 6920888393421201468L;
	public static final int DEFAULT_PORT = 2222;
	public static final String DEFAULT_URL = "jdbc:mysql://localhost/test";
	public static final String DEFAULT_USER = "root";
	public static final String DEFAULT_PASSWORD = "Braude";
	private JButton btnStart;
	private JTextArea log;
	private JTextField tPortNumber;
	private SimpleDateFormat dateFormat;
	private JTextField tfURL;
	private JTextField tfUser;
	private JTextField tfPassword;
	private JLabel lblHostIP;

	public Server_GUI() {
		super("Server");
		draw();
	}
	
	public String getPort() {
		return tPortNumber.getText().trim();
	}
	
	public String getURL() {
		return tfURL.getText();
	}
	
	public String getUser() {
		return tfUser.getText();
	}
	
	public String getPassword() {
		return tfPassword.getText();
	}
	
	
	public void draw() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 400);
		setLocationRelativeTo(null);
		setResizable(false);
		dateFormat = new SimpleDateFormat("HH:mm:ss");
		setLayout(new BorderLayout());
		
		JPanel north = new JPanel(new MigLayout());
		north.add(new JLabel("URL: "));
		tfURL = new JTextField(DEFAULT_URL + "   ");
		north.add(tfURL);
		north.add(new JLabel("User: "));
		tfUser = new JTextField(DEFAULT_USER + "   ");
		north.add(tfUser);
		north.add(new JLabel("Password: "));
		tfPassword = new JTextField(DEFAULT_PASSWORD + "   ");
		north.add(tfPassword);
		north.add(new JLabel("Port number: "));
		tPortNumber = new JTextField(DEFAULT_PORT + "   ");
		north.add(tPortNumber, "span, wrap");
		lblHostIP = new JLabel();
		north.add(lblHostIP, "span, wrap");
		btnStart = new JButton("Start");
		north.add(btnStart, "span, wrap");
		add(north, BorderLayout.NORTH);
		
		log = new JTextArea(8, 8);
		log.setEditable(false);
		JScrollPane scroll = new JScrollPane(log);
		add(scroll);
	}
	
	public void displayHostIP(String ip) {
		lblHostIP.setText(ip);
	}
	
	public void registerStartListener(ActionListener listener) {
		btnStart.addActionListener(listener);
	}


	public void showMessage(String str) {
		log.append(" " + dateFormat.format(new Date()) + ": " + str + "\n");
	}
	

	public void closeWindow() {
		setVisible(false);
		dispose();
	}
	
	public void disableUI() {
		btnStart.setEnabled(false);
		btnStart.setVisible(false);
		tPortNumber.setEditable(false);
		tfPassword.setEditable(false);
		tfURL.setEditable(false);
		tfUser.setEditable(false);
	}

}
