package com.calculator.web.wrappers.db;

public enum MockedPersistencePropertyNames {
	JDBC_URL_PROPERTY_NAME("javax.persistence.jdbc.url"),
	JDBC_USER_PROPERTY_NAME("javax.persistence.jdbc.user"),
	JDBC_PASSWORD_PROPERTY_NAME("javax.persistence.jdbc.password"),
	JDBC_DRIVER_PROPERTY_NAME("javax.persistence.jdbc.driver");
	
	
	private final String value;
	
	MockedPersistencePropertyNames(final String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
