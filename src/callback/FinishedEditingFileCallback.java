package callback;

import common.MessageType;
import common.MyBoxException;

public abstract class FinishedEditingFileCallback extends Callback<Void> {

	public abstract void finishedEditingFile(MyBoxException exception);
	
	@Override
	protected void messageReceived(Void obj, MyBoxException exception) {
		finishedEditingFile(exception);
	}

	@Override
	protected MessageType getMessageType() {
		return MessageType.FINISHED_EDITING_FILE;
	}

}
