package callback;

import model.ItemFolder;

import common.MessageType;
import common.MyBoxException;

public abstract class RemoveFolderCallback extends Callback<ItemFolder> {

	protected abstract void done(ItemFolder folder, MyBoxException exception);
	@Override
	protected void messageReceived(ItemFolder folder, MyBoxException exception) {
		done(folder,exception);
		
	}

	@Override
	protected MessageType getMessageType() {
		return MessageType.REMOVE_FOLDER;
	}

}
