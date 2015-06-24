package callback;

import java.util.Set;

import model.ItemFile;

import common.MessageType;
import common.MyBoxException;

public abstract class GetRestoreFilesCallback extends Callback<Set<ItemFile>> {

	/**
	 * Called when the operation completes.
	 * @param files
	 * @param exception
	 */
	protected abstract void done(Set<ItemFile> files, MyBoxException exception);

	@Override
	protected void messageReceived(Set<ItemFile> files, MyBoxException exception) {
		done(files, exception);
	}

	@Override
	protected MessageType getMessageType() {
		return MessageType.GET_RESTORE_FILES;
	}

}
