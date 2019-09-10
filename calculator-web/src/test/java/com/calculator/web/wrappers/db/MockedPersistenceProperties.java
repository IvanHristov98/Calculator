package com.calculator.web.wrappers.db;

public enum MockedPersistenceProperties {
	JDBC_DRIVER("org.apache.derby.jdbc.EmbeddedDriver"),
	JDBC_URL("jdbc:derby:memory:calculator_db;create=true"),
	JDBC_USER(""),
	JDBC_PASSWORD("");
	
	private final String value;
	
	MockedPersistenceProperties(final String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
