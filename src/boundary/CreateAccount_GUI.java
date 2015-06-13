package boundary;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import common.Controller;
import common.JPanelBoundary;
import controller.CreateAccountController;
import custom_gui.TextPrompt;

public class CreateAccount_GUI extends JPanelBoundary{



	private static final long serialVersionUID = -8879377620723123663L;
	private JButton btnCreateAccount;
	private JButton btnShowSignIn;
	private JTextField fieldFirstName;
	private JTextField fieldLastName;
	private JTextField fieldNewUsername;
	private JPasswordField fieldNewPassword;
	private JTextField fieldEmail;

	public CreateAccount_GUI(Controller controller) {
		super(controller);
	}	
	
	@Override
	public void draw() {
		setBackground(SystemColor.textHighlight);
		setLayout(null);
		
		JLabel lblJoinMybox = new JLabel("Join MyBox");
		lblJoinMybox.setHorizontalAlignment(SwingConstants.CENTER);
		lblJoinMybox.setForeground(Color.WHITE);
		lblJoinMybox.setBounds(61, 36, 193, 25);
		lblJoinMybox.setFont(new Font("Arial", Font.BOLD, 20));
		add(lblJoinMybox);
		
		fieldFirstName = new JTextField();
		TextPrompt placeHolderFirstName = new TextPrompt("First Name", fieldFirstName);
		placeHolderFirstName.setFont(new Font("Tahoma", Font.ITALIC, 11));
		placeHolderFirstName.setForeground(Color.GRAY);
		placeHolderFirstName.changeAlpha(0.5f);
		placeHolderFirstName.changeStyle(Font.BOLD + Font.ITALIC);
		fieldFirstName.setBounds(61, 97, 193, 20);
		add(fieldFirstName);
		fieldFirstName.setColumns(10);
		
		fieldLastName = new JTextField();
		TextPrompt placeHolderLastName = new TextPrompt("Last Name", fieldLastName);
		placeHolderLastName.setFont(new Font("Tahoma", Font.ITALIC, 11));
		placeHolderLastName.setForeground(Color.GRAY);
		placeHolderLastName.changeAlpha(0.5f);
		placeHolderLastName.changeStyle(Font.BOLD + Font.ITALIC);
		fieldLastName.setColumns(10);
		fieldLastName.setBounds(61, 128, 193, 20);
		add(fieldLastName);
		
		fieldNewUsername = new JTextField();
		TextPrompt placeHolderNewUsername = new TextPrompt("Username", fieldNewUsername);
		placeHolderNewUsername.setFont(new Font("Tahoma", Font.ITALIC, 11));
		placeHolderNewUsername.setForeground(Color.GRAY);
		placeHolderNewUsername.changeAlpha(0.5f);
		placeHolderNewUsername.changeStyle(Font.BOLD + Font.ITALIC);
		fieldNewUsername.setColumns(10);
		fieldNewUsername.setBounds(61, 159, 193, 20);
		add(fieldNewUsername);
		
		fieldNewPassword = new JPasswordField();
		TextPrompt placeHolderNewPassword = new TextPrompt("Password", fieldNewPassword);
		placeHolderNewPassword.setFont(new Font("Tahoma", Font.ITALIC, 11));
		placeHolderNewPassword.setForeground(Color.GRAY);
		placeHolderNewPassword.changeAlpha(0.5f);
		placeHolderNewPassword.changeStyle(Font.BOLD + Font.ITALIC);
		fieldNewPassword.setColumns(10);
		fieldNewPassword.setBounds(61, 190, 193, 20);
		add(fieldNewPassword);
		
		fieldEmail = new JTextField();
		TextPrompt placeHolderEmail = new TextPrompt("Email", fieldEmail);
		placeHolderEmail.setFont(new Font("Tahoma", Font.ITALIC, 11));
		placeHolderEmail.setForeground(Color.GRAY);
		placeHolderEmail.changeAlpha(0.5f);
		placeHolderEmail.changeStyle(Font.BOLD + Font.ITALIC);
		fieldEmail.setColumns(10);
		fieldEmail.setBounds(61, 221, 193, 20);
		add(fieldEmail);
		
		btnCreateAccount = new JButton("Create Account");
		btnCreateAccount.setBounds(82, 262, 152, 23);
		btnCreateAccount.setBackground(Color.WHITE);
		btnCreateAccount.setFont(new Font("Tahoma", Font.BOLD, 14));
		add(btnCreateAccount);
		
		JLabel lblAlreadyInMybox = new JLabel("Already in MyBox?");
		lblAlreadyInMybox.setForeground(Color.WHITE);
		lblAlreadyInMybox.setHorizontalAlignment(SwingConstants.CENTER);
		lblAlreadyInMybox.setBounds(105, 305, 105, 14);
		add(lblAlreadyInMybox);
		
		btnShowSignIn = new JButton("Sign In");
		btnShowSignIn.setBackground(Color.WHITE);
		btnShowSignIn.setBounds(105, 322, 100, 23);
		add(btnShowSignIn);
	}
	
	public String getUsernameText() {
		return fieldNewUsername.getText();
	}
	
	public String getPasswordText() {
		return new String(fieldNewPassword.getPassword());
	}
	
	public String getFirstNameText() {
		return fieldFirstName.getText();
	}
	
	public String getLastNameText() {
		return fieldLastName.getText();
	}
	
	public String getEmailText() {
		return fieldEmail.getText();
	}




	@Override
	public void registerListeners() {
		final CreateAccountController control = (CreateAccountController)controller;
		
		btnCreateAccount.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				control.btnCreateAccountClicked();	
			}
		});
		
		
		btnShowSignIn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				control.btnShowSignInClicked();	
			}
		});
		
	}

	
	
}
