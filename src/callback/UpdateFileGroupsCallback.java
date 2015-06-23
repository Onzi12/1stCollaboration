package callback;

import java.util.HashMap;

import common.MessageType;
import common.MyBoxException;
import model.Group;

public abstract class UpdateFileGroupsCallback extends Callback<HashMap<Group,Integer>> {

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
