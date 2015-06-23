package callback;

import java.util.Set;

import model.Request;

import common.MessageType;
import common.MyBoxException;

public abstract class GetGroupRequestsCallback extends Callback<Set<Request>> {

	protected abstract void done(Set<Request> requests, MyBoxException exception);
	
	@Override
	protected void messageReceived(Set<Request> requests, MyBoxException exception) {
		done(requests, exception);
	}

	@Override
	protected MessageType getMessageType() {
		return MessageType.GET_GROUP_REQUESTS;
	}

}
