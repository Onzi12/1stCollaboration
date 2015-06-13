package boundary;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import common.Controller;
import controller.FileUpdateController;

public class FileUpdate_GUI extends FileRead_GUI {

	private static final long serialVersionUID = -2841064597688335514L;
	private JButton btnSave;
	private JButton btnCancel;

	public FileUpdate_GUI(Controller controller) {
		super(controller);
	}
	
	
	@Override
	public boolean isEditable() {
		return true;
	}
	
	@Override
	public void addButtons(JPanel buttonPanel) {
		btnSave = new JButton("Save");
		buttonPanel.add(btnSave);
		
		btnCancel = new JButton("Cancel");
		buttonPanel.add(btnCancel);
	}

	
	@Override
	public void registerListeners() {
		
		final FileUpdateController control = (FileUpdateController)controller;
		
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				control.btnCancelClicked();	
			}
		});
		
		btnSave.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				control.btnSaveClicked();
				
			}
		});
	}
}
