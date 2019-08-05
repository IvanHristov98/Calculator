package com.calculator.core.exception;

@SuppressWarnings("serial")
public class NumberMisplacementException extends CalculatorException {

	public NumberMisplacementException(String message) {
		super(message);
	}
	
	public NumberMisplacementException(String message, Throwable cause) {
		super(message, cause);
	}
}
