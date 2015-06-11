package common;

import java.io.Serializable;

/**
 * This class is used to contain messages transfered between the server and a client
 */

public class Message implements Serializable {

	private static final long serialVersionUID = -7395426558268419571L;
	
	/** The data to send to the server */
	private Object data;
	
	/** The type of the message that is sent to the server  */
	private MessageType type;
	
	/**
	 * Constructs the message.
	 * @param data - the actual message.
	 * @param type - the type of the message.
	 */
	public Message(Object data, MessageType type) {
		this.data = data;
		this.type = type;
	}
	
	/**
	 * Returns the message type
	 * @return MessageType
	 */
	public MessageType getType() {
		return type;
	}
	
	/**
	 * Return the contents of the message 
	 * @return Object 
	 */
	public Object getData() {
		return data;
	}
	
}
