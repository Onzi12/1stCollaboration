package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import common.Message;
import common.MessageWithUser;
import model.Group;
import model.Request;
import model.User;
import server.Server;

public class GroupDAO implements DAO<Group> {



	/** This method takes all of the 
	 * groups from the DB and returns
	 * a Set of all of the groups  
	 */	
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
	
	/**  
	 */	
	public void updateUserGroups(Request req)
	 {
		 Connection con = Server.getConnection(); 
		 try(Statement stmt = con.createStatement())
		 {
			 stmt.executeUpdate("delete from grouprequests where userID = " + req.getUser().getID() + " AND groupID = " + req.getGroup().getGroupID());
			 stmt.executeUpdate("insert into usergroups (userID,groupID)  " + "VALUES ("+req.getUser().getID()+","+req.getGroup().getGroupID() + ")");
			 

		 }catch (SQLException e) {
			 try(Statement stmt2 = con.createStatement() ){
			 stmt2.executeUpdate("delete from usergroups where userID = " + req.getUser().getID() + " AND groupID = " + req.getGroup().getGroupID());
			 } catch (SQLException e1) {e1.printStackTrace(); }
		}
	 }
	 
	public void createNewGroup(Group group) throws SQLException
	{
		Connection con = Server.getConnection();
		Statement stmt = con.createStatement();
		stmt.executeUpdate("insert into groups (groupName) VALUES (" + group.getName() +")");
		
	}
	public void updateUserGroupsRejection(Request req)
	 {
		 Connection con = Server.getConnection(); 
		 try(Statement stmt = con.createStatement())
		 {
			 stmt.executeUpdate("delete from grouprequests where userID = " + req.getUser().getID() + " AND groupID = " + req.getGroup().getGroupID());

		 }catch (SQLException e) {e.printStackTrace();}
	 }
	 
	/** gets all of the files that have a privilege of group
	 * and puts them on an HashMap when the key is a string
	 * that contains a groupID and fileID and the value
	 * is an integer who gives as the status 
	 */	
	public HashMap<String,Integer> getGroupsFilePrivelege(){
		  Connection con  = Server.getConnection();
		  HashMap<String,Integer> set = new HashMap<String,Integer>();
		  try(Statement stmt = con.createStatement())	{
		  	ResultSet rs = stmt.executeQuery("select * from filegroups ");
		  	while(rs.next()){
		  		set.put(rs.getString(1) + " " + rs.getString(2), rs.getInt(3));
		  	}
		  }catch (SQLException e){ e.printStackTrace(); }
		  return set;
	}
	
	
	
	/** this method takes a set of Group and takes it to the DB  
	 */	
	public void putAlltoDB(Set<Group> list) {
		for(Group group : list )
			ObjectToDB(group);
	}
	
	/** this method takes the userID and FileID  and checks what 
	 * the privilege of the file in groups that there are users who 
	 */	
	public HashMap<Integer,Integer> getGroupPrivilege(int uid,int fid){
		HashMap<Integer,Integer> res = new HashMap<Integer,Integer>();
		Connection con = Server.getConnection();
		try(Statement stmt = con.createStatement() ) 
		{
			ResultSet rs = stmt.executeQuery("SELECT f.groupID,f.writeAccess FROM filegroups f,usergroups u WHERE " +
											" f.fileID = " + fid + " AND u.userID = " + uid + " and f.groupID = u.groupID");
			if(rs.next())
			{			
				res.put(rs.getInt(1), rs.getInt(2));
			}
		} catch (SQLException e) { e.printStackTrace(); }
		return res;
	}

	public void deleteFromDB(Group group) {
		Connection con = Server.getConnection();
		try(Statement stmt = con.createStatement() ) 
		{
			stmt.executeUpdate("DELETE FROM groups WHERE groupID = " + group.getGroupID() );
		} catch (SQLException e) { e.printStackTrace(); }
		
	}

	
	/** This method do UpSert to the groups table:
	 *  Update group row  - if the group exist
	 *  Create new group  - else
	 * @param group the group to UpSert to groups table
	 */
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

