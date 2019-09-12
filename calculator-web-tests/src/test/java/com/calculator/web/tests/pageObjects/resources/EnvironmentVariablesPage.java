package com.calculator.web.tests.pageObjects.resources;

import org.junit.contrib.java.lang.system.EnvironmentVariables;

public class EnvironmentVariablesPage {
	
	public static final String VCAP_SERVICES = "VCAP_SERVICES";
	public static final String SERVICE = "elephantsql";
	public static final String CREDENTIALS = "credentials";
	
	public static final String URI_VARIABLE_NAME = "uri";
	
	public static final String URI = "derbyClient://APP:APP@localhost:1527/calculator_db;create=true";
	
	private EnvironmentVariables environmentVariables;
	
	public EnvironmentVariablesPage(EnvironmentVariables environmentVariables) {
		this.environmentVariables = environmentVariables;
	}
	
	public void mockEnvironmentVariables() {
		
    	environmentVariables.set(VCAP_SERVICES, "{" + 
    			"		\"" + SERVICE + "\": [" + 
    			"			{" + 
    			"				\"" + CREDENTIALS + "\": {" + 
    			"					\"" + URI_VARIABLE_NAME + "\": \"" + URI + "\"" + 
    			"				}," + 
    			"			}" + 
    			"		]" + 
    			"	}");
	}
}