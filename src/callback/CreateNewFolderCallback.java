package callback;

import model.ItemFolder;

import common.MessageType;
import common.MyBoxException;

public abstract class CreateNewFolderCallback extends Callback<ItemFolder> {

	protected abstract void done(ItemFolder folder, MyBoxException exception);
	
	@Override
	protected void messageReceived(ItemFolder folder, MyBoxException exception) {
		done(folder, exception);
	}

	@Override
	protected MessageType getMessageType() {
		return MessageType.CREATE_NEW_FOLDER;
	}

}
