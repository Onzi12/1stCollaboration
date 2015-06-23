package dao;

import java.util.Set;

public interface DAO<T> {

	 public T DBtoObject(int id);
	 public void ObjectToDB(T obj);
	 public T DBtoObject(String name);
	 public Set<T> getAllfromDB();
	 public void putAlltoDB(Set<T> list);
	 public void deleteFromDB(T obj);
}
