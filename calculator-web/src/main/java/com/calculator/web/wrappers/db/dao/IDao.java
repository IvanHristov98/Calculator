package com.calculator.web.wrappers.db.dao;

public interface IDao<T> {
	
	public <E> T get(E key);
	
	public T[] getItems();
	
	public void save(T item);
	
	public void update(T item);
	
	public void delete(T item);
}
