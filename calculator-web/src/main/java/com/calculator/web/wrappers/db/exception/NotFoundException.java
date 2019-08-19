package com.calculator.web.wrappers.db.exception;

@SuppressWarnings("serial")
public class NotFoundException extends DbException {

	public NotFoundException(String message) {
		super(message);
	}
	
	public NotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
