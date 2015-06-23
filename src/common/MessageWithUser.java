package common;

import model.User;
import client.Client;


public class MessageWithUser extends Message {
	private User user;
	public MessageWithUser(Object obj,MessageType type){
		super(obj,type);
		user = Client.getInstance().getUser();
	}
	
	public User getUser() {
		return user;
	}
}