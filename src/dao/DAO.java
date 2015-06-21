package dao;

import java.util.Set;

public abstract class DAO<T> {

	 public abstract T DBtoObject(int id);
	 public abstract void ObjectToDB(T obj);
	 public abstract T DBtoObject(String name);
	 public abstract Set<T> getAllfromDB();
	 public abstract void putAlltoDB(Set<T> list);
	 public abstract void deleteFromDB(T obj);
}
