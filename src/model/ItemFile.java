package model;


public class ItemFile extends Item {

	private static final long serialVersionUID = 243683429589199787L;
	
	/** A referance to the ItemFolder that holds this ItemFile */
	private ItemFolder folder;
	
	public ItemFile(int id) {
		super(id);
	}
	
	public ItemFile() {}
	
	/**
	 * Get the ItemFolder that holds this ItemFile.
	 * @return ItemFolder
	 */
	public ItemFolder getFolder() {
		return folder;
	}

	/**
	 * Set the ItemFolder that holds this ItemFile.
	 * @param folder
	 */
	public void setFolder(ItemFolder folder) {
		this.folder = folder;
	}	
}
