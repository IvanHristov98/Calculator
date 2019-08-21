package com.calculator.web.wrappers.db;

public class LocalJdbcEnvironment {
	
	public static final String JDBC = "jdbc";
	public static final String JDBC_SUBPROTOCOL = "CALCULATOR_JDBC_SUBPROTOCOL";
	public static final String JDBC_HOST = "CALCULATOR_JDBC_HOST";
	public static final String JDBC_PORT = "CALCULATOR_JDBC_PORT";
	public static final String JDBC_DATABASE = "CALCULATOR_JDBC_DATABASE";
	
	public static final String JDBC_USER = "CALCULATOR_JDBC_USER";
	public static final String JDBC_PASSWORD = "CALCULATOR_JDBC_PASSWORD";
	
	public String getDatabaseUrl() {
		return 	JDBC + ":" +
				getSubprotocol() + "://" +
				getHost() + ":" +
				getPort() + "/" +
				getDatabase();
	}
	
	public LocalJdbcEnvironment() {
	}
	
	public String getSubprotocol() {
		return getSystemEnvironment(JDBC_SUBPROTOCOL);
	}
	
	public String getHost() {
		return getSystemEnvironment(JDBC_HOST);
	}
	
	public String getPort() {
		return getSystemEnvironment(JDBC_PORT);
	}
	
	public String getDatabase() {
		return getSystemEnvironment(JDBC_DATABASE);
	}
	
	public String getUser() {
		return getSystemEnvironment(JDBC_USER);
	}
	
	public String getPassword() {
		return getSystemEnvironment(JDBC_PASSWORD);
	}
	
	private String getSystemEnvironment(String variableName) {
		return System.getenv(variableName);
	}
}
