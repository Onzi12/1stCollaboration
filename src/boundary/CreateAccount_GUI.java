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
	private JTextField fieldNewUsername;
	private JPasswordField fieldNewPassword;

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
		lblJoinMybox.setFont(new Font("Tahoma", Font.BOLD, 20));
		add(lblJoinMybox);
		
		fieldNewUsername = new JTextField();
		TextPrompt placeHolderNewUsername = new TextPrompt("Username", fieldNewUsername);
		placeHolderNewUsername.setForeground(Color.GRAY);
		placeHolderNewUsername.changeAlpha(0.5f);
		placeHolderNewUsername.changeStyle(Font.BOLD + Font.ITALIC);
		fieldNewUsername.setColumns(10);
		fieldNewUsername.setBounds(61, 159, 193, 20);
		add(fieldNewUsername);
		
		fieldNewPassword = new JPasswordField();
		TextPrompt placeHolderNewPassword = new TextPrompt("Password", fieldNewPassword);
		placeHolderNewPassword.setForeground(Color.GRAY);
		placeHolderNewPassword.changeAlpha(0.5f);
		placeHolderNewPassword.changeStyle(Font.BOLD + Font.ITALIC);
		fieldNewPassword.setColumns(10);
		fieldNewPassword.setBounds(61, 190, 193, 20);
		add(fieldNewPassword);
		
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
