package callback;

import model.User;

import common.MessageType;
import common.MyBoxException;

public abstract class LoginCallback extends Callback<User> {

	/**
	 * Called when the operation completes.
	 * @param user
	 * @param exception
	 */
	protected abstract void done(User user, MyBoxException exception);
		
	@Override
	protected void messageReceived(User user, MyBoxException exception) {
		done(user, exception);
	}
	
	@Override
	protected MessageType getMessageType() {
		return MessageType.LOGIN;
	}
}
