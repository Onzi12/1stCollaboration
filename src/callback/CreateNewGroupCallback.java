package callback;

import model.Group;

import common.MessageType;
import common.MyBoxException;

public abstract class CreateNewGroupCallback extends Callback<Group> {
	
	/**
	 * Called when the operation completes.
	 * @param group
	 * @param exception
	 */
	public abstract void done(Group group, MyBoxException exception);
	
	@Override
	protected void messageReceived(Group group, MyBoxException exception) {
		done(group,exception);
		
	}

	@Override
	protected MessageType getMessageType() {
		// TODO Auto-generated method stub
		return null;
	}

}
