package com.calculator.web.tests;

import org.junit.contrib.java.lang.system.EnvironmentVariables;

public class EnvironmentVariablesMocker {
	
	public static final String VCAP_SERVICES = "VCAP_SERVICES";
	public static final String SERVICE = "hanatrial";
	public static final String CREDENTIALS = "credentials";
	
	public static final String DRIVER_VARIABLE_NAME = "driver";
	public static final String URL_VARIABLE_NAME = "url";
	public static final String USER_VARIABLE_NAME = "user";
	public static final String PASSWORD_VARIABLE_NAME = "password";
	
	public static final String URL = "jdbc:derby://localhost:1527/calculator_db;create=true";
	public static final String DRIVER = "org.apache.derby.jdbc.ClientDriver";
	public static final String USER = "APP";
	public static final String PASSWORD = "APP";
	
	private EnvironmentVariables environmentVariables;
	
	public EnvironmentVariablesMocker(EnvironmentVariables environmentVariables) {
		this.environmentVariables = environmentVariables;
	}
	
	public void mockEnvironmentVariables() {
		
    	environmentVariables.set(VCAP_SERVICES, "{" + 
    			"		\"" + SERVICE + "\": [" + 
    			"			{" + 
    			"				\"" + CREDENTIALS + "\": {" + 
    			"					\"" + DRIVER_VARIABLE_NAME + "\": \"" + DRIVER + "\"," + 
    			"					\"" + URL_VARIABLE_NAME + "\": \"" + URL + "\"," + 
    			"					\"" + USER_VARIABLE_NAME + "\": \"" + USER + "\"," + 
    			"					\"" + PASSWORD_VARIABLE_NAME + "\": \"" + PASSWORD + "\"," + 
    			"				}," + 
    			"			}" + 
    			"		]" + 
    			"	}");
	}
}
