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

public class FileDAO {

	private Connection connection; 
	
	public FileDAO(Connection connection) {
		this.connection = connection;
	}
	
	public HashMap<String, Item> getAllFiles() throws SQLException {
		HashMap<String, Item> items = new HashMap<String, Item>();
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery("select * from file");
			
			while (rs.next()) {
				
				if (rs.getBoolean("isfolder")) {
					
					ItemFolder folder = new ItemFolder(rs.getInt("id"));
					folder.setName(rs.getString("name"));
					items.put(Integer.toString(folder.getID()), folder);
					
				} else {
					
					ItemFile file = new ItemFile(rs.getInt("id"));
					file.setName(rs.getString("name"));
					items.put(Integer.toString(file.getID()), file);
					
				}
				
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
			stmt = connection.prepareStatement("update file " + "set name = ?, location = ?" + " where id = ?");
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
	
	public void deleteFile(ItemFile file) throws SQLException {
		PreparedStatement stmt = null;
		
		try {
			stmt = connection.prepareStatement("delete from file where id = ?");
			stmt.setInt(1, file.getID());
			stmt.executeUpdate();
		}
		finally {
			if (stmt != null)
				stmt.close();
		}
	}
}
