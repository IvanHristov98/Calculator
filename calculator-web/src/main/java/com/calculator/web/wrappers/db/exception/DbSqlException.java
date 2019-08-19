package com.calculator.web.wrappers.db.exception;

@SuppressWarnings("serial")
public class DbSqlException extends RuntimeException {
	
	public DbSqlException(String message) {
		super(message);
	}
	
	public DbSqlException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
