package com.calculator.core.exception;

@SuppressWarnings("serial")
public class OperatorMisplacementException extends OperatorException {

	public OperatorMisplacementException(String message) {
		super(message);
	}
	
	public OperatorMisplacementException(String message, Throwable cause) {
		super(message, cause);
	}
}
