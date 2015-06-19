package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import model.Item;
import model.ItemFile;
import model.ItemFolder;
import model.User;

public class FileDAO {

	private Connection connection; 
	
	public FileDAO(Connection connection) {
		this.connection = connection;
	}
	
	public HashMap<String, Item> getAllFiles(User user) throws SQLException {
		HashMap<String, Item> items = new HashMap<String, Item>();
		ResultSet rs1 = null,rs2 = null;
		PreparedStatement stmt1 = null,stmt2 = null;
		try {
			stmt1 = connection.prepareStatement("select * from folder where userId = ?");
			stmt1.setInt(1, user.getID());
			rs1 = stmt1.executeQuery();
			
			while (rs1.next()) {
					ItemFolder folder = new ItemFolder(rs1.getInt("id"));
					folder.setName(rs1.getString("name"));
					folder.setFolder(rs1.getInt("folderId"));
					folder.setUserId(user.getID());
					items.put("folder" + folder.getStringID(), folder);
			}
			
			stmt2 = connection.prepareStatement("SELECT * FROM userfiles,file where userId = ? and file.id = userfiles.fileId");//where userId = ?
			stmt2.setInt(1, user.getID());
			rs2 = stmt2.executeQuery();
			
			while (rs2.next()) {
					ItemFile file = new ItemFile(rs2.getInt("id"));
					file.setName(rs2.getString("name"));
					file.setFolder(rs2.getInt("folderId"));
					file.setOwner(rs2.getInt("ownerId"));
					file.setUserId(user.getID());
					file.setType(rs2.getString("type"));
					items.put("file" + file.getStringID(), file);
			}

			return items;		
		}
		finally {
			if (rs1 != null)
				rs1.close();
			if (stmt1 != null) 
				stmt1.close();
			if (rs2 != null)
				rs2.close();
			if (stmt2 != null) 
				stmt2.close();
		}
	}
	
