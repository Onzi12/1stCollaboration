package model;



public class ItemFile extends Item {

	private static final long serialVersionUID = 243683429589199787L;
	
	private byte[] file;
	private String description;
	private Privilege privilege;
	private String type;
	private int owner;
	
	public enum Privilege {
		PRIVATE(0) ,GROUP(1) , PUBLIC(2);

	    private final int value;
	    
	    private Privilege(int value) {
	        this.value = value;
	    }

	    public int getValue() {
	        return value;
	    }
	}
	
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
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public String getDescription(){
			return description;
	}
	public void setPrivilege(Privilege privilege) {
		this.privilege = privilege;
	}
	
	public Privilege getPrivilege(){
		return privilege;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}

	
}