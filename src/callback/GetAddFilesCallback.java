package callback;

import java.util.HashSet;

import model.ItemFile;

import common.MessageType;
import common.MyBoxException;

public abstract class GetAddFilesCallback extends Callback<HashSet<ItemFile>> {

	/**
	 * Called when the operation completes.
	 * @param items
	 * @param exception
	 */
	protected abstract void done(HashSet<ItemFile> items,MyBoxException exception);
	
	@Override
	protected void messageReceived(HashSet<ItemFile> items,MyBoxException exception) {
		done(items,exception);
	}

	@Override
	protected MessageType getMessageType() {
		return MessageType.GET_ADD_FILES;
	}

}
