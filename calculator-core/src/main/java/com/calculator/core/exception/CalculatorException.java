package com.calculator.core.exception;

@SuppressWarnings("serial")
public abstract class CalculatorException extends Exception {

	public CalculatorException(String message) {
		super(message);
	}
	
	public CalculatorException(String message, Throwable cause) {
		super(message, cause);
	}
}
