package com.calculator.cli.wrappers.calculator.exception;

@SuppressWarnings("serial")
public class CliCalculatorException extends Exception {
	
	public CliCalculatorException(String message) {
		super(message);
	}
	
	public CliCalculatorException(String message, Throwable cause) {
		super(message, cause);
	}
}
