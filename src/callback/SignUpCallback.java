package callback;

import common.MessageType;
import common.MyBoxException;

import model.User;

public abstract class SignUpCallback extends Callback<User> {

	protected abstract void userSignedIn(User user, MyBoxException exception);
	
	@Override
	protected void messageReceived(User user, MyBoxException exception) {
		userSignedIn(user, exception);
	}

	@Override
	protected MessageType getMessageType() {
		return MessageType.CREATE_ACCOUNT;
	}

}
