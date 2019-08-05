package com.calculator.core.exception;

@SuppressWarnings("serial")
public class UnformattedExpressionException extends RuntimeException {

	public UnformattedExpressionException(String message) {
		super(message);
	}
	
	public UnformattedExpressionException(String message, Throwable cause) {
		super(message, cause);
	}
}
