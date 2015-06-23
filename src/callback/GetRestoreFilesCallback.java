package callback;

import java.util.Set;

import common.MessageType;
import common.MyBoxException;
import model.ItemFile;

public abstract class GetRestoreFilesCallback extends Callback<Set<ItemFile>> {

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
