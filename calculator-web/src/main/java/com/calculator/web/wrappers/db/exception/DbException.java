package com.calculator.web.wrappers.db.exception;

@SuppressWarnings("serial")
public class DbException extends Exception {
	
	public DbException(String message) {
		super(message);
	}
	
	public DbException(String message, Throwable cause) {
		super(message, cause);
	}
}