	public Set<Request> allRequestsFromDB(){
		Connection con = Server.getConnection();
		UserDAO uDao = new UserDAO();
		GroupDAO gDao = new GroupDAO();
		Set<Request> set = null;
		set =  new HashSet<Request>();

		try(Statement stmt = con.createStatement())
		{
			ResultSet rs = stmt.executeQuery("select * from grouprequests");
			while(rs.next()){
				Request req = new Request(uDao.DBtoObject(rs.getInt(1)), 
										  gDao.DBtoObject(rs.getInt(2)), 
										  Request.RequestType.values()[rs.getInt(3)]);
				set.add(req);
			}
		}
		catch(SQLException e) {e.printStackTrace(); }
		return set;
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
	 public void updateGroupAccess(HashMap<String,Integer> res)
	 {
		 Connection con = Server.getConnection();
		 try(Statement stmt = con.createStatement() )
		 {
			 stmt.executeUpdate("UPDATE filegroups SET writeAccess = " + res.get("access") + " WHERE fileID = " + res.get("fileId") + " AND groupID = " + res.get("groupId"));
		 }catch (SQLException e) { e.printStackTrace(); }
	 }
	 
	
	/**
	 * Return a set of all the groups the user have 
	 * @param userID the id of the user
	 * @return a set of user groups
	 */
	public Set<Group> getUserGroups(int userID) {
		Connection con = Server.getConnection();
		HashSet<Group> set = null;

		try(Statement stmt = con.createStatement() ) {
			ResultSet rs = stmt.executeQuery("SELECT groupID FROM userGroups WHERE userID = "+userID);
			set = new HashSet<Group>();
			GroupDAO gDao = new GroupDAO();
			while( rs.next() )
				{
				Group group = gDao.DBtoObject( rs.getInt(1) );
				set.add(group);
				}
			} catch(SQLException e){ e.printStackTrace(); }
		
		return set;
	}
	public void addUserGroupRequestsToDB(Message msg) {
		MessageWithUser m = (MessageWithUser)msg;
		Set<Group> updatedUserGroups = (HashSet<Group>)m.getData();
		User user = ((MessageWithUser)msg).getUser();
		GroupDAO gDao = new GroupDAO();
		
		Set<Group> oldGroups = gDao.getUserGroups(user.getID());
		
		Set<Group> newOther = gDao.getAllfromDB();
		newOther.removeAll(updatedUserGroups);
		
		Set<Group> oldOther = gDao.getAllfromDB();
		oldOther.removeAll(oldGroups);
		
		newOther.removeAll(oldOther);
		gDao.createLeaveGroupRequests(user, newOther);
		updatedUserGroups.removeAll(oldGroups);
		gDao.createJoinGroupRequests(user, updatedUserGroups);
		
		
//		Set<Group> all = gDao.getAllfromDB();
//		Set<Group> oldGroups = gDao.getUserGroups(user.getID());
//		all.removeAll(updatedUserGroups); 	//newOtherGroups
//		Set<Group> all2 =  gDao.getAllfromDB();
//		all2.removeAll(oldGroups);	//oldOtherGroups
//		
//		all2.removeAll(all);
//		gDao.createJoinGroupRequests(user,all2);
		
		
	}
	private void createLeaveGroupRequests(User user, Set<Group> join) {
		Connection con = Server.getConnection();
		try(Statement stmt = con.createStatement() )
			{
			for(Group group : join) 
				{
				try{
				stmt.executeUpdate("INSERT INTO grouprequests (userID,groupID,type) "
						          + "VALUES ("+user.getID()+","+group.getGroupID()+","
						          +Request.RequestType.LEAVE.getValue()+")");}catch(SQLException e){}

				}
			} catch (SQLException e) { e.printStackTrace(); }
	}
	private void createJoinGroupRequests(User user, Set<Group> groups) {
		Connection con = Server.getConnection();
		try(Statement stmt = con.createStatement() )
			{
			for(Group group : groups) 
				{
				stmt.executeUpdate("INSERT INTO grouprequests  (userID,groupID,type) "
						          + "VALUES ("+user.getID()+","+group.getGroupID()+","
						          +Request.RequestType.JOIN.getValue()+")");

				}
			} catch (SQLException e) { e.printStackTrace(); }
		
	}

	
	
	
	
	
	
}
