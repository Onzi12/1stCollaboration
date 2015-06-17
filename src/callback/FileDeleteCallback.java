package callback;

import common.MessageType;
import common.MyBoxException;

import model.ItemFile;

public abstract class FileDeleteCallback extends Callback<ItemFile>{

	protected abstract void done(ItemFile file, MyBoxException exception);
	
	@Override
	protected void messageReceived(ItemFile file, MyBoxException exception) {
		done(file,exception);
		
	}

	@Override
	protected MessageType getMessageType() {
		return MessageType.DELETE_FILE_PHYSICAL;
	}

}
