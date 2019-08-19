package com.calculator.web.wrappers.db.exception;

@SuppressWarnings("serial")
public class DbDataTruncationException extends Exception {
	
	public DbDataTruncationException(String message) {
		super(message);
	}
	
	public DbDataTruncationException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
