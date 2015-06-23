package callback;

import java.util.HashSet;

import common.MessageType;
import common.MyBoxException;
import model.Group;

public abstract class GetAllGroupsCallback extends Callback<HashSet<Group>> {
	
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
