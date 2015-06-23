package boundary;



import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import model.ItemFile;

class FileTableModel extends AbstractTableModel {
	
	protected static final long serialVersionUID = 1L;
	public static final int OBJECT_COL = -1;
	protected static final int FILENAME_COL = 0;
	protected static final int PRIVILEGE_COL = 1;
	protected static final int STATE_COL = 2;
	protected static final int OWNER_COL = 3;

	protected String[] columnNames = {"Filename", "Privilege", "State"};
	protected ArrayList<ItemFile> files;

	public FileTableModel(ArrayList<ItemFile> files) {
		this.files = files;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return files.size();
	}

	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override
	public Object getValueAt(int row, int col) {

		ItemFile file = files.get(row);

		switch (col) {
		case FILENAME_COL:
			return file.getName();
		case PRIVILEGE_COL:
			return file.getPrivilege();
		case STATE_COL:
			return file.getState();
		case OBJECT_COL:
			return file;
		}
		return null;
	}

	@Override
	public Class<?> getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}
}
