package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import server.Server;

public class UserDAO extends DAO<User> {
	
	private static Connection con;
	
	@Override
	public Set<User> getAllfromDB() {
		con = Server.getConnection();
		HashSet<User> set = new HashSet<User>();
		try(Statement stmt = con.createStatement()){
			ResultSet rs = stmt.executeQuery("SELECT * FROM USER");
			while(rs.next()){
				User user = new User();
				user.setID(rs.getInt(1));
				user.setUserName(rs.getString(2));
				user.setPassword(rs.getString(3));
				user.setStatus(User.Status.values()[rs.getInt(4)]);
				user.setAdmin(rs.getBoolean(5));
				user.setCounter(rs.getInt(6));
				set.add(user);
			}  
		} catch(SQLException e){ e.printStackTrace(); }
		return set;
	}

	@Override
	public User DBtoObject(int id) {
		con = Server.getConnection();
		User user = new User();
		try ( Statement stmt = con.createStatement()){
			ResultSet rs = stmt.executeQuery("SELECT * FROM USER WHERE userID = " + id);
			if (rs.next()){
				user.setID(rs.getInt(1));
				user.setUserName(rs.getString(2));
				user.setPassword(rs.getString(3));
				user.setStatus(User.Status.values()[rs.getInt(4)]);
				user.setAdmin(rs.getBoolean(5));
				user.setCounter(rs.getInt(6));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public void ObjectToDB(User user) {
		con = Server.getConnection();
		try(Statement stmt = con.createStatement()){
			int update = stmt.executeUpdate("UPDATE USER SET userID = "+ user.getID() +
											", userName = " + user.getUserName() + 
											", password = " + user.getPassword() + 
											", isAdmin = " + user.isAdmin() + 
											", counter = " + user.getCounter());
			if (update == 0)
				stmt.executeUpdate("INSERT INTO USER (userID,userName,password,status,isAdmin,counter) VALUES (" 
									+ user.getID() + "," + user.getUserName() + "," + user.getPassword() + ","
									+ user.isAdmin() + "," + user.getCounter() + ")",Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = stmt.getGeneratedKeys();
			if( rs.next() ) 
			user.setID( rs.getInt(1) );
			} catch (SQLException e) { e.printStackTrace(); }
		
		}
		
	@Override
	public User DBtoObject(String userName) {
		con = Server.getConnection();
		User user = null;
		try (Statement stmt = con.createStatement()) {
			ResultSet rs = stmt.executeQuery("SELECT * FROM USER WHERE userName = " + userName);
			if (rs.next()){
				user = new User();
				user.setID(rs.getInt(1));
				user.setUserName(rs.getString(2));
				user.setPassword(rs.getString(3));
				user.setStatus(User.Status.values()[rs.getInt(4)]);
				user.setAdmin(rs.getBoolean(5));
				user.setCounter(rs.getInt(6));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public void putAlltoDB(Set<User> list) {
		for (User x : list)
			ObjectToDB(x);
		
	}

	@Override
	public void deleteFromDB(User user) {
		con = Server.getConnection();
		try (Statement stmt = con.createStatement()){
			stmt.executeQuery("DELETE FROM USER WHERE userID = " + user.getID());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
