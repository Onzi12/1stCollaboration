package model;

import java.io.Serializable;

/**
 * The {@link Request} class contains the details of a group join/leave request.
 */
public class Request implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -751041429490265936L;
	
	/**
	 * The {@link User} that created the {@link Request}
	 */
	private User user;
	
	/**
	 * The {@link Group} that the {@link User} wants to join/leave
	 */
	private Group group;
	
	/**
	 * The {@link RequestType}: join/leave.
	 */
	private RequestType type;
	
	public static enum RequestType {
		JOIN(0) ,LEAVE(1);

	    private final int value;
	    
	    private RequestType(int value) {
	        this.value = value;
	    }

	    public int getValue() {
	        return value;
	    }
	}
	
	/**
	 * Constructs the {@link Request}.
	 * @param user {@link User}
	 * @param group {@link Group}
	 * @param type {@link RequestType}
	 */
	public Request(User user, Group group, RequestType type) {
		setUser(user);
		setGroup(group);
		setType(type);
	}
	
	/** 
	 * Get the {@link User}.
	 * @return
	 */
	public User getUser() {
		return user;
	}
	
	public int hashCode(){
		return user.hashCode() + group.hashCode();
	}
	
	/**
	 * Set the {@link User}.
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	/**
	 * Get the {@link Group}.
	 * @return {@link Group}
	 */
	public Group getGroup() {
		return group;
	}
	
	/**
	 * Set the {@link Group}.
	 * @param group
	 */
	public void setGroup(Group group) {
		this.group = group;
	}

	/**
	 * Get the {@link RequestType}.
	 * @return
	 */
	public RequestType getType() {
		return type;
	}

	/**
	 * Set the {@link RequestType}.
	 * @param requestorType
	 */
	public void setType(RequestType requestorType) {
		this.type = requestorType;
	}
}
