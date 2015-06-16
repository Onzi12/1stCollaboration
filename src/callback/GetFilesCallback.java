package callback;

import java.util.HashMap;

import model.Item;

import common.MessageType;
import common.MyBoxException;

public abstract class GetFilesCallback extends Callback<HashMap<String, Item>> {

	protected abstract void done(HashMap<String, Item> items, MyBoxException exception);
	
	@Override
	protected void messageReceived(HashMap<String, Item> items, MyBoxException exception) {
		done(items, exception);
	}

	@Override
	protected MessageType getMessageType() {
		return MessageType.GET_FILES;
	}

}
