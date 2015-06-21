package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

import dao.GroupDAO;
import dao.UserDAO;
import model.User;
import boundary.Server_GUI;
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
	private static HashMap<Integer,UserDAO> loggedInUsers;
	private static HashMap<Integer,GroupDAO> allGroups;
	
	/** The connection to the mysql database */
	private static Connection connection;
	public static Server_GUI gui;

	
	
	
	public static void addUser(UserDAO user) {
		if( loggedInUsers.containsKey( user.getID()) )
			gui.showMessage("Error: The User Was Already Logged In!");
		else
			loggedInUsers.put(user.getID(),user);
	}

	public static UserDAO getUserIfLoggedIn(int userID) {
			return loggedInUsers.get(userID);
	}

	public static void removeUser(User user) {
	if( loggedInUsers.containsKey(user.getID()) )
		loggedInUsers.remove(user.getID() );
	else
		gui.showMessage("ERROR removeUser: the user was not loggedIn!");
	}
	
	/**
	 * Constructs an instance of the server.
	 * @param port - The port number to connect on.
	 * @throws IOException
	 */
	public Server(int port, String url, String user, String password, Server_GUI gui) throws IOException {
		super(port);
		this.url = url;
		this.user = user;
		this.password = password;
		this.gui = gui;
		listen();
	}
	
	/**
	 * Get the connection (to the DB) instance
	 * @return Connection
	 */
	public static Connection getConnection() {
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
        } 
		catch (SQLException e) {
			gui.showMessage(e.getMessage());
		}
		catch (Exception e) {
			gui.showMessage(e.getMessage());
    	    setChanged();
    	    notifyObservers(CONNECTION_FAILED);
        }
	}
	
	@Override
	protected void serverStopped() {
		super.serverStopped();
		try {
			if(connection != null )
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

	/**
	 * @return the allGroups
	 */
	public static HashMap<Integer,GroupDAO> getAllGroups() {
		return allGroups;
	}

	/**
	 * @param allGroups the allGroups to set
	 */
	public static void setAllGroups(HashMap<Integer,GroupDAO> allGroups) {
		Server.allGroups = allGroups;
	}
}
