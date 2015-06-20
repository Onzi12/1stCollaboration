package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import server.Server;

public class GroupDAO extends Group {

	public static TreeSet<Group> getUserGroups(int id) {
		
		
		return null;
	}

	public static Group DBtoObject(int groupID) {
		return null;
	}
	
	public static HashMap<Integer,GroupDAO> DBgetAll() {
		Connection con = Server.getConnection();
		HashMap<Integer,GroupDAO> map = null;

		try(Statement stmt = con.createStatement() ) {
			ResultSet rs = stmt.executeQuery("SELECT * FROM groups");
			map = new HashMap<Integer,GroupDAO>();
			while( rs.next() )
				{
				GroupDAO group = new GroupDAO();
				group.setGroupID(rs.getInt(1));
				group.setName(rs.getString(2));
				map.put(group.getGroupID(), group);
				}
			} catch(SQLException e){ e.printStackTrace(); }
		
		return map;
	}
	
	
	public static void AlltoDB() {
		Connection con = Server.getConnection();
		HashMap<Integer,GroupDAO> map = Server.getAllGroups();

		for(GroupDAO group : map.values() )
		{
			try(PreparedStatement stmt = con.prepareStatement("UPDATE groups SET groupName = ? WHERE groupID = ?") ) {
				stmt.setString(1, group.getName());
				stmt.setInt(2, group.getGroupID());
				if ( stmt.executeUpdate() == 0 )
					Server.gui.showMessage("Error: group "+group.getName()+" does not exist in DB");
				} catch(SQLException e) { e.printStackTrace(); }
			
		}
		
		
	}

	/** This method do UpSert to the groups table:
	 *  Update group row  - if the group exist
	 *  Create new group  - else
	 * @param group the group to UpSert to groups table
	 */
	public static void objectToDB(GroupDAO group) {
		Connection con = Server.getConnection();
		try(Statement stmt = con.createStatement() )
			{
			int update = stmt.executeUpdate("UPDATE groups SET groupName = '"+group.getName()+
										   "' WHERE groupID = "+group.getGroupID());
			if(update == 0)
				stmt.executeUpdate("INSERT INTO groups (groupName) VALUES('"+group.getName()+"')" );
			} catch (SQLException e) { e.printStackTrace(); }
	}
}
