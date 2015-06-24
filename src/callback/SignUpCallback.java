package callback;

import model.User;

import common.MessageType;
import common.MyBoxException;

public abstract class SignUpCallback extends Callback<User> {

	/**
	 * Called when the operation completes.
	 * @param user
	 * @param exception
	 */
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
