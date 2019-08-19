package com.calculator.web.wrappers.db.dao;

import java.util.Collection;

import com.calculator.web.wrappers.db.exception.DbException;

public interface IDao<T, E> {
	
	public T getItem(E key) throws DbException;
	
	public Collection<T> getItems() throws DbException;
	
	public void save(T item) throws DbException;
	
	public void update(T item) throws DbException;
	
	public void delete(T item) throws DbException;
}
