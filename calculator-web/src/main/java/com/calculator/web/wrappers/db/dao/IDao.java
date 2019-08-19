package com.calculator.web.wrappers.db.dao;

public interface IDao<T> {
	public <E> T get(E key);
	
	public void save(T element);
	
	public void update(T element);
	
	public void delete(T element);
}
