package callback;

import java.util.HashMap;

import common.MessageType;
import common.MyBoxException;

public abstract class GetGroupAccessCallback extends Callback<HashMap<Integer,Integer>>{

	/**
	 * Called when the operation completes.
	 * @param groupsAccess
	 * @param exception
	 */
	protected abstract void done(HashMap<Integer,Integer> groupsAccess , MyBoxException exception);
	
	@Override
	protected void messageReceived(HashMap<Integer,Integer> groupsAccess, MyBoxException exception) {
		done(groupsAccess,exception);
	}

	@Override
	protected MessageType getMessageType() {
		return MessageType.GET_FILE_GROUPS_ACCESS;
	}

}
