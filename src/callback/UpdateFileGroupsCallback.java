package callback;

import java.util.HashMap;

import model.Group;

import common.MessageType;
import common.MyBoxException;

public abstract class UpdateFileGroupsCallback extends Callback<HashMap<Group,Integer>> {

	/**
	 * Called when the operation completes.
	 * @param groupsAccess
	 * @param exception
	 */
	protected abstract void done(HashMap<Group, Integer> groupsAccess,MyBoxException exception);
	
	@Override
	protected void messageReceived(HashMap<Group, Integer> groupsAccess,MyBoxException exception) {
		done(groupsAccess,exception);
		
	}

	@Override
	protected MessageType getMessageType() {
		return MessageType.UPDATE_FILE_GROUPS_ACCESS;
	}

}
