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

/**
 * Graphical User Interface that allows a user to initialize the server and allows to view the event log.
 */
public class Server_GUI extends JFrame {

	private static final long serialVersionUID = 6920888393421201468L;
	public static final int DEFAULT_PORT = 2222;
	public static final String DEFAULT_URL = "jdbc:mysql://localhost/test";
	public static final String DEFAULT_USER = "root";
	public static final String DEFAULT_PASSWORD = "root";
	
	/**
	 * Start server button.
	 */
	private JButton btnStart;
	
	/**
	 * The log view.
	 */
	private JTextArea log;
	
	/**
	 * Port field.
	 */
	private JTextField tPortNumber;
	private SimpleDateFormat dateFormat;
	
	/**
	 * Data base URL field.
	 */
	private JTextField tfURL;
	
	/**
	 * Data base User name field.
	 */
	private JTextField tfUser;
	
	/**
	 * Data base password field.
	 */
	private JTextField tfPassword;
	
	/**
	 * The server IP.
	 */
	private JLabel lblHostIP;
	
	/**
	 * Close server button.
	 */
	private JButton btnClose;
	
	/**
	 * Initialize data base button.
	 */
	private JButton btnInitDB;

	/**
	 * Constructs the {@link Server_GUI}.
	 */
	public Server_GUI() {
		super("Server");
		draw();
	}
	
	/**
	 * Get the port text.
	 * @return {@link String}
	 */
	public String getPort() {
		return tPortNumber.getText().trim();
	}
	
	/**
	 * Get the data base URL.
	 * @return {@link String}
	 */
	public String getURL() {
		return tfURL.getText();
	}
	
	/**
	 * Get the user name text.
	 * @return {@link String}
	 */
	public String getUser() {
		return tfUser.getText();
	}
	
	/**
	 * Get the password text.
	 * @return String
	 */
	public String getPassword() {
		return tfPassword.getText();
	}
	
	/**
	 * Draw the view.
	 */
	public void draw() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 400);
		setLocationRelativeTo(null);
		setResizable(false);
		dateFormat = new SimpleDateFormat("HH:mm:ss");
		getContentPane().setLayout(new BorderLayout());
		
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
		north.add(btnStart, "gap unrelated");
		btnClose = new JButton("Close");
		btnClose.setEnabled(false);
		north.add(btnClose,"gap unrelated");
		btnInitDB = new JButton("Init Database");
		north.add(btnInitDB,"cell 6 2 3 3");
		getContentPane().add(north, BorderLayout.NORTH);
		
		
		
		log = new JTextArea(8, 8);
		log.setEditable(false);
		JScrollPane scroll = new JScrollPane(log);
		getContentPane().add(scroll);
	}
	
	/**
	 * Display the server IP.
	 * @param ip
	 */
	public void displayHostIP(String ip) {
		lblHostIP.setText(ip);
	}
	
	/**
	 * Add {@link ActionListener} to the start button.
	 * @param listener
	 */
	public void registerStartListener(ActionListener listener) {
		btnStart.addActionListener(listener);
	}
	
	/**
	 * Add {@link ActionListener} to the close button.
	 * @param listener
	 */
	public void registerCloseListener(ActionListener listener) {
		btnClose.addActionListener(listener);
	}

	/**
	 * Add {@link ActionListener} to the initialize data base button.
	 * @param listener
	 */
	public void registerInitDBListener(ActionListener listener) {
		btnInitDB.addActionListener(listener);
	}

	/**
	 * Add a message to the log view.
	 * @param str
	 */
	public void showMessage(String str) {
		log.append(" " + dateFormat.format(new Date()) + ": " + str + "\n");
	}
	
	/**
	 * Close the window.
	 */
	public void close() {
		setVisible(false);
		dispose();
	}
	
	/**
	 * Disable all the buttons except for btnClose and make text fields uneditable.
	 */
	public void disableUI() {
		btnStart.setEnabled(false);
		btnStart.setVisible(false);
		btnClose.setEnabled(true);
		btnInitDB.setVisible(false);
		tPortNumber.setEditable(false);
		tfPassword.setEditable(false);
		tfURL.setEditable(false);
		tfUser.setEditable(false);
	}
	
	/**
	 * Enables all the buttons except for btnClose and make text fields editable.
	 */
	public void enableUI() {
		btnStart.setEnabled(true);
		btnStart.setVisible(true);
		btnClose.setEnabled(false);
		btnInitDB.setVisible(true);
		tPortNumber.setEditable(true);
		tfPassword.setEditable(true);
		tfURL.setEditable(true);
		tfUser.setEditable(true);
		displayHostIP("");
	}

}
