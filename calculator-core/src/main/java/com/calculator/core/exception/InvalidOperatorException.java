package com.calculator.core.exception;

public final class InvalidOperatorException extends OperatorException {
	private static final long serialVersionUID = -546706523140104924L;

	public InvalidOperatorException(String message) {
		super(message);
	}
	
	public InvalidOperatorException(String message, Throwable cause) {
		super(message, cause);
	}
}
