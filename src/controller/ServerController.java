package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;

import model.Item;
import model.ItemFile;
import model.ItemFolder;
import model.User;
import ocsf.server.ConnectionToClient;
import ocsf.server.OriginatorMessage;

import org.apache.ibatis.jdbc.ScriptRunner;

import server.Server;
import boundary.Server_GUI;
import common.ByteArray;
import common.Message;
import common.MessageType;
import dao.FileDAO;
import dao.UserDAO;

public class ServerController implements Observer {
	
	private Server_GUI gui;
	private Server server;
	
	public ServerController(final Server_GUI ui) {
		this.gui = ui;
		
		gui.addWindowListener(new ServerWindowListener());
		gui.registerStartListener(new BtnStartActionListener());
		gui.registerCloseListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnCloseClicked();
			}
		});
		gui.registerInitDBListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
						initDBClicked();
				 	}
			
		});
	}
	
	
	private void initDBClicked() {
		String confirm = "Are you sure you wish execute a full server initialization?"+
				"\nTHIS WILL REMOVE ALL EXISTING DATABASE DATA!!!";
		int rs = JOptionPane.showConfirmDialog(gui, confirm,"Init Database",JOptionPane.YES_NO_OPTION);
		if (rs != JOptionPane.YES_OPTION)
			return;
        String script = "db.sql";
        String url = gui.getURL().trim();
        String user = gui.getUser().trim();
        String pass = gui.getPassword().trim();
        try {
        	Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con;
            con = DriverManager.getConnection(url,user, pass);
            new ScriptRunner(con)
                    .runScript(new BufferedReader(new FileReader(script)));
        } catch (Exception e2) {
            gui.showMessage(e2.getMessage());
            return;
        }
        
      gui.showMessage("Database Initiated Successfully!");
	}
	
	
	
	private void btnCloseClicked() {
		try {
			server.close();
		} catch (IOException e) {
			gui.showMessage(e.getMessage());;
		}
		
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
				gui.enableUI();
				break;
				
			case Server.CONNECTION_FAILED:
//				try {
//					server.close();			//On: Cause a NullPointerException
//				} catch (IOException e) {}
				gui.showMessage("Connection to DB failed.");
				server = null;
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
				{
					try{
						User user = (User)msg.getData();
						System.out.println(user.getStatus());
						user.setStatus(0);
						System.out.println(user.getStatus());
						UserDAO userDAO = new UserDAO(server.getConnection());
						userDAO.setStatusDB(user);
					}catch (SQLException e) {
						gui.showMessage(e.getMessage());
					}
					gui.showMessage(client.getInfo("username") + " logged out.");
				}
					break;
					
				case LOGIN:
					try {
						User user = (User)msg.getData();
						UserDAO userDAO = new UserDAO(server.getConnection()); 
						Boolean isConnected = userDAO.authenticate(user);
						client.setInfo("username", user.getUsername());
						if (isConnected && user.getStatus() != 1) {
							user.setCounter(0);
							userDAO.setCounterDB(user);
							user.setStatus(1);
							userDAO.setStatusDB(user);
							Message response = new Message(user, MessageType.LOGIN); 
							client.sendToClient(response);
							gui.showMessage(user.getUsername() + " logged in successfully. (authentication succeeded)");
						} else if (isConnected && user.getStatus() == 1){
							gui.showMessage(user.getUsername() + " is already logged in.");
							Message response = new Message(user.getUsername() + " is already logged in.", MessageType.ERROR_MESSAGE); 
							client.sendToClient(response);
						} else {
								Message response;
							if(user.getCounter() == 2) {
								user.setStatus(2);
								userDAO.setStatusDB(user);
								response = new Message("Password is incorrect.the user " + user.getUsername() +" is blocked", MessageType.ERROR_MESSAGE);
							} else { 
								System.out.println("fdg");
								user.setCounter(user.getCounter()+1);
								userDAO.setCounterDB(user);
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
					
				case UPDATE_FILE_LOCATION:
					try {
						FileDAO fileDAO = new FileDAO(server.getConnection());
						ItemFile file = (ItemFile)msg.getData();
						fileDAO.updateUserFile(file);
						Message response = new Message(file, MessageType.UPDATE_FILE_LOCATION);
						client.sendToClient(response);
						gui.showMessage("Successfully updated file's location in the DB.");
					} catch (SQLException e) {
						gui.showMessage("Failed to update file's location in DB.");
						try {
							Message response = new Message("Failed to update file's location in DB.", MessageType.ERROR_MESSAGE);
							client.sendToClient(response);
						} catch (IOException e1) {
							gui.showMessage("Failed to send response to client.");
						}
					} catch (IOException e) {
						gui.showMessage("Failed to send response to client.");
					}
					break;
					
				case GET_ADD_FILES:
					try {
						User user = (User)msg.getData();
						System.out.println(user.getID());
						FileDAO fileDAO = new FileDAO(server.getConnection());
						
						HashMap<String, Item> res = fileDAO.getAllAddFiles(user);
						Message response = new Message(res, MessageType.GET_ADD_FILES);
						client.sendToClient(response);
					} catch (SQLException e) {
						System.out.println(e.getMessage());
						gui.showMessage("Failed to retrieve data from DB.");
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
						fileDAO.addFile(file);		
						Message response = new Message(file, MessageType.ADD_FILE);
						client.sendToClient(response);
						gui.showMessage("Successfully add file to the DB.");
					} catch (SQLException e) {
						gui.showMessage("Failed to add file to DB: " + e.getMessage());
					} catch (IOException e) {
						gui.showMessage("Failed to send response to client.");
					}
					break;	
					
				case DELETE_FILE_PHYSICAL:
					try {
						FileDAO fileDAO = new FileDAO(server.getConnection());
						ItemFile file = (ItemFile)msg.getData();
						if(!deleteLocalFile(file)){throw new SQLException("the delete f");}
						fileDAO.deletePhysicalFile(file);
						Message response = new Message(file, MessageType.DELETE_FILE_PHYSICAL);
						client.sendToClient(response);
						gui.showMessage("Successfully deleted file from the DB.");
					} catch (SQLException e) {
						gui.showMessage("Failed to delete file from DB: " + e.getMessage());
					} catch (IOException e) {
						gui.showMessage("Failed to send response to client.");
					}
					break;
					
				case DELETE_FILE_VIRTUAL:
					try{
						FileDAO fileDAO = new FileDAO(server.getConnection());
						ItemFile file = (ItemFile)msg.getData();
						fileDAO.deleteVirtualFile(file);
						Message response = new Message(file, MessageType.DELETE_FILE_VIRTUAL);
						client.sendToClient(response);
						gui.showMessage("Successfully deleted file from the DB.");
					} catch (SQLException e) {
						gui.showMessage("Failed to delete file from DB: " + e.getMessage());
					} catch (IOException e) {
						gui.showMessage("Failed to send response to client.");
					}
					break;
					
					
				case READ:
				/*	try{
						FileDAO fileDAO = new FileDAO(server.getConnection());
						ItemFile file = (ItemFile)msg.getData();
						fileDAO.
					}catch (SQLException e) {
							gui.showMessage("Failed to upload file to DB: " + e.getMessage());
							try {
								Message response = new Message("Failed to upload file to DB: " + e.getMessage(), MessageType.UPLOAD_FILE);
								client.sendToClient(response);
							} catch (IOException e1) {
								gui.showMessage("Failed to send response to client.");
							}
					}catch(IOException e) {
						gui.showMessage("Failed to send response to client.");
					}
					break;
					*/
					break;
				case UPLOAD_FILE:

			        try {
						ItemFile file = (ItemFile)msg.getData();
				        byte[] bFile = file.getFile();
				        
						String myBoxPath = System.getProperty("user.home") + "\\Desktop\\MyBox\\";

						ByteArray.writeByteArrayToFile(bFile, myBoxPath + file.getName() + file.getType());
				 
						
						FileDAO fileDAO = new FileDAO(server.getConnection());
						fileDAO.uploadFile(file);
												
						Message response = new Message(file, MessageType.UPLOAD_FILE);
						client.sendToClient(response);
						
					    System.out.println("Successfully uploaded file to the DB.");
					    
			        } catch (SQLException e) {
						gui.showMessage("Failed to upload file to DB: " + e.getMessage());
						try {
							Message response = new Message("Failed to upload file to DB: " + e.getMessage(), MessageType.UPLOAD_FILE);
							client.sendToClient(response);
						} catch (IOException e1) {
							gui.showMessage("Failed to send response to client.");
						}
					} catch (IOException e) {
						gui.showMessage("Failed to send response to client.");
					} catch (Exception e) {}
					break;
					
				case FILE_EDIT:
					
			        try {
				   
						FileDAO fileDAO = new FileDAO(server.getConnection());
						ItemFile file = (ItemFile)msg.getData();
						
						fileDAO.setFileDB(file);
						
						Message response = new Message(file, MessageType.FILE_EDIT);
						client.sendToClient(response);
						
						gui.showMessage("Successfully edited file in the DB.");
					} catch (SQLException e) {
						gui.showMessage("Failed to edit file in the DB: " + e.getMessage());
						try {
							Message response = new Message("Failed to edit file in the DB: " + e.getMessage(), MessageType.ERROR_MESSAGE);
							client.sendToClient(response);
						} catch (IOException e1) {
							gui.showMessage("Failed to send response to client.");
						}
					} catch (IOException e) {
						gui.showMessage("Failed to send response to client.");
					}
					break;
				case CHANGE_FOLDER_NAME:
	/*			try{
	  					FileDAO fileDAO = new FileDAO(server.getConnection());
						ItemFolder folder = (ItemFolder)msg.getData();
						fileDAO.changeFolderName(folder);
						
						} catch (SQLException e) {
						gui.showMessage("Failed to edit file in the DB: " + e.getMessage());
						try {
							Message response = new Message("Failed to edit file in the DB: " + e.getMessage(), MessageType.ERROR_MESSAGE);
							client.sendToClient(response);
						} catch (IOException e1) {
							gui.showMessage("Failed to send response to client.");
						}
					} catch (IOException e) {
						gui.showMessage("Failed to send response to client.");
					}
					*/
				case CREATE_NEW_FOLDER:
				{
					try
					{
						FileDAO fileDAO = new FileDAO(server.getConnection());
						ItemFolder folder = (ItemFolder)msg.getData();
						
						folder.setID(fileDAO.addFolder(folder));
						Message response = new Message(folder, MessageType.CREATE_NEW_FOLDER);
						client.sendToClient(response);
						
						gui.showMessage("Successfully inserted folder in the DB.");
					} catch (SQLException e) {
						gui.showMessage("Failed to insert folder to the DB: " + e.getMessage());
						try {
							Message response = new Message("Failed to insert folder in the DB: " + e.getMessage(), MessageType.CREATE_NEW_FOLDER);
							client.sendToClient(response);
						} catch (IOException e1) {
							gui.showMessage("Failed to send response to client.");
						}
					} catch (IOException e1) {
						gui.showMessage("Failed to send response to client.");
					}
				}
					
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
			if(server != null )
				server.close();
			server = new Server(port, url, user, password,gui);
		} catch (IOException e) {
		//	try {
				//server.close();  //On: if we are here the server wasn't created so .close cause a NullPointerException
		//} catch (IOException e) {}
			server = null;
			gui.showMessage("ERROR - Could not listen for clients!");
			gui.showMessage(e.getMessage());
			return;
		}
		server.addObserver(ServerController.this);
			
		

	}
	
	
	/**
	 * Get the file from the Server directory
	 * @param name - name = file name.file type
	 * @return File
	 */
	public File getlocalFile(String name) {
		String myBoxPath = System.getProperty("user.home") + "\\Desktop\\MyBox\\";
		File file = new File(myBoxPath + name);
		return file;
	}
	
	/**
	 * Remove the selected file from the server Physically
	 * @param itemFile
	 * @return true/false
	 */
	public boolean deleteLocalFile(ItemFile itemFile){
		try{
			System.out.println("file is not null"); 
			String myBoxPath = System.getProperty("user.home") + "\\Desktop\\MyBox\\";
			System.out.println(myBoxPath);
			System.out.println(itemFile.getType());
			System.out.println(itemFile.getName());
			File file = new File (myBoxPath+itemFile.getName()+itemFile.getType());
			System.out.println("ss");
			
			if (!file.delete())
				throw new Exception();
			return true;
		}
		catch(Exception e){
			System.out.println("catch");
			return false;
		}
	}
}
