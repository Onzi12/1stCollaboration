package callback;

import model.ItemFile;

import common.MessageType;
import common.MyBoxException;

public abstract class CanEditFileCallback extends Callback<ItemFile> {

	/**
	 * Called when the operation completes.
	 * @param file
	 * @param exception
	 */
	public abstract void canEditFile(ItemFile file, MyBoxException exception);
	
	@Override
	protected void messageReceived(ItemFile file, MyBoxException exception) {
		canEditFile(file, exception);
	}

	@Override
	protected MessageType getMessageType() {
		return MessageType.CAN_EDIT_FILE;
	}

}
