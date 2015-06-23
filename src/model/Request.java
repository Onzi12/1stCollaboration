package model;

import java.io.Serializable;
import java.net.Authenticator.RequestorType;

public class Request implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -751041429490265936L;
	private User user;
	private Group group;
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
	
	public Request(User user, Group group, RequestType type) {
		setUser(user);
		setGroup(group);
		setType(type);
	}
	
	public User getUser() {
		return user;
	}
	
	public int hashCode(){
		return user.hashCode() + group.hashCode();
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public Group getGroup() {
		return group;
	}
	
	public void setGroup(Group group) {
		this.group = group;
	}

	public RequestType getType() {
		return type;
	}

	public void setType(RequestType requestorType) {
		this.type = requestorType;
	}
}
