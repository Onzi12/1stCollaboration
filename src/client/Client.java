package client;

import java.io.IOException;

import model.User;
import ocsf.client.ObservableClient;
import callback.Callback;

import common.Message;
import common.MessageType;

/**
* The {@link Client} class is a singleton that overrides some of the methods in the ObservableClient 
* superclass in order to give more functionality to the client.
*/

public class Client extends ObservableClient {

	private static Client instance = null;
	
	/**
	 * The current user.
	 */
	private User user;
	
	/**
	 * Constructs the client.
	 */
	private Client() {
		super("localhost", 2222);
	}
	
	public static Client getInstance() {
		if (instance == null) {
			instance = new Client();
		}
		return instance;
	}
	
	/**
	 * Send message to the server.
	 * @param msg - The message.
	 * @param callback 
	 * @throws IOException
	 */
	public void sendMessage(Message msg, Callback<?> callback) throws IOException {
		if (callback != null)
			addObserver(callback);
		sendToServer(msg);
	}
	
	/**
	 * Connect to the server.
	 * @throws IOException
	 */
	public void connect() throws IOException {
		if (isConnected() == false) {
			openConnection();
		}
	}
	
	/**
	 * Disconnect from the server.
	 */
	public void disconnect() {
	    try {
	    	closeConnection();
		} catch(IOException e) {}
	}
	
	@Override
	protected void connectionException(Exception exception) {
	    setChanged();
	    notifyObservers(new Message(CONNECTION_EXCEPTION, MessageType.ERROR_MESSAGE));
	}

	/**
	 * Get the user.
	 * @return
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Set the user.
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
	}



}
