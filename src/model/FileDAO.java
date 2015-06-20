package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import server.Server;

public class FileDAO extends ItemFile {

	public static FileDAO DBtoObject(int fileID) {
		Connection con = Server.getConnection();
		FileDAO file = null;
		try(PreparedStatement stmt = con.prepareStatement("SELECT * FROM file WHERE fileID = ?") ) {
			stmt.setInt(1, fileID);
			ResultSet rs = stmt.executeQuery();
			file = new FileDAO();
			file.setID(rs.getInt(1));
			file.setName(rs.getString(2));
			file.setDescription(rs.getString(3));
			file.setPrivilege(Privilege.values()[rs.getInt(4)]);
			file.setIsDeleted(rs.getBoolean(5));
			file.setIsEdited(rs.getBoolean(6));
			file.setOwner(UserDAO.DBtoObject(rs.getInt(7)));	
		} catch(SQLException e){ Server.gui.showMessage("SQLERROR in FileDAO DBtoObject");}
		
		
		return file;
	}

}
