package callback;

import common.MessageType;
import common.MyBoxException;

public abstract class GroupRequestAnswerCallback extends Callback<Void> {

	/**
	 * Called when the operation completes.
	 * @param exception
	 */
	protected abstract void done(MyBoxException exception);
	
	@Override
	protected void messageReceived(Void obj, MyBoxException exception) {
		done(exception);
	}

	@Override
	protected MessageType getMessageType() {
		return MessageType.REQUEST_ANSWER_ACCEPT;
	}

}
