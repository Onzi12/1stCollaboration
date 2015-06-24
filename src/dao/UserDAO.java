package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import model.Group;
import model.ItemFile;
import model.User;
import server.Server;

/**
 * Class UserDAO is Used to access the DataBase from the server to withdraw/input all needed information regarding a user
 * @author Idan
 *
 */
public class UserDAO implements DAO<User> {
	
	/**
	 * Returns a set of all existing users from the Database
	 * @return set<User>
	 */
	@Override
	public Set<User> getAllfromDB() {
		Connection con = Server.getConnection();
		HashSet<User> set = new HashSet<User>();
		try(Statement stmt = con.createStatement()){					
			ResultSet rs = stmt.executeQuery("SELECT * FROM user");	//withdraws all fields from all user table
			while(rs.next()){
				User user = inputUser(rs);				//retrieves all user fields from the ResultSet
				set.add(user);							//inputs retrieved user into the user set
			}  
		} catch(SQLException e){ e.printStackTrace(); }
		return set;
	}

	/**
	 * returns a single existing User details from the DataBase according to userID
	 * @param int id
	 * @return User 
	 */
	@Override
	public User DBtoObject(int id) {
		Connection con = Server.getConnection();
		User user = new User();
		try ( Statement stmt = con.createStatement()){
			ResultSet rs = stmt.executeQuery("SELECT * FROM USER WHERE userID = " + id);	//withdraws from DB all User fields by the userId
			if (rs.next()) 
				user = inputUser(rs);	//retrieves all user fields from the ResultSet
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	/**
	 * returns a single existing User details from the DataBase according to userName
	 * @param String userName
	 * @return User
	 */
	@Override
	public User DBtoObject(String userName) {
		Connection con = Server.getConnection();
		User user = null;
		try (Statement stmt = con.createStatement()) {
			ResultSet rs = stmt.executeQuery("SELECT * FROM USER WHERE userName = '" + userName+"'"); //withdraws from DB all User fields by the userName 
			if (rs.next())
				user = inputUser(rs); //retrieves all user fields from the ResultSet
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	
	/**
	 * inputs a single User to DataBase
	 * @param User user
	 */
	@Override
	public void ObjectToDB(User user) {
		Connection con = Server.getConnection();
		try(Statement stmt = con.createStatement()){
			int update = stmt.executeUpdate("UPDATE USER SET "+
											" userName = '" + user.getUserName() +"'"+ 
											", password = " + "'"+user.getPassword() +"'"+ 
											", isAdmin = " + user.getIsAdminValue() + 
											", counter = " + user.getCounter()+
											", status = " + user.getStatusValue() +
											" WHERE userID = "+user.getID());		//updates the DataBase with the details of a new user
			if (update == 0)															//if selected field to update did not exist then create a new field with given details
				stmt.executeUpdate("INSERT INTO USER (userName,password,status,isAdmin,counter) VALUES (" 
									+ "'" + user.getUserName() + "','" + user.getPassword() + "',"
								+ user.getStatusValue() +","+ user.getIsAdminValue() + "," + user.getCounter() + ")",Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = stmt.getGeneratedKeys();
			if( rs.next() ) 
			user.setID( rs.getInt(1) );			//inputs automated generated key from DataBase to userId
			} catch (SQLException e) { e.printStackTrace(); }
		
		}
		
	/**
	 * inputs entire User set to DataBase
	 * @param Set<User> list
	 */
	@Override
	public void putAlltoDB(Set<User> list) {		
		for (User x : list)
			ObjectToDB(x);
		
	}
	
	/**
	 * deletes a single User from DataBase
	 * @param User user
	 */
	@Override
	public void deleteFromDB(User user) {
		Connection con = Server.getConnection();
		try (Statement stmt = con.createStatement()){			
			stmt.executeQuery("DELETE FROM USER WHERE userID = " + user.getID()); //deletes a specific user from the DataBase
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Builds a User according to the given ResultSet and returns it
	 * @param ResultSet rs
	 * @return User
	 * @throws SQLException
	 */
	private User inputUser(ResultSet rs) throws SQLException {
		User user = new User();
		user.setID(rs.getInt(1));
		user.setUserName(rs.getString(2));
		user.setPassword(rs.getString(3));
		user.setStatus(User.Status.values()[rs.getInt(4)]);
		user.setAdmin(rs.getBoolean(5));
		user.setCounter(rs.getInt(6));
		return user;
	}
	/**
	 * we go throught all of the users in the group
	 * that we got and every user we add to the set 
	 * @param Group group
	 * @return Set<User>
	 */
	public Set<User> getGroupUsers(Group group) {
		UserDAO uDao = new UserDAO();
		Connection con = Server.getConnection();
		HashSet<User> set = new HashSet<User>();
		try(Statement stmt = con.createStatement())
			{					
			ResultSet rs = stmt.executeQuery("SELECT userID FROM userGroups WHERE groupID = "+group.getGroupID() );	
			while(rs.next())
				{
				User user = uDao.DBtoObject(rs.getInt(1));
				set.add(user);	
				}  
		} catch(SQLException e){ e.printStackTrace(); }
		return set;
	}
	
	/**
	 * returns all of the users in a set who are now active
	 * and have the file 
	 * @param ItemFile file
	 * @return Set<User> 
	 */
	public Set<User> getActiveFileUsers(ItemFile file)
	{
		UserDAO uDao = new UserDAO();
		Connection con = Server.getConnection();
		HashSet<User> set = new HashSet<User>();
		try(Statement stmt = con.createStatement())
			{					
			ResultSet rs = stmt.executeQuery("SELECT uf.userID FROM userfiles uf,user u WHERE u.userID = uf.userID AND u.status = 1 AND fileID = "+file.getID() );	
			while(rs.next())
				{
				User user = uDao.DBtoObject(rs.getInt(1));
				set.add(user);	
				}  
		} catch(SQLException e){ e.printStackTrace(); }
		return set;
	}
}
