package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import model.Group;
import server.Server;

public class GroupDAO extends DAO<Group> {


	

	/** This method do UpSert to the groups table:
	 *  Update group row  - if the group exist
	 *  Create new group  - else
	 * @param group the group to UpSert to groups table
	 */
	public static void objectToDB(GroupDAO group) {

	}

	


	
	public Set<Group> getAllfromDB() {
		Connection con = Server.getConnection();
		HashSet<Group> set = null;

		try(Statement stmt = con.createStatement() ) {
			ResultSet rs = stmt.executeQuery("SELECT * FROM groups");
			set = new HashSet<Group>();
			while( rs.next() )
				{
				Group group = new Group();
				group.setGroupID(rs.getInt(1));
				group.setName(rs.getString(2));
				set.add(group);
				}
			} catch(SQLException e){ e.printStackTrace(); }
		
		return set;
	}


	public void putAlltoDB(Set<Group> list) {
		for(Group group : list )
			ObjectToDB(group);
	}


	public void deleteFromDB(Group group) {
		Connection con = Server.getConnection();
		try(Statement stmt = con.createStatement() ) 
		{
			stmt.executeUpdate("DELETE FROM groups WHERE groupID = " + group.getGroupID() );
		} catch (SQLException e) { e.printStackTrace(); }
		
	}


	@Override
	public Group DBtoObject(int id) {
		Connection con = Server.getConnection();
		Group group = null;
		try(Statement stmt = con.createStatement() ) 
		{
			ResultSet rs = stmt.executeQuery("SELECT * FROM groups WHERE groupID = " + id );
			group = new Group();
			if(rs.next())
			{
			group.setGroupID(rs.getInt(1));
			group.setName(rs.getString(2));
			}
		} catch (SQLException e) { e.printStackTrace(); }
		return group;
	}


	@Override
	public void ObjectToDB(Group group) {
		Connection con = Server.getConnection();
		try(Statement stmt = con.createStatement() )
			{
			int update = stmt.executeUpdate("UPDATE groups SET groupName = '"+group.getName()+
										   "' WHERE groupID = "+group.getGroupID());
			if(update == 0) {
				stmt.executeUpdate("INSERT INTO groups (groupName) "
						          + "VALUES('"+group.getName()+"')",Statement.RETURN_GENERATED_KEYS );
				ResultSet rs = stmt.getGeneratedKeys();
				if( rs.next() ) {
				group.setGroupID( rs.getInt(1) );
				}
			}
			} catch (SQLException e) { e.printStackTrace(); }
	}


	@Override
	public Group DBtoObject(String name) {
		Connection con = Server.getConnection();
		Group group = null;
		try(Statement stmt = con.createStatement() ) 
		{
			ResultSet rs = stmt.executeQuery("SELECT * FROM groups WHERE groupName = '" + name+"'" );
			if (rs.next() ) 
			{
				group = new Group();
				group.setGroupID(rs.getInt(1));
				group.setName(rs.getString(2));
			}
		} catch (SQLException e) { e.printStackTrace(); }
		return group;
	}
	
	
	/*FIX TOMORROW BLATTTTTTTT*/
	public Set<Group> getUserGroups(int userID) {
		Connection con = Server.getConnection();
		HashSet<Group> set = null;

		try(Statement stmt = con.createStatement() ) {
			ResultSet rs = stmt.executeQuery("SELECT * FROM userGroups WHERE userID = "+userID);
			set = new HashSet<Group>();
			while( rs.next() )
				{
				Group group = new Group();
				group.setGroupID(rs.getInt(1));
				group.setName(rs.getString(2));
				set.add(group);
				}
			} catch(SQLException e){ e.printStackTrace(); }
		
		return set;
	}
	
	
}
