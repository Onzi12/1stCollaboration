package callback;

import common.MessageType;
import common.MyBoxException;

public abstract class SendRequestsCallback extends Callback<String> {

	@Override
	protected abstract void messageReceived(String message, MyBoxException exception);

	@Override
	protected MessageType getMessageType() {
		return MessageType.SEND_NEW_GROUP_REQUESTS;
	}

}
