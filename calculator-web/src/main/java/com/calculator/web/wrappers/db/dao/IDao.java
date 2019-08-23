package com.calculator.web.wrappers.db.dao;

import java.sql.SQLException;
import java.util.List;

public interface IDao<T, E> {
	
	public T getItem(E key) throws SQLException;
	
	public List<T> getItems() throws SQLException;
	
	public void save(T item) throws SQLException;
	
	public void update(T item) throws SQLException;
	
	public void delete(T item) throws SQLException;
}
