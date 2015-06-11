package boundary;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FileUpdate_GUI extends FileRead_GUI {

	private static final long serialVersionUID = -2841064597688335514L;
	private JButton btnSave;
	private JButton btnCancel;
	private boolean isCreate;

	public FileUpdate_GUI(JFrame parent, boolean isCreate) {
		super(parent);
		this.isCreate = isCreate;
	}
	
	public boolean isCreateFile() {
		return isCreate;
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
	
	public void registerSaveListener(ActionListener listener) {
		btnSave.addActionListener(listener);
	}
	
	public void registerCancelListener(ActionListener listener) {
		btnCancel.addActionListener(listener);
	}
	
}
