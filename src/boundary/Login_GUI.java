package boundary;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import common.Controller;
import common.JPanelBoundary;

import controller.LoginController;
import custom_gui.TextPrompt;

/**
 * Graphical User Interface that Allows a user to log in the system and start using it
 * @author Idan
 *
 */
@SuppressWarnings("serial")
public class Login_GUI extends JPanelBoundary {
	
	/**
	 * Username insert field
	 */
	private JTextField fieldUsername;
	
	/**
	 * Password insert field
	 */
	public JPasswordField fieldPassword;
	
	/**
	 * Sign in button
	 */
	private JButton btnSignIn;
	
	/**
	 * Switch window to Create account
	 */
	private JButton btnShowCreateAnAccount;
	
	/**
	 * Displays a window to insert IP address to connect to the server
	 */
	private JButton btnIpPort;

	/**
	 * Constructs the window and assignes it a controller
	 * @param controller
	 */
	public Login_GUI(Controller controller) {
		super(controller);
		
	}
	
	@Override
	public void draw() {
		
		AppFrame.getInstance().setSize(326, 400);
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
		fieldUsername.setFocusable(true);


		fieldPassword = new JPasswordField();
		TextPrompt placeHolderPassword = new TextPrompt("Password", fieldPassword);
		placeHolderPassword.setForeground(Color.GRAY);
		placeHolderPassword.changeAlpha(0.5f);
		placeHolderPassword.changeStyle(Font.BOLD + Font.ITALIC);
		fieldPassword.setBounds(61, 168, 193, 20);
		add(fieldPassword);
		fieldPassword.setColumns(10);
		
		fieldPassword.setFocusable(true);
		fieldPassword.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER )
					((LoginController)controller).btnSignInClicked();
			}
		});

	
		
		btnSignIn = new JButton("Sign In");
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
		btnShowCreateAnAccount.setBackground(Color.WHITE);
		btnShowCreateAnAccount.setBounds(83, 282, 149, 23);
		add(btnShowCreateAnAccount);

		
		btnIpPort = new JButton("IP & Port");
		btnIpPort.setHorizontalAlignment(SwingConstants.LEFT);
		btnIpPort.setBorder(BorderFactory.createEmptyBorder());
		btnIpPort.setBackground(SystemColor.textHighlight);
		btnIpPort.setBounds(0, 0, 50, 23);
		add(btnIpPort);
		

	}
	
	/**
	 * Withdraws the entered username to sign it
	 * @return
	 */
	public String getUsernameText() {
		return fieldUsername.getText();
	}
	
	/**
	 * Withdraws the enterd password to sign in
	 * @return
	 */
	public String getPasswordText() {
		return new String(fieldPassword.getPassword());
	}

	@Override
	public void registerListeners() {
		final LoginController control = (LoginController)controller;
		btnShowCreateAnAccount.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				control.btnCreateAnAccountclicked();
			}
		});
		
		
		btnIpPort.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				control.btnSetPortAndIPClicked();	
			}
		});
		
		
		btnSignIn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				control.btnSignInClicked();
				
			}
		});
		
	}

	/**
	 * Clears the username and password text fields of any text 
	 * @param clearName
	 */
	public void clearFields(boolean clearName) {

		if(clearName) {
			fieldUsername.setText("");
			fieldUsername.grabFocus(); }
		else
			fieldPassword.grabFocus();
		
		fieldPassword.setText("");
	}


	public void setUsername(String username){
		fieldUsername.setText(username);
	}
	
	public void setPassword(String password){
		fieldPassword.setText(password);
	}

}
