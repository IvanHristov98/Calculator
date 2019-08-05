package com.calculator.core.exception;

@SuppressWarnings("serial")
public class EmptyExpressionException extends CalculatorException {

	public EmptyExpressionException(String message) {
		super(message);
	}
	
	public EmptyExpressionException(String message, Throwable cause) {
		super(message, cause);
	}
}
