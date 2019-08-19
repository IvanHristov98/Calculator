package com.calculator.web.wrappers.db.dao;

import com.calculator.web.wrappers.db.exception.DbDataTruncationException;

public interface IDao<T> {
	
	public <E> T get(E key);
	
	public T[] getItems();
	
	public void save(T item) throws DbDataTruncationException;
	
	public void update(T item) throws DbDataTruncationException;
	
	public void delete(T item) throws DbDataTruncationException;
}
