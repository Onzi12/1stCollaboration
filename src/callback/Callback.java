package callback;

import java.util.Observable;
import java.util.Observer;

import common.Message;
import common.MessageType;

public abstract class Callback implements Observer {
	
	public abstract void messageReceived(Object obj, MessageType type);
	
	@Override
	public void update(Observable arg0, Object arg1) {
	
		if (arg1 instanceof Message) {
			
			Message msg = (Message)arg1;
			MessageType type = msg.getType();
			Object obj = msg.getData();
			messageReceived(obj, type);
			arg0.deleteObserver(this);
			
		}
		
		
	}

}
