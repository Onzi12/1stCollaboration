package custom_gui;

import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;

import model.ItemFolder;

public abstract class TreeNodeEditorListener implements CellEditorListener {

	private ItemFolder folder;
	
	public TreeNodeEditorListener(ItemFolder folder) {
		this.folder = folder;
	}
	
	@Override
	public void editingStopped(ChangeEvent e) {
		editingDone(folder);
	}

	@Override
	public void editingCanceled(ChangeEvent e) {}
	
	protected abstract void editingDone(ItemFolder folder);

}
