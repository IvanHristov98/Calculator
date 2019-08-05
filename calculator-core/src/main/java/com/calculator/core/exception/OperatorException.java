package com.calculator.core.exception;

@SuppressWarnings("serial")
public abstract class OperatorException extends CalculatorException {

	public OperatorException(String message) {
		super(message);
	}
	
	public OperatorException(String message, Throwable cause) {
		super(message, cause);
	}
}
