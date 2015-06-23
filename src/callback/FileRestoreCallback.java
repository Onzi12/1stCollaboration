package callback;

import common.MessageType;
import common.MyBoxException;

public abstract class FileRestoreCallback extends Callback<Void> {

	protected abstract void done(MyBoxException exception);

	@Override
	protected void messageReceived(Void v, MyBoxException exception) {
		done(exception);
	}

	@Override
	protected MessageType getMessageType() {
		return MessageType.RESTORE_FILE;
	}

}
