package com.calculator.web.wrapper.exception;

@SuppressWarnings("serial")
public class WebCalculatorException extends Exception {
	
	public WebCalculatorException(String message) {
		super(message);
	}
	
	public WebCalculatorException(String message, Throwable cause) {
		super(message, cause);
	}
}
