package custom_gui;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import model.Request;

public class RequestTableModel extends AbstractTableModel {
	
	protected static final long serialVersionUID = 1L;
	public static final int OBJECT_COL = -1;
	protected static final int USER_COL = 0;
	protected static final int GROUP_COL = 1;
	protected static final int JOIN_LEAVE_COL = 2;

	protected String[] columnNames = {"User", "Group", "Request"};
	protected ArrayList<Request> requests;

	public RequestTableModel(ArrayList<Request> requests) {
		this.requests = requests;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return requests.size();
	}

	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override
	public Object getValueAt(int row, int col) {

		Request request = requests.get(row);

		switch (col) {
		case USER_COL:
			return request.getUser().getUserName();
		case GROUP_COL:
			return request.getGroup().getName();
		case JOIN_LEAVE_COL:
			return request.getType();
		case OBJECT_COL:
			return request;
		}
		return null;
	}

	@Override
	public Class<?> getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}
}
