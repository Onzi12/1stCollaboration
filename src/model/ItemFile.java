package model;

import java.io.File;


public class ItemFile extends Item {

	private static final long serialVersionUID = 243683429589199787L;
	
	private File file;
	
	public ItemFile(int id) {
		super(id);
	}
	
	public ItemFile() {}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

}
