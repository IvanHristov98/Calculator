package com.calculator.core.exception;

public class OperatorMisplacementException extends OperatorException {
	private static final long serialVersionUID = -7066236372174568388L;

	public OperatorMisplacementException(String message) {
		super(message);
	}
	
	public OperatorMisplacementException(String message, Throwable cause) {
		super(message, cause);
	}
}
