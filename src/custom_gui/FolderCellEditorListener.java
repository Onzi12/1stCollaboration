package custom_gui;

import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;

import model.ItemFolder;

public abstract class FolderCellEditorListener implements CellEditorListener {

	private ItemFolder folder;
	
	public FolderCellEditorListener(ItemFolder folder) {
		this.folder = folder;
	}
	
	@Override
	public void editingCanceled(ChangeEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void editingStopped(ChangeEvent arg0) {
		doneEditing(folder);
	}

	protected abstract void doneEditing(ItemFolder folder);
	
}
