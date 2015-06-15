package boundary;

import common.Controller;
import common.JDialogBoundary;

import javax.swing.JPanel;

import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JSeparator;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;

import controller.FileRestoreController;
import java.awt.Rectangle;

public class FileRestore_GUI extends JDialogBoundary {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable tableFiles;
	private JButton btnClose;
	private JButton btnRestore;

	public FileRestore_GUI(Controller controller) {
		super(controller);
		setBounds(new Rectangle(150, 200, 500, 400));
	}

	@Override
	public void draw() {
		
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 484, 293);
		getContentPane().add(panel);
		panel.setLayout(null);
				
		JLabel lblRestoreFile = new JLabel("Restore File");
		lblRestoreFile.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 30));
		lblRestoreFile.setBounds(10, 11, 425, 42);
		panel.add(lblRestoreFile);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.BLUE);
		separator.setBounds(10, 50, 425, 11);
		panel.add(separator);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 61, 464, 232);
		panel.add(scrollPane);
		
		tableFiles = new JTable();
		tableFiles.setCellSelectionEnabled(true);
		scrollPane.setViewportView(tableFiles);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(10, 293, 474, 57);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		btnClose = new JButton("Close");
		btnClose.setBounds(375, 11, 89, 23);
		panel_1.add(btnClose);
		
		btnRestore = new JButton("Restore");
		btnRestore.setBounds(286, 11, 79, 23);
		panel_1.add(btnRestore);
		
	}

	@Override
	public void registerListeners() {
		
		final FileRestoreController control = (FileRestoreController)controller;
		
		btnRestore.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				control.btnRestoreClicked();
				
			}
		});
		
		btnClose.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				control.btnCloseClicked();
				
			}
		});
	}
}
