package callback;

import model.ItemFile;

import common.MessageType;
import common.MyBoxException;

public abstract class UpdateFileLocationCallback extends Callback<ItemFile>{

	protected abstract void done(ItemFile file, MyBoxException exception);

	@Override
	protected void messageReceived(ItemFile file, MyBoxException exception) {
		done(file, exception);
	}

	@Override
	protected MessageType getMessageType() {
		return MessageType.UPDATE_FILE_LOCATION;
	}
	
}
