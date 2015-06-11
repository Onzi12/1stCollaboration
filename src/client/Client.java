package client;

import java.io.IOException;

import ocsf.client.ObservableClient;
import callback.Callback;
import common.Message;
import common.MessageType;

/**
* This class is a singleton that overrides some of the methods in the ObservableClient 
* superclass in order to give more functionality to the client.
*/

public class Client extends ObservableClient {

	private static Client instance = null;

	private Client() {
		super("localhost", 2222);
	}
	
	public static Client getInstance() {
		if (instance == null) {
			instance = new Client();
		}
		return instance;
	}
	
	public void sendMessage(Message msg) throws IOException {
		sendToServer(msg);
	}
	
	public void sendMessage(Message msg, Callback callback) throws IOException {
		addObserver(callback);
		sendToServer(msg);
	}
	
	public void connect() throws IOException {
		if (isConnected() == false) {
			openConnection();
		}
	}
	
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
}
