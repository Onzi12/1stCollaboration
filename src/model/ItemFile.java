package model;



public class ItemFile extends Item {

	private static final long serialVersionUID = 243683429589199787L;
	
	private byte[] file;
	private String fileDesc;
	
	public ItemFile(int id) {
		super(id);
	}
	
	public ItemFile() {}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}
	
	public void setFileDesc(String fileDesc){
		this.fileDesc = fileDesc;
	}
	
	public String getFileDesc(){
			return fileDesc;
	}
}