package com.calculator.core.exception;

@SuppressWarnings("serial")
public final class InvalidOperatorException extends OperatorException {

	public InvalidOperatorException(String message) {
		super(message);
	}
	
	public InvalidOperatorException(String message, Throwable cause) {
		super(message, cause);
	}
}
