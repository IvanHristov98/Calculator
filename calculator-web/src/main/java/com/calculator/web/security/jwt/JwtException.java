package com.calculator.web.security.jwt;

@SuppressWarnings("serial")
public class JwtException extends Exception {
	
	public JwtException(String message) {
		super(message);
	}
	
	public JwtException(String message, Throwable cause) {
		super(message, cause);
	}
}
