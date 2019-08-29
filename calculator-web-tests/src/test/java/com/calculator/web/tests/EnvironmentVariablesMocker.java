package com.calculator.web.tests;

import org.junit.contrib.java.lang.system.EnvironmentVariables;

public class EnvironmentVariablesMocker {
	
	public static final String JDBC_URL_VARIABLE_NAME = "CALCULATOR_JDBC_URL";
	public static final String DATABASE_VARIABLE_NAME = "CALCULATOR_DATABASE";
	public static final String JDBC_USER_VARIABLE_NAME = "CALCULATOR_JDBC_USER";
	public static final String JDBC_PASSWORD_VARIABLE_NAME = "CALCULATOR_JDBC_PASSWORD";
	
	public static final String JDBC_URL = "jdbc:derby://localhost:1527/calculator_db;create=true";
	public static final String DATABASE = "derbyClient";
	public static final String USER = "APP";
	public static final String PASSWORD = "APP";
	
	private EnvironmentVariables environmentVariables;
	
	public EnvironmentVariablesMocker(EnvironmentVariables environmentVariables) {
		this.environmentVariables = environmentVariables;
	}
	
	public void mockEnvironmentVariables() {
		environmentVariables.set(JDBC_URL_VARIABLE_NAME, JDBC_URL);
    	environmentVariables.set(DATABASE_VARIABLE_NAME, DATABASE);
    	environmentVariables.set(JDBC_USER_VARIABLE_NAME, USER);
    	environmentVariables.set(JDBC_PASSWORD_VARIABLE_NAME, PASSWORD);
	}
}
