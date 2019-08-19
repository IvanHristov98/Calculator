package com.calculator.web.wrappers.db;

public class LocalJdbcEnvironment {
	
	public static final String JDBC = "jdbc";
	public static final String JDBC_SUBPROTOCOL = "JDBC_SUBPROTOCOL";
	public static final String JDBC_HOST = "JDBC_HOST";
	public static final String JDBC_PORT = "JDBC_PORT";
	public static final String JDBC_DATABASE = "JDBC_DATABASE";
	
	public static final String JDBC_USER = "JDBC_USER";
	public static final String JDBC_PASSWORD = "JDBC_PASSWORD";
	
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
		return "postgresql";
	}
	
	public String getHost() {
		return "localhost";
	}
	
	public String getPort() {
		return "5432";
	}
	
	public String getDatabase() {
		return "calculator_db";
	}
	
	public String getUser() {
		return "calculator";
	}
	
	public String getPassword() {
		return "calculator";
	}
	
	private String getSystemEnvironment(String variable) {
		return System.getenv(variable);
	}
}
