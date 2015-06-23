package callback;

import common.MessageType;
import common.MyBoxException;

import model.ItemFolder;

public abstract class ChangeFolderNameCallback extends Callback<ItemFolder> {

	protected abstract void done(ItemFolder folder, MyBoxException exception);
	
	@Override
	protected void messageReceived(ItemFolder folder, MyBoxException exception) {
		done(folder, exception);
	}

	@Override
	protected MessageType getMessageType() {
		return MessageType.CHANGE_FOLDER_NAME;
	}

}
