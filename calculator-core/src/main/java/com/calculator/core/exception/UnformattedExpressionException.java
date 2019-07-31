package com.calculator.core.exception;

public class UnformattedExpressionException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UnformattedExpressionException(String message) {
		super(message);
	}
	
	public UnformattedExpressionException(String message, Throwable cause) {
		super(message, cause);
	}
}
