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
					items.put("folder" + Integer.toString(folder.getID()), folder);
			}
			
			stmt2 = connection.prepareStatement("SELECT * FROM test.userfile,file where userId = ? and file.id = userfile.fileId");//where userId = ?
			stmt2.setInt(1, user.getID());
			rs2 = stmt2.executeQuery();
			
			while (rs2.next()) {
					ItemFile file = new ItemFile(rs2.getInt("id"));
					file.setName(rs2.getString("name"));
					file.setFolder(rs2.getInt("folderId"));
					items.put("file" + Integer.toString(file.getID()), file);
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
	
	public HashMap<String, Item> getAllAddFiles(User user) throws SQLException {
		HashMap<String, Item> items = new HashMap<String, Item>();
		ResultSet rs = null;
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement("SELECT * FROM userfile u,file f where u.userId <> ? and f.id = u.fileId and u.priv <> 0" );//where userId = ?
			stmt.setInt(1, user.getID());
			rs = stmt.executeQuery();
			
			while (rs.next()) {
					ItemFile file = new ItemFile(rs.getInt("id"));
					file.setName(rs.getString("name"));
					file.setFolder(rs.getInt("folderId"));
					items.put("file" + Integer.toString(file.getID()), file);
			}

			return items;		
		}
		finally {
			if (rs != null)
				rs.close();
			if (stmt != null) 
				stmt.close();
		}
	}
	
	
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
			stmt = connection.prepareStatement("insert into file" + " (name, location)" + " values(?, ?)", Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, file.getName());
			stmt.setString(2, file.getFullPath());
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
			stmt = connection.prepareStatement("insert into file" + " (name, fileDesc, priv, ownerId)" + " values(?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, file.getName());
			System.out.println( file.getID());
			stmt.setString(2, file.getDescription());
			stmt.setInt(3, file.getPrivilege().getValue());
			stmt.setInt(4, file.getOwner());
			stmt.executeUpdate();
			rs = stmt.getGeneratedKeys();
		    if (rs.next()) {
				file.setID(rs.getInt(1));
				stmt2 = connection.prepareStatement("insert into userfile" + " (userId,  fileId)" + " values(?, ?)");
				stmt2.setInt(1,file.getOwner());
				stmt2.setInt(2, file.getID());
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
	public void deleteFile(ItemFile file) throws SQLException {
		PreparedStatement stmt = null;
		
		try {
			stmt = connection.prepareStatement("delete from userfile where fileId = ?");
			stmt.setInt(1, file.getID());
			stmt.executeUpdate();
		}
		finally {
			if (stmt != null)
				stmt.close();
		}
	}
	
	public void setFileDB(ItemFile file) throws SQLException{
		PreparedStatement stmt = null;
		stmt = connection.prepareStatement("UPDATE file SET priv=?,fileDesc=?,name=? where name = ?");
		stmt.setInt(1, file.getPrivilege().getValue());
		stmt.setString(2, file.getDescription());
		stmt.setString(3, file.getName());
		stmt.setString(4, file.getName());
		stmt.executeUpdate();
	}

}
