package callback;

import java.util.HashMap;

import common.MessageType;
import common.MyBoxException;
import model.Group;

public abstract class GetGroupsCallback extends Callback<HashMap<String, Group>>{
	
	protected abstract void done(HashMap<String, Group> group, MyBoxException exception);

	@Override
	protected void messageReceived(HashMap<String, Group> group, MyBoxException exception) {
		done(group,exception);
		
	}

	@Override
	protected MessageType getMessageType() {
		return MessageType.GET_ALL_USER_IN_GROUP;
	}

}
