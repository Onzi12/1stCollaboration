package callback;

import java.util.HashSet;

import model.Group;

import common.MessageType;
import common.MyBoxException;

public abstract class GetAllGroupsCallback extends Callback<HashSet<Group>> {
	
	/**
	 * Called when the operation completes.
	 * @param groups
	 * @param exception
	 */
	protected abstract void done(HashSet<Group> groups, MyBoxException exception);
	
	@Override
	protected void messageReceived(HashSet<Group> groups, MyBoxException exception) {
		done(groups,exception);
		
	}

	@Override
	protected MessageType getMessageType() {
		return MessageType.GET_ALL_GROUPS;
	}

}
