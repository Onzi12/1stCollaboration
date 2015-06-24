package callback;

import java.util.Set;

import model.Group;

import common.MessageType;
import common.MyBoxException;

public abstract class GetGroupsCallback extends Callback<Set<Group>>{
	
	/**
	 * Called when the operation completes.
	 * @param groups
	 * @param exception
	 */
	protected abstract void done(Set<Group> groups, MyBoxException exception);

	@Override
	protected void messageReceived(Set<Group> groups, MyBoxException exception) {
		done(groups,exception);
		
	}

	@Override
	protected MessageType getMessageType() {
		return MessageType.GET_USER_GROUPS;
	}

}
