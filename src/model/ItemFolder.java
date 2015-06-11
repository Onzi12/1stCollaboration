package model;

import java.util.HashMap;

public class ItemFolder extends Item {

	private static final long serialVersionUID = -1040876964303827361L;
	private HashMap<String, ItemFile> files = new HashMap<String, ItemFile>();
	
	public ItemFolder(int id) {
		super(id);
	}
	
	public HashMap<String, ItemFile> getFiles() {
		return files;
	}

	public void setFiles(HashMap<String, ItemFile> files) {
		this.files = files;
	}

}
