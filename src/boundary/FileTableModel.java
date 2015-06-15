package boundary;



import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import model.Item;
import model.ItemFile;

class FileTableModel extends AbstractTableModel {
	
	private static final long serialVersionUID = 1L;
	public static final int OBJECT_COL = -1;
	private static final int FILENAME_COL = 0;
	private static final int LOCATION_COL = 1;

	private String[] columnNames = {"Filename", "Location"};
	private ArrayList<ItemFile> files;

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

		Item file = files.get(row);

		switch (col) {
		case FILENAME_COL:
			return file.getName();
		case LOCATION_COL:
			return file.getFullPath();
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
