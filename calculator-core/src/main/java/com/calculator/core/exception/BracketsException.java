package com.calculator.core.exception;

@SuppressWarnings("serial")
public final class BracketsException extends OperatorMisplacementException {

	public BracketsException(String message) {
		super(message);
	}
	
	public BracketsException(String message, Throwable cause) {
		super(message, cause);
	}
}