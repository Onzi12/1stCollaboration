package model;

import java.util.HashMap;

public class ItemFolder extends Item {

	private static final long serialVersionUID = -1040876964303827361L;
	private HashMap<String, Item> files = new HashMap<String, Item>();
	
	public ItemFolder(int id) {
		super(id);
	}
	
	public HashMap<String, Item> getFiles() {
		return files;
	}


	public void addFile(Item file) {
		files.put(file.getStringID(), file);
	}
	
	
}
