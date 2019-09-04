package com.calculator.web.wrappers.db;

public enum UriComponents {
	DBMS(0),
	USERNAME(1),
	PASSWORD(2),
	HOST(3),
	PORT(4),
	DATABASE(5);
	
	private final int value;
	
	UriComponents(final int value) {
		this.value = value;
	}
	
	public int getIndex() {
		return value;
	}
}
