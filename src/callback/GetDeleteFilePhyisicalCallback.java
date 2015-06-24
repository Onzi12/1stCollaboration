package callback;

import java.util.HashSet;

import model.ItemFile;

import common.MessageType;
import common.MyBoxException;

public abstract class GetDeleteFilePhyisicalCallback extends Callback<HashSet<ItemFile>>{

	/**
	 * Called when the operation completes.
	 * @param files
	 * @param exception
	 */
	protected abstract void done(HashSet<ItemFile> files,MyBoxException exception);
	
	@Override
	protected void messageReceived(HashSet<ItemFile> files,MyBoxException exception) {
		done(files,exception);
		
	}

	@Override
	protected MessageType getMessageType() {
		return MessageType.GET_DELETE_FILE_PHYSICAL;
	}

}
