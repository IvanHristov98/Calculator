package com.calculator.web.tests.pageObjects.resources;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.contrib.java.lang.system.EnvironmentVariables;

public class EnvironmentVariablesPage {
	
	public static final String VCAP_SERVICES = "VCAP_SERVICES";
	public static final String DB_SERVICE = "postgresql";
	public static final int DB_SERVICE_INDEX = 0;
	public static final String CREDENTIALS_FIELD = "credentials";
	public static final String URI_FIELD = "uri";
	public static final String URI = "derbyClient://APP:APP@localhost:1527/calculator_db;create=true";
	
	private EnvironmentVariables environmentVariables;
	
	public EnvironmentVariablesPage(EnvironmentVariables environmentVariables) {
		this.environmentVariables = environmentVariables;
	}
	
	public void mockEnvironmentVariables() {
		JSONObject vcapServices = getVcapServices();
		
    	environmentVariables.set(VCAP_SERVICES, vcapServices.toString());
	}
	
	public JSONObject getVcapServices() {
		JSONObject vcapServices = new JSONObject();
		JSONArray dbServices = new JSONArray();
		JSONObject dbService = new JSONObject();
		JSONObject dbCredentials = new JSONObject();
		
		vcapServices.put(DB_SERVICE, dbServices);
		dbServices.put(DB_SERVICE_INDEX, dbService);
		dbService.put(CREDENTIALS_FIELD, dbCredentials);
		dbCredentials.put(URI_FIELD, URI);
		
		return vcapServices;
	}
}