package dao;

import java.util.Set;

public interface DAO<T> {

	/**
	 * withdraws data from the DB and stores it in the Local data structures <br>
	 * data is withdrawn according to matching id
	 * @param id the object id
	 * @return the new Object
	 */
	 public T DBtoObject(int id);
	 
	 /**
		 * withdraws data from the DB and stores it in the Local data structures<br> 
		 * data is withdrawn according to matching name
		 * @param id
		 * @return the new Object
		 */
	 public T DBtoObject(String name);
	 
	 /**
	  * inserts data from the local data structures to the DB
	  * @param <T> 
	  * 
	  */
	 public void ObjectToDB(T obj);
	 
	 /**
	  * withdraws all Objects from DB into local data structure
	  * 
	  * @return A Set of all Objects
	  */
	 public Set<T> getAllfromDB();
	 
	 /**
	  * inserts a set of Objects into the DB
	  * @param list - a list of objects
	  */
	 public void putAlltoDB(Set<T> list);
	 
	 /**
	  * deletes a single Object from DB
	  * @param <T>
	  */
	 public void deleteFromDB(T obj);
}
