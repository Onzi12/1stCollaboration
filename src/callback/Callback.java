package callback;

import java.util.Observable;
import java.util.Observer;

import common.Message;
import common.MessageType;
import common.MyBoxException;

public abstract class Callback<T> implements Observer {
		
	protected abstract void messageReceived(T obj, MyBoxException exception);
	protected abstract MessageType getMessageType();

	@SuppressWarnings("unchecked")
	@Override
	public void update(Observable arg0, Object arg1) {
	
		if (arg1 instanceof Message) {
			
			Message msg = (Message)arg1;
			MessageType type = msg.getType();
			Object obj = msg.getData(); 
			
			MyBoxException exception = null;
			T retObj = (T)obj;
			if (type == MessageType.ERROR_MESSAGE) {
				exception = new MyBoxException((String)obj);
				retObj = null;
			} 
			
			if (type == getMessageType() || type == MessageType.ERROR_MESSAGE) {
				messageReceived(retObj, exception);
				arg0.deleteObserver(this);
			}
			
		}
			
	}
}
