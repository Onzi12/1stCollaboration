package callback;

import java.util.HashSet;

import common.MessageType;
import common.MyBoxException;

import model.ItemFile;

public abstract class GetDeleteFilePhyisicalCallback extends Callback<HashSet<ItemFile>>{

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
