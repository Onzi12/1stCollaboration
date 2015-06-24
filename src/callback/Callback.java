package callback;

import java.util.Observable;
import java.util.Observer;

import model.Group;

import common.Message;
import common.MessageType;
import common.MyBoxException;

/**
 * The {@link Callback} class is used to execute a specific routine after receiving a specific message from the server.
 * Needs to be added as an observer to the client before sending the message.
 * @param <T> - the type of object to be received from the server.
 */
public abstract class Callback<T> implements Observer {
		
	/**
	 * Called when a message is received from the server.
	 * @param obj
	 * @param exception
	 */
	protected abstract void messageReceived(T obj, MyBoxException exception);
	
	/**
	 * Set the expected message type.
	 * @return
	 */
	protected abstract MessageType getMessageType();

	@SuppressWarnings("unchecked")
	@Override
	public void update(Observable arg0, Object arg1) {
	
		// check if the received object is of type Message.
		if (arg1 instanceof Message) {
			
			Message msg = (Message)arg1; // The message received from the server.
			MessageType type = msg.getType(); // The type of the message.
			Object obj = msg.getData(); // The contents of the message.
			
			MyBoxException exception = null;
			T retObj = (T)obj;
			 // if the type if ERROR_MESSAGE create an exception.
			if (type == MessageType.ERROR_MESSAGE) {
				exception = new MyBoxException((String)obj);
				retObj = null;
			} 
			
			// check if the message type of the message received from the server is the expected message type.
			if (type == getMessageType() || type == MessageType.ERROR_MESSAGE) {
				arg0.deleteObserver(this); // Stop observing.
				messageReceived(retObj, exception);
			}
			
		}
			
	}

	protected void messageReceived(Group group, MyBoxException exception) {
		// TODO Auto-generated method stub
		
	}
}
