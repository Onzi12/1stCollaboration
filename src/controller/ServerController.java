package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import javax.swing.JOptionPane;

import model.Group;
import model.ItemFile;
import model.ItemFile.State;
import model.ItemFolder;
import model.Request;
import model.User;
import model.User.Status;
import ocsf.server.ConnectionToClient;
import ocsf.server.OriginatorMessage;

import org.apache.ibatis.jdbc.ScriptRunner;

import server.Server;
import boundary.Server_GUI;

import common.ByteArray;
import common.Message;
import common.MessageType;

import dao.FileDAO;
import dao.FolderDAO;
import dao.GroupDAO;
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
        InputStream is = URLClassLoader.getSystemResourceAsStream(script);
        Reader reader = new InputStreamReader(is);
        try {
        	Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con;
            con = DriverManager.getConnection(url,user, pass);
            new ScriptRunner(con).runScript(reader);
        } catch (Exception e2) {
            gui.showMessage(e2.getMessage());
            return;
        } finally {
            try {
				reader.close();
	            is.close();
			} catch (IOException e) {}
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
		try {
			handleNotificationFromServer(arg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void handleNotificationFromServer(Object arg) throws Exception {
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
				

				switch (type) {
				case CREATE_ACCOUNT: 	
				{
					User user = (User)msg.getData();
					UserDAO uDao = new UserDAO();
					if(uDao.DBtoObject(user.getUserName() ) != null )
						{
						String message = "The userName '" + user.getUserName() + "' already exists.";
						gui.showMessage(message);
						Message response = new Message(message, MessageType.ERROR_MESSAGE); 
						sendToClient(client,response);
						return;
						}
					uDao.ObjectToDB(user);
					new FolderDAO().createRootInDB(user); // create root folder for the new user.
					Message response = new Message(null, MessageType.CREATE_ACCOUNT); 
					sendToClient(client,response);
					gui.showMessage(user.getUserName() + " signed up to MyBox.");

				
				}
				break;
				case GET_GROUP_REQUESTS:
				{
					GroupDAO groupDAO = new GroupDAO();
					Message response = new Message(groupDAO.allRequestsFromDB(), MessageType.GET_GROUP_REQUESTS);
					sendToClient(client,response);
				}
				break;
				case REQUEST_ANSWER_ACCEPT:
				{
					Request req = (Request)msg.getData();
					GroupDAO gDao = new GroupDAO();
					gDao.updateUserGroups(req);

				}
				break;
				case REQUEST_ANSWER_REJECT:
				{
					Request req = (Request)msg.getData();
					GroupDAO gDao = new GroupDAO();
					gDao.updateUserGroupsRejection(req);

				}
				break;
				case LOGOUT: 
				{
					User user = (User)msg.getData();
					user.setStatus(Status.NOTCONNECTED);
					new UserDAO().ObjectToDB(user);
					gui.showMessage(user.getUserName() + " logged out.");
				}
					break;
					
				case LOGIN:
				{
						User user = (User)msg.getData();
						GroupDAO groupDAO = new GroupDAO();
						try{
							user = User.authenticate(user.getUserName(), user.getPassword());
							user.setGroups(groupDAO.getUserGroups(user.getID()));
						} catch(Exception e) 
							{
							sendToClient(client,new Message(e.getMessage(), MessageType.ERROR_MESSAGE));
							return;
							}
						gui.showMessage(user.getUserName() + " logged in successfully");
	
						Message response = new Message(user, MessageType.LOGIN);
						sendToClient(client,response);
				}
					break;
				
					
				case UPDATE_FILE_LOCATION:
				{
					FileDAO fileDAO = new FileDAO();
					@SuppressWarnings("unchecked")
					HashMap<String, Object> data = (HashMap<String, Object>)msg.getData();
					ItemFile file = (ItemFile)data.get("file");
					int oldParentID = (int)data.get("oldParentID");
					int userID = (int)data.get("userID");
					file.setUserID(userID);
					fileDAO.updateFileLocationInDB(file, oldParentID);
					Message response = new Message(null, MessageType.UPDATE_FILE_LOCATION);
					sendToClient(client, response);
					gui.showMessage("Moved file to another folder.");
				}
				break;
				case CREATE_NEW_GROUP:
				{
					Group group = (Group)msg.getData();
					GroupDAO gDao = new GroupDAO();

					gDao.ObjectToDB(group);
					
					//SQL error
					if(group == null)
						{
						sendToClient(client,new Message("Group Creation Failed!!! SQL ERROR", MessageType.ERROR_MESSAGE));
						return;
						}
					
					//Group With the same name already exists
					if(group.getGroupID() == 0 )
					{
					String err = "Group Creation Failed, A group with the name '"+group.getName()+"'"+
								"\nAlready exists!!";
					Message responce = new Message(err, MessageType.ERROR_MESSAGE);
					sendToClient(client,responce);
					return;
					}
					
					//Success!
					Message response = new Message(group,MessageType.CREATE_NEW_GROUP);
					sendToClient(client, response);
					gui.showMessage("New Group '"+group.getName()+"' created successfully!");
				}
				break;
				case GET_RESTORE_FILES:
				{
					User user = (User)msg.getData();
					FileDAO fileDAO = new FileDAO();
					Set<ItemFile> res = fileDAO.getUserRestoreFile(user.getID());
					Message response = new Message(res, MessageType.GET_RESTORE_FILES);
					sendToClient(client, response);
				}
				break;
				
				case RESTORE_FILE:
				{
					ItemFile file = (ItemFile)msg.getData();
					FileDAO fileDAO = new FileDAO();
//					fileDAO.userRestoreFile(file.getID());
					file.setState(State.NORMAL);
					fileDAO.ObjectToDB(file);
					Message response = new Message(null, MessageType.RESTORE_FILE);
					sendToClient(client, response);
					
					UserDAO uDao = new UserDAO();
					Set<User> users = uDao.getActiveFileUsers(file);
					
					Message broadcastMessage = new Message(users, MessageType.BROADCAST_FILE_STATE_CHANGE);
					server.sendToAllClients(broadcastMessage);
				}
				break;
				
				case GET_ADD_FILES:
				{
					ItemFolder folder = (ItemFolder)msg.getData();

					FileDAO fileDAO = new FileDAO();
					HashSet<ItemFile> res = (HashSet<ItemFile>)fileDAO.getAddUserFiles(folder.getUserID(),folder.getID());
					Message response = new Message(res, MessageType.GET_ADD_FILES);
					sendToClient(client, response);
				}
					break;
					
				case GET_GROUP_FILE_PRIV:
				{
					GroupDAO groupDAO = new GroupDAO();
					HashMap <String,Integer> res = groupDAO.getGroupsFilePrivelege();
					Message response = new Message(res, MessageType.GET_GROUP_FILE_PRIV);
					sendToClient(client, response);
				}
				break;
				
				case GET_USER_GROUPS:
				{
					User user = (User)msg.getData();
					Set<Group> groups = new GroupDAO().getUserGroups(user.getID());
					Message response = new Message(groups, MessageType.GET_USER_GROUPS);
					sendToClient(client, response);
				}
					break;
				case SEND_NEW_GROUP_REQUESTS: {

					new GroupDAO().addUserGroupRequestsToDB(msg);
					Message response = new Message(null, MessageType.SEND_NEW_GROUP_REQUESTS);
					sendToClient(client, response);
				}
					break;
					
				case GET_ALL_OTHER_GROUPS: {
					
					User user = (User)msg.getData();
					GroupDAO gDao = new GroupDAO();
					Set<Group> all = gDao.getAllfromDB();
					Set<Group> userGroups = gDao.getUserGroups(user.getID());
					all.removeAll(userGroups);
					Message response = new Message(all , MessageType.GET_ALL_OTHER_GROUPS);
					sendToClient(client, response);
				}	
					break;
				
				case UPDATE_FILE_GROUPS_ACCESS:
				{
					@SuppressWarnings("unchecked")
					HashMap<String,Integer> groupAccess = (HashMap<String , Integer>)msg.getData();
					GroupDAO groupDAO = new GroupDAO();

					groupDAO.updateGroupAccess(groupAccess);
					Message response = new Message(null, MessageType.UPDATE_FILE_GROUPS_ACCESS);
					sendToClient(client, response);
					

				}
				break;
				
				case GET_FILES:
				{	
					User user = (User)msg.getData();

						try {
							new FolderDAO().DBtoTree(user);
						} catch (SQLException e) {
							gui.showMessage(e.getMessage());
							Message response = new Message("Failed to retrieve files.", MessageType.ERROR_MESSAGE);
							sendToClient(client,response);
						}

						Message response = new Message(user.getRootFolder(), MessageType.GET_FILES);
						sendToClient(client,response);

				}
					break;
				case GET_FILE_GROUPS_ACCESS:{
					ItemFile file = (ItemFile)msg.getData();
					GroupDAO groupdao = new GroupDAO();
					HashMap<Integer,Integer> res = groupdao.getGroupPrivilege(file.getOwner().getID(), file.getID());
					Message response = new Message(res, MessageType.GET_FILE_GROUPS_ACCESS);
					sendToClient(client,response);
				}
					break;
				
				case ADD_FILE:
				{
					@SuppressWarnings("unchecked")
					HashMap<String, Object> data = (HashMap<String, Object>)msg.getData();
					ItemFile file = (ItemFile)data.get("file");
					int parentID = ((Integer)data.get("parentID")).intValue();
					int userID = ((Integer)data.get("userID")).intValue();
					FileDAO fileDAO = new FileDAO();
					try{
						fileDAO.insertToUserFiles(file, parentID, userID);
						Message response = new Message(file, MessageType.ADD_FILE);
						sendToClient(client,response);
					}catch(SQLException e){
						gui.showMessage("Failed to Download file to client: ");	
					}
				}
					break;	
				
				case GET_DELETE_FILE_PHYSICAL:
				{
					User user = (User)msg.getData(); 
					FileDAO fileDAO = new FileDAO();
					HashSet<ItemFile> res = (HashSet<ItemFile>)fileDAO.getDeletePhysicalFiles(user.getID());
					Message response = new Message(res, MessageType.GET_DELETE_FILE_PHYSICAL);
					sendToClient(client, response);
				}
				break;
				case REMOVE_FOLDER:{
					ItemFolder folder = (ItemFolder)msg.getData();
					FolderDAO folderDAO = new FolderDAO();
					folderDAO.deleteFromDB(folder);
					Message response = new Message(folder, MessageType.REMOVE_FOLDER);
					sendToClient(client,response);
				}
				break;
				case DELETE_FILE_PHYSICAL:{
					FileDAO fileDAO = new FileDAO();
					ItemFile file = (ItemFile)msg.getData(); 
					fileDAO.deletePhysicalFile(file);
					Message response = new Message(file, MessageType.GET_DELETE_FILE_PHYSICAL);
					sendToClient(client, response);
					
					UserDAO uDao = new UserDAO();
					Set<User> users = uDao.getActiveFileUsers(file);
					
					Message broadcastMessage = new Message(users, MessageType.BROADCAST_FILE_STATE_CHANGE);
					server.sendToAllClients(broadcastMessage);
					}
					break;
					
				case DELETE_FILE_VIRTUAL:
				{
					FileDAO fileDAO = new FileDAO();
					@SuppressWarnings("unchecked")
					HashMap<String, Object> data = (HashMap<String, Object>)msg.getData();
					ItemFile file = (ItemFile)data.get("file");
					int userID = (int)data.get("userID");
					int parentID = (int)data.get("parentID");
					file.setUserID(userID);
					fileDAO.deleteFromUserFiles(file, parentID);
					
					if (userID == file.getOwner().getID()) {
						file.setState(State.ABANDONED);
						fileDAO.ObjectToDB(file);
					}
					Message response = new Message(file, MessageType.DELETE_FILE_VIRTUAL);
					sendToClient(client, response);

					UserDAO uDao = new UserDAO();
					Set<User> users = uDao.getActiveFileUsers(file);
					
					Message broadcastMessage = new Message(users, MessageType.BROADCAST_FILE_STATE_CHANGE);
					server.sendToAllClients(broadcastMessage);
				}
					break;
				case GET_ALL_FILES:
				{
					FileDAO fDAO = new FileDAO();
					Set<ItemFile> set = fDAO.getAllfromDB();
					Message response = new Message(set, MessageType.GET_ALL_FILES);
					sendToClient(client,response);
				}
				break;
				
				case GET_ALL_GROUPS:
				{
					GroupDAO gDAO = new GroupDAO();
					Set<Group> set = gDAO.getAllfromDB();
					Message response = new Message(set, MessageType.GET_ALL_GROUPS);
					sendToClient(client,response);
				}
				break;
				
				case UPLOAD_FILE: 
				{
					@SuppressWarnings("unchecked")
					HashMap<String, Object> data = (HashMap<String, Object>)msg.getData();
					ItemFile file1 = (ItemFile)data.get("file");
					boolean isUpdate = (boolean)data.get("isUpdate");
					byte[] bFile = file1.getFile();
					String myBoxPath = System.getProperty("user.home") + "\\Desktop\\MyBox\\";
					ByteArray.writeByteArrayToFile(bFile, myBoxPath + file1.getName());
					FileDAO fDao = new FileDAO();
					fDao.ObjectToDB(file1);
					if (isUpdate == false) 
						fDao.addToUserFilesDB(file1);
					Message response = new Message(file1, MessageType.UPLOAD_FILE);
					sendToClient(client,response);
				    gui.showMessage("Successfully uploaded file to the DB.");
				}
					break;
				

					
				case FILE_EDIT:
				{
			        try {
				   
						FileDAO fileDAO = new FileDAO();
						ItemFile file = (ItemFile)msg.getData();
						file.setIsEdited(false);
						fileDAO.ObjectToDB(file);
						
						Message response = new Message(file, MessageType.FILE_EDIT);
						client.sendToClient(response);
						
						gui.showMessage("Successfully edited file in the DB.");
						
					} catch (Exception e) {
						gui.showMessage("Failed to edit file in the DB: " + e.getMessage());

					}
				}
					break;
					
				case CHANGE_FOLDER_NAME:
				{
					FolderDAO folderDAO = new FolderDAO();
					ItemFolder folder = (ItemFolder)msg.getData();
					folderDAO.ObjectToDB(folder);
					Message response = new Message(folder, MessageType.CHANGE_FOLDER_NAME);
					sendToClient(client, response);
				}
					break;
				case CREATE_NEW_FOLDER:
				{
					try
					{
						FolderDAO folderDAO = new FolderDAO();
						ItemFolder folder = (ItemFolder)msg.getData();
						folderDAO.checkInsert(folder);
						folderDAO.ObjectToDB(folder);
						
						Message response = new Message(folder, MessageType.CREATE_NEW_FOLDER);
						sendToClient(client, response);
						
					} catch (SQLException e) {
						gui.showMessage("Failed to insert folder to the DB: " + e.getMessage());
						Message response = new Message("Failed to insert folder in the DB: " + e.getMessage(), MessageType.ERROR_MESSAGE);
						sendToClient(client, response);
					}
				}
				break;	
				
				case DOWNLOAD_FILE:
				{
					ItemFile iFile = (ItemFile)msg.getData();
					File pFile = new File(System.getProperty("user.home") + "\\desktop\\MyBox\\" + iFile.getName());
					try {
						iFile.setFile(ByteArray.convertFileToByteArray(pFile));
						Message response = new Message(iFile,MessageType.DOWNLOAD_FILE);
						client.sendToClient(response);
					} catch (Exception e) {
						gui.showMessage("Failed to Download file to client: " + e.getMessage());
						try {
							Message response = new Message("Failed to Download file" + e.getMessage(), MessageType.ERROR_MESSAGE);
							client.sendToClient(response);
						} catch (IOException e1) {
							gui.showMessage("Failed to send response to client.");
						}
					}
				}
				break;
				
				case CAN_EDIT_FILE:
				{
					FileDAO fDao = new FileDAO();
					int fileID = (int)msg.getData();
					ItemFile fileDB = fDao.DBtoObject(fileID);
					if (fileDB.isEdited() == false) {
						fileDB.setIsEdited(true);
						fDao.ObjectToDB(fileDB);
						Message response = new Message(fileDB, MessageType.CAN_EDIT_FILE);
						sendToClient(client, response);
					} else {
						Message response = new Message(fileDB.getName() + "is currently being edited by another user.", MessageType.ERROR_MESSAGE);
						sendToClient(client, response);
					}
				}
					break;
					
				case FINISHED_EDITING_FILE:
				{
					FileDAO fDao = new FileDAO();
					ItemFile file = (ItemFile)msg.getData();
					file.setIsEdited(false);
					fDao.ObjectToDB(file);
					Message response = new Message(null, MessageType.FINISHED_EDITING_FILE);
					sendToClient(client, response);
				}
					break;
				
				default:
					break;
				}
				
			} 

		}
	}
	
	
	private void sendToClient(ConnectionToClient client, Message message) {
		try{
			client.sendToClient(message);
		}catch (IOException e) {
			gui.showMessage("Failed to send response to client.");
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

			String myBoxPath = System.getProperty("user.home") + "\\Desktop\\MyBox\\";
			File file = new File (myBoxPath+itemFile.getName());

			
			if (!file.delete())
				throw new Exception();
			return true;
		}
		catch(Exception e){

			return false;
		}
	}
}
