package boundary;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import common.Boundary;
import common.Controller;
import common.Displayable;
import custom_gui.TextPrompt;

public class Login_GUI extends Boundary {

	public Login_GUI(Controller controller) {
		super(controller);
		
	}

	private static final long serialVersionUID = 1868351078697687291L;
	private JTextField fieldUsername;
	private JPasswordField fieldPassword;
	private JButton btnSignIn;
	private JButton btnShowCreateAnAccount;
	private JButton btnIpPort;

	public final static String ACTION_COMMAND_SIGN_IN = "SignIn";
	public final static String ACTION_COMMAND_SHOW_CREATE_ACCOUNT = "ShowCreateAccount";
	public final static String ACTION_COMMAND_IP_PORT = "IP&Port";
	

	@Override
	public void draw() {
		setBackground(SystemColor.textHighlight);
		setLayout(null);
		
		JLabel lblWelcomeToMybox = new JLabel("Welcome to MyBox");
		lblWelcomeToMybox.setForeground(Color.WHITE);
		lblWelcomeToMybox.setBounds(61, 36, 193, 25);
		lblWelcomeToMybox.setFont(new Font("Tahoma", Font.BOLD, 20));
		add(lblWelcomeToMybox);
		
		JLabel lblAllYourStuff = new JLabel("All your stuff in MyBox :)");
		lblAllYourStuff.setForeground(Color.WHITE);
		lblAllYourStuff.setBounds(83, 91, 157, 17);
		lblAllYourStuff.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblAllYourStuff);
		
		fieldUsername = new JTextField();
		TextPrompt placeHolderUsername = new TextPrompt("Username", fieldUsername);
		placeHolderUsername.setForeground(Color.GRAY);
		placeHolderUsername.changeAlpha(0.5f);
		placeHolderUsername.changeStyle(Font.BOLD + Font.ITALIC);
		fieldUsername.setBounds(61, 144, 193, 20);
		add(fieldUsername);
		fieldUsername.setColumns(10);

		fieldPassword = new JPasswordField();
		TextPrompt placeHolderPassword = new TextPrompt("Password", fieldPassword);
		placeHolderPassword.setForeground(Color.GRAY);
		placeHolderPassword.changeAlpha(0.5f);
		placeHolderPassword.changeStyle(Font.BOLD + Font.ITALIC);
		fieldPassword.setBounds(61, 168, 193, 20);
		add(fieldPassword);
		fieldPassword.setColumns(10);
		
		btnSignIn = new JButton("Sign In");
		btnSignIn.setActionCommand(ACTION_COMMAND_SIGN_IN);
		btnSignIn.setBounds(105, 224, 105, 23);
		btnSignIn.setBackground(Color.WHITE);
		btnSignIn.setFont(new Font("Tahoma", Font.BOLD, 14));
		add(btnSignIn);
		
		JLabel lblNewToMybox = new JLabel("New to MyBox?");
		lblNewToMybox.setForeground(Color.WHITE);
		lblNewToMybox.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewToMybox.setBounds(105, 265, 105, 14);
		add(lblNewToMybox);
		
		btnShowCreateAnAccount = new JButton("Create an account");
		btnShowCreateAnAccount.setActionCommand(ACTION_COMMAND_SHOW_CREATE_ACCOUNT);
		btnShowCreateAnAccount.setBackground(Color.WHITE);
		btnShowCreateAnAccount.setBounds(83, 282, 149, 23);
		add(btnShowCreateAnAccount);

		
		btnIpPort = new JButton("IP & Port");
		btnIpPort.setActionCommand(ACTION_COMMAND_IP_PORT);
		btnIpPort.setHorizontalAlignment(SwingConstants.LEFT);
		btnIpPort.setBorder(BorderFactory.createEmptyBorder());
		btnIpPort.setBackground(SystemColor.textHighlight);
		btnIpPort.setBounds(0, 0, 50, 23);
		add(btnIpPort);
	}
	
	public String getUsernameText() {
		return fieldUsername.getText();
	}
	
	public String getPasswordText() {
		return new String(fieldPassword.getPassword());
	}
	
	public void registerSignInListener(ActionListener listener) {
		btnSignIn.addActionListener(listener);
	}
	
	public void registerIPAndPortListener(ActionListener listener) {
		btnIpPort.addActionListener(listener);
	}
	
	public void registerShowCreateAccountListener(ActionListener listener) {
		btnShowCreateAnAccount.addActionListener(listener);
	}
	
	@Override
	public void showMessage(String str) {
		JOptionPane.showMessageDialog(this, str, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	@Override
	public void closeWindow() {}


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
		btnShowCreateAnAccount.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

}