	public int addFolder(ItemFolder folder) throws SQLException
	{
		PreparedStatement stmt = null,stmt2 = null;
		ResultSet rs = null,rs2 = null;		
		try {
			stmt = connection.prepareStatement("select * from folder f where f.name = ? and f.userId = ? and f.folderId = ?");
			stmt.setString(1, folder.getName());
			stmt.setInt(2,folder.getUserId());
			stmt.setInt(3, folder.getFolderID());
			System.out.println(folder.getName() + " " + folder.getUserId() + " " + folder.getFolderID());
			rs = stmt.executeQuery();
			if(rs.next()) {
				throw new SQLException("there is a folder with the same name");
			}
			stmt2 = connection.prepareStatement("insert into folder" + " (folderId, userId, name)" + " values(?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			stmt2.setInt(1, folder.getFolderID());
			stmt2.setInt(2, folder.getUserId());
			stmt2.setString(3, folder.getName());
			stmt2.executeUpdate();
			
			rs2 = stmt2.getGeneratedKeys();
		    if (rs2.next()) {
		    	return rs2.getInt(1);
		    } 		    
		    throw new SQLException("Failed to create id.");
		}
		finally {
			if (rs != null)
				rs.close();
			if (stmt != null) 
				stmt.close();
			if (rs2 != null)
				rs2.close();
			if (stmt2 != null) 
				stmt2.close();
		}
	}
	public HashMap<String, Item> getAllAddFiles(User user) throws SQLException {
		System.out.println("userid:" + user.getID());
		String st = "SELECT * FROM file f where f.id not in (select u.fileId from userFiles u where u.userId = ?) and f.havedeleted = 0 and f.priv = ";
		HashMap<String, Item> items = new HashMap<String, Item>();
		ResultSet rs = null;
		PreparedStatement stmt = null;
		ResultSet rs2 = null;
		PreparedStatement stmt2 = null;
		try {
			stmt = connection.prepareStatement(st + "2" );
			stmt.setInt(1, user.getID());
			rs = stmt.executeQuery();
			
			while (rs.next()) {
					ItemFile file = new ItemFile(rs.getInt("id"));
					file.setName(rs.getString("name"));
					System.out.println(file.getName()+("\n"));
					items.put("file" + file.getStringID(), file);
			}
			stmt2 = connection.prepareStatement(st + "1 and f.id in (select fg.fileId from filegroups fg,usergroups ug where fg.groupId = ug.groupId and ug.userId = ?)" );
			stmt2.setInt(1, user.getID());
			stmt2.setInt(2, user.getID());
			rs2 = stmt.executeQuery();
			
			while (rs2.next()) {
					ItemFile file = new ItemFile(rs2.getInt("id"));
					file.setName(rs2.getString("name"));
					items.put("file" + file.getStringID(), file);
			}

			return items;		
		}
		finally {
			if (rs != null)
				rs.close();
			if (stmt != null) 
				stmt.close();
			if (rs2 != null)
				rs2.close();
			if (stmt2 != null) 
				stmt2.close();
		}
	}
	
	
	
	
	@Deprecated
	public void updateFile(ItemFile file) throws SQLException {
		PreparedStatement stmt = null;

		try {
			stmt = connection.prepareStatement("update file " + "set name = ?,location = ?" + " where id = ?");
			stmt.setString(1, file.getName());
			stmt.setString(2, file.getFullPath());
			stmt.setInt(3, file.getID());
			stmt.executeUpdate();
		}
		finally {
			if (stmt != null)
				stmt.close();
		}
	}
	
	public int addFile(ItemFile file) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;		
		try {
			stmt = connection.prepareStatement("insert into userFiles" + " (userId, folderId, fileId)" + " values(?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, file.getUserId());
			stmt.setInt(2, file.getFolderID());
			stmt.setInt(3, file.getID());
			stmt.executeUpdate();
			
			rs = stmt.getGeneratedKeys();
		    if (rs.next()) {
		    	return rs.getInt(1);
		    } 		    
		    return 0;
		}
		finally {
			if (rs != null)
				rs.close();
			if (stmt != null) 
				stmt.close();
		}
	}
	
	public void uploadFile(ItemFile file) throws SQLException {
		PreparedStatement stmt = null,stmt2 = null;
		ResultSet rs = null;		
		try {
			stmt = connection.prepareStatement("insert into file" + " (name, fileDesc, priv, ownerId, type)" + " values(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, file.getName());
			stmt.setString(2, file.getDescription());
			stmt.setInt(3, file.getPrivilege().getValue());
			stmt.setInt(4, file.getOwner());
			stmt.setString(5, file.getType());
			stmt.executeUpdate();
			rs = stmt.getGeneratedKeys();
		    if (rs.next()) {
				file.setID(rs.getInt(1));
				stmt2 = connection.prepareStatement("insert into userFiles" + " (userId, folderId, fileId)" + " values(?, ?, ?)");
				stmt2.setInt(1,file.getOwner());
				stmt2.setInt(2, file.getFolderID());
				stmt2.setInt(3, file.getID());
				stmt2.executeUpdate();
		    } 		    
		}
		finally {
			if (rs != null)
				rs.close();
			if (stmt != null) 
				stmt.close();
			if (stmt2 != null) 
				stmt2.close();
		}
	}
	
	public void deletePhysicalFile(ItemFile file) throws SQLException {
		PreparedStatement stmt = null , stmt2 = null;
		ResultSet rs = null;
		try {
			stmt2 = connection.prepareStatement("select f.isEdited from file f where f.id = ?");
			stmt2.setInt(1, file.getID());
			rs = stmt2.executeQuery();
			if (rs.next())
				if(rs.getInt("isEdited") == 1)
					throw new SQLException("file is being edited");
			stmt = connection.prepareStatement("delete from file where id = ?");
			stmt.setInt(1, file.getID());
			stmt.executeUpdate();
		}
		finally {
			if (stmt != null)
				stmt.close();
			if (stmt2 != null)
				stmt2.close();
			if (rs != null)
				rs.close();
		}
	}
	
	public void changeFolderName(ItemFolder folder){
		PreparedStatement stmt = null;
		ResultSet rs = null;
	}
	public void deleteVirtualFile(ItemFile file) throws SQLException {
		PreparedStatement stmt = null ;
		try {
			stmt = connection.prepareStatement("delete from userFiles where fileId = ? and userId = ?;");
			stmt.setInt(1, file.getID());
			stmt.setInt(2, file.getUserId());
			System.out.println(file.getUserId()+ " " + file.getID());
			stmt.executeUpdate();
		}
		finally {
			if (stmt != null)
				stmt.close();
		}
	}
	
	
	public void setFileDB(ItemFile file) throws SQLException{
		PreparedStatement stmt = null;
		
		try {
			
			stmt = connection.prepareStatement("update file " + "set priv = ?,fileDesc = ?,name = ?" + " where id = ?");
			stmt.setInt(1, file.getPrivilege().getValue());
			stmt.setString(2, file.getDescription());
			stmt.setString(3, file.getName());
			stmt.setInt(4, file.getID());
			stmt.executeUpdate();
			
		} finally {
			if (stmt != null)
				stmt.close();
		}
		
	}
	
	/**
	 * Update the file's folderId after moving the file to another folder
	 * @param file
	 * @throws SQLException
	 */
	public void updateuserFiles(ItemFile file) throws SQLException {
		
		PreparedStatement stmt = null;
		
		try {
			//TODO GIL: do i need to update 'canUpdate'
			stmt = connection.prepareStatement("update userFiles" + " set folderId = ?" + " where userId = ? and fileId = ?");
			stmt.setInt(1, file.getFolderID());
			stmt.setInt(2, file.getUserId());
			stmt.setInt(3, file.getID());
			stmt.executeUpdate();
			
		} finally {
			if (stmt != null)
				stmt.close();
		}
		
	}

}
