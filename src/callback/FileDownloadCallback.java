package callback;

import model.ItemFile;

import common.MessageType;
import common.MyBoxException;

public abstract class FileDownloadCallback extends Callback<ItemFile> {

	/**
	 * Called when the operation completes.
	 * @param file
	 * @param exception
	 */
	protected abstract void done(ItemFile file, MyBoxException exception);
	
	
	@Override
	protected void messageReceived(ItemFile file, MyBoxException exception) {
		done(file,exception);
		
	}

	@Override
	protected MessageType getMessageType() {
		return MessageType.DOWNLOAD_FILE;
	}

}
