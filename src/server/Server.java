package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import ocsf.server.ConnectionToClient;
import ocsf.server.ObservableServer;
import ocsf.server.OriginatorMessage;

/**
* This class overrides some of the methods in the ObservableServer 
* superclass in order to give more functionality to the server.
*/

public class Server extends ObservableServer {

	private String url = "jdbc:mysql://localhost/test";
	private String user = "root";
	private String password = "Braude";
	public static final String CONNECTION_FAILED= "#OS:Connection failed.";
	public static final String CONNECTION_SUCCEEDED= "#OS:Connection succeeded.";
	
	/** The connection to the mysql data base */
	private Connection connection;

	/**
	 * Constructs an instance of the server.
	 * @param port - The port number to connect on.
	 * @throws IOException
	 */
	public Server(int port, String url, String user, String password) throws IOException {
		super(port);
		this.url = url;
		this.user = user;
		this.password = password;
		listen();
	}
	
	/**
	 * Get the connection (to the DB) instance
	 * @return Connection
	 */
	public Connection getConnection() {
		return connection;
	}
	
	@Override
	protected synchronized void serverStarted() {
		try {
    	    setChanged();
    	    notifyObservers(SERVER_STARTED);
            Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(url, user, password);
    	    setChanged();
    	    notifyObservers(CONNECTION_SUCCEEDED);
        } catch (Exception e) {
    	    setChanged();
    	    notifyObservers(CONNECTION_FAILED);
        }
	}
	
	@Override
	protected void serverStopped() {
		super.serverStopped();
		try {
			connection.close();
		} catch (SQLException e) {}
	}

	@Override
	protected synchronized void handleMessageFromClient(Object message, ConnectionToClient client) {
		OriginatorMessage msg = new OriginatorMessage(client, message);
		setChanged();
	    notifyObservers(msg);
	}
	
	@Override
	protected synchronized void clientConnected(ConnectionToClient client) {
		OriginatorMessage msg = new OriginatorMessage(client, CLIENT_CONNECTED);
	    setChanged();
	    notifyObservers(msg);
	}
	
	@Override
	protected synchronized void clientDisconnected(ConnectionToClient client) {
		OriginatorMessage msg = new OriginatorMessage(client, CLIENT_DISCONNECTED);
	    setChanged();
	    notifyObservers(msg);
	}
	
	public String getIPAddress() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			return null;
		}
	}
}
