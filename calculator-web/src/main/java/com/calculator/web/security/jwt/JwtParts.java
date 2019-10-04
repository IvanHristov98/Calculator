package com.calculator.web.security.jwt;

public enum JwtParts {
	Header(0),
	Payload(1),
	Signature(2);
	
	private final int index;
	
	JwtParts(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}
}
