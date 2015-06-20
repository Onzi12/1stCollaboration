package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import server.Server;

public class FolderDAO extends ItemFolder {

		
		public static ItemFolder DBgetRoot(int userID) throws SQLException {
			FolderDAO root;
			Connection con = Server.getConnection();
			try(Statement stmt  = con.createStatement() ) 
			{
				ResultSet rs = stmt.executeQuery("SELECT folderID FROM folder WHERE userID = "+userID+" AND name = 'root'");
				if(rs.next())
					{
					root = new FolderDAO();
					root.setParent(null);
					root.setName("root");
					root.setId( rs.getInt("folderID") );
					root.createContent();
					}
				else 
					{
					System.err.println("ERROR: root folder for user" +userID +" is not exist!! ");
					return null; 
					}
			}
			return root;
		}
		
		
		private void createContent() {
			Connection con = Server.getConnection();
			try( Statement stmt  = con.createStatement() ) 
			{
				ResultSet rs = stmt.executeQuery("SELECT fileId FROM userfiles WHERE folderID = "+getId());
				while(rs.next())
				  {
					//try(PreparedStatement st = con.prepareStatement(")) continue tomorrow..
				  }
			}
			
			stmt2 = connection.prepareStatement("SELECT * FROM userfiles,file where userId = ? and file.id = userfiles.fileId");//where userId = ?
			stmt2.setInt(1, user.getID());
			rs2 = stmt2.executeQuery();
		}


		//Used by the server only
		public ItemFolder DBgetFolder(int userID, int folderID) {
			return null;
		}
		
		//Used by the server only
		public ItemFolder DBgetFolder(int userID, int folderID,String folderName) {
			return null;
		}

		//Used by the server only
		public Item[] DBgetFolderItems(int userID, int folderID) {
			return null;
		}

}
