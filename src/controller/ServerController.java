package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import model.Item;
import model.ItemFile;
import model.User;
import ocsf.server.ConnectionToClient;
import ocsf.server.OriginatorMessage;
import server.Server;
import boundary.Server_GUI;
import common.Message;
import common.MessageType;
import dao.FileDAO;
import dao.UserDAO;

public class ServerController implements Observer {
	
	private Server_GUI gui;
	private Server server;
	
	public ServerController(Server_GUI gui) {
		this.gui = gui;
		
		gui.addWindowListener(new ServerWindowListener());
		gui.registerStartListener(new BtnStartActionListener());
	}
	
	private class ServerWindowListener extends WindowAdapter {

		@Override
		public void windowClosing(WindowEvent e) {
			if (server != null) {
				try {
					server.close();
				} catch (IOException ex) {}
			}
		}
		
	}
	
	private class BtnStartActionListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			btnStartClicked();

		}
	}

	@Override
	public void update(Observable o, Object arg) {
		handleNotificationFromServer(arg);
	}

	private void handleNotificationFromServer(Object arg) {
		if (arg instanceof String) {
			
			String str = (String)arg;
			switch (str) {
			case Server.SERVER_STARTED:
				gui.showMessage("Server listening for connections on port " + server.getPort() + ".");
				break;
				
			case Server.SERVER_STOPPED:
				gui.showMessage("Server has stopped listening for connections.");
				break;
				
			case Server.CONNECTION_FAILED:
				try {
					server.close();
				} catch (IOException e) {}
				gui.showMessage("Connection to DB failed.");
				break;
				
			case Server.CONNECTION_SUCCEEDED:
				gui.disableUI();
				gui.displayHostIP("Server IP address: " + server.getIPAddress());
				gui.showMessage("Server is connected successfully to DB.");
				break;
			}
			
		}
		else if (arg instanceof OriginatorMessage) {
			
			OriginatorMessage originatorMessage = (OriginatorMessage)arg;
			ConnectionToClient client = originatorMessage.getOriginator();
			if (originatorMessage.getMessage() instanceof String) {
				
				String str = (String)originatorMessage.getMessage();
				switch (str) {
				case Server.CLIENT_CONNECTED:
					gui.showMessage(client.getInetAddress().getHostName() + " established connection to the server.");
					client.setInfo("hostname", client.getInetAddress().getHostName());
					break;

				case Server.CLIENT_DISCONNECTED:
					gui.showMessage("Connection to " + client.getInfo("hostname") + " closed.");
					break;
				}
				
			} else if (originatorMessage.getMessage() instanceof Message) {
				
				Message msg = (Message)originatorMessage.getMessage();
				MessageType type = (MessageType)msg.getType();
				
				System.out.println(type);
				
				switch (type) {
				case CREATE_ACCOUNT: 
				{
					User user = (User)msg.getData();
					try {
						UserDAO userDAO = new UserDAO(server.getConnection());
						user.setId(userDAO.signUp(user));
						Message response = new Message(user, MessageType.LOGIN); 
						client.sendToClient(response);
						gui.showMessage(user.getUsername() + " signed up to MyBox.");
						gui.showMessage(user.getUsername() + " logged in successfully. (authentication succeeded)");
					} catch (SQLException e) {
						String message = "The username " + user.getUsername() + " already exists.";
						gui.showMessage(message);
						try {
							Message response = new Message(message, MessageType.ERROR_MESSAGE); 
							client.sendToClient(response);
						} catch (IOException e1) {
							gui.showMessage("Failed to send response to client: " + e.getMessage());
						}
					} catch (IOException e) {
						gui.showMessage("Failed to send response to client: " + e.getMessage());
					}
				}
					break;
					
				case LOGOUT:
					User user2 = (User)msg.getData();
					user2.setStatus(0);
					gui.showMessage(client.getInfo("username") + " logged out.");
					break;
					
				case LOGIN:
					try {
						User user = (User)msg.getData();
						UserDAO userDAO = new UserDAO(server.getConnection()); 
						Boolean isConnected = userDAO.authenticate(user);
						client.setInfo("username", user.getUsername());
						if (isConnected) {
							user.setCounter(0);
							user.setStatus(1);
							Message response = new Message(user, MessageType.LOGIN); 
							client.sendToClient(response);
							gui.showMessage(user.getUsername() + " logged in successfully. (authentication succeeded)");
						} else {
							Message response;
							if(user.getCounter() == 2)
							{
								user.setStatus(2);
								response = new Message("Password is incorrect.the user " + user.getUsername() +" is blocked", MessageType.ERROR_MESSAGE);
							}
							else{ 
								user.setCounter(user.getCounter()+1);
								response = new Message("Password is incorrect.", MessageType.ERROR_MESSAGE); 
							}
							client.sendToClient(response);
							gui.showMessage(user.getUsername() + " authentication failed.");
						}
					} catch (IOException e) {
						gui.showMessage("Failed to send response to client: " + e.getMessage());
					} catch (SQLException e) {
						gui.showMessage(e.getMessage());
						try {
							Message response = new Message(e.getMessage(), MessageType.ERROR_MESSAGE); 
							client.sendToClient(response);
						} catch (IOException e1) {
							gui.showMessage("Failed to send response to client: " + e.getMessage());
						}
					}
					break;
					
				case UPDATE_FILE:
					try {
						FileDAO fileDAO = new FileDAO(server.getConnection());
						ItemFile file = (ItemFile)msg.getData();
						fileDAO.updateFile(file);
						Message response = new Message(file, MessageType.UPDATE_FILE);
						client.sendToClient(response);
						gui.showMessage("Successfully updated file in the DB.");
					} catch (SQLException e) {
						gui.showMessage("Failed to update file in DB.");
					} catch (IOException e) {
						gui.showMessage("Failed to send response to client.");
					}
					break;

				case GET_FILES:
					try {
						User user = (User)msg.getData();
						System.out.println(user.getID());
						FileDAO fileDAO = new FileDAO(server.getConnection());
						HashMap<String, Item> res = fileDAO.getAllFiles(user);
						Message response = new Message(res, MessageType.GET_FILES);
						client.sendToClient(response);
					} catch (SQLException e) {
						System.out.println(e.getMessage());
						gui.showMessage("Failed to retrieve data from DB.");
					} catch (IOException e) {
						gui.showMessage("Failed to send response to client.");
					}
					break;
					
				case ADD_FILE:
					try {
						FileDAO fileDAO = new FileDAO(server.getConnection());
						ItemFile file = (ItemFile)msg.getData();
						int id = fileDAO.addFile(file);
						file.setID(id);
						Message response = new Message(file, MessageType.ADD_FILE);
						client.sendToClient(response);
						gui.showMessage("Successfully add file to the DB.");
					} catch (SQLException e) {
						gui.showMessage("Failed to add file to DB: " + e.getMessage());
					} catch (IOException e) {
						gui.showMessage("Failed to send response to client.");
					}
					break;	
					
				case DELETE_FILE:
					try {
						FileDAO fileDAO = new FileDAO(server.getConnection());
						ItemFile file = (ItemFile)msg.getData();
						fileDAO.deleteFile(file);
						Message response = new Message(null, MessageType.DELETE_FILE);
						client.sendToClient(response);
						gui.showMessage("Successfully deleted file from the DB.");
					} catch (SQLException e) {
						gui.showMessage("Failed to delete file from DB: " + e.getMessage());
					} catch (IOException e) {
						gui.showMessage("Failed to send response to client.");
					}
					break;

				default:
					break;
				}
				
			}

		}
	}
	
	
	private void btnStartClicked() {
		int port;
		String url;
		try {
			port = Integer.parseInt(gui.getPort());
		} catch (Exception ex) {
			port = Server_GUI.DEFAULT_PORT;
		}
		
		url = gui.getURL().trim();
		if (url.equals("")) {
			url = Server_GUI.DEFAULT_URL;
		}
		
		String user = gui.getUser().trim();
		String password = gui.getPassword().trim();
		try {
			server = new Server(port, url, user, password);
		} catch (IOException e1) {
			try {
				server.close();
			} catch (IOException e) {}
			server = null;
			gui.showMessage("ERROR - Could not listen for clients!");
		}
		server.addObserver(ServerController.this);
	}
	
}
