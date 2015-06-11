package callback;

import model.User;

import common.MessageType;

public abstract class LoginCallback extends Callback {

	public abstract void userSignIn(User user);
	
	public abstract void error(String message);
	
	@Override
	public void messageReceived(Object obj, MessageType type) {
		if (type == MessageType.LOGIN)
			userSignIn((User)obj);
		else if (type == MessageType.ERROR_MESSAGE) 
			error((String)obj);
	}
}
