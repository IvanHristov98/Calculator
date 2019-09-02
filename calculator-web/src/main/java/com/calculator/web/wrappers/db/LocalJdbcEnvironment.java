package com.calculator.web.wrappers.db;

import javax.inject.Inject;

import org.json.JSONArray;
import org.json.JSONObject;

public class LocalJdbcEnvironment {
	
	public static final String VCAP_SERVICES = "VCAP_SERVICES";
	public static final String SERVICE = "hanatrial";
	public static final int HANATRIAL_CONTENTS_INDEX = 0;
	public static final String CREDENTIALS = "credentials";
	
	public static final String DRIVER = "driver";
	public static final String DATABASE_URL = "url";
	public static final String USER = "user";
	public static final String PASSWORD = "password";
	
	@Inject public LocalJdbcEnvironment() {
	}
	
	public String getDatabaseUrl() {
		return getCredentials().getString(DATABASE_URL);
	}
	
	public String getUser() {
		return getCredentials().getString(USER);
	}
	
	public String getPassword() {
		return getCredentials().getString(PASSWORD);
	}
	
	public String getDriverName() {
		return getCredentials().getString(DRIVER);
	}
	
	private JSONObject getCredentials() {
		JSONObject vcapServices = new JSONObject(getEnvironmentVariable(VCAP_SERVICES));
		JSONArray hanatrial = vcapServices.getJSONArray(SERVICE);
		JSONObject hanatrialContents = hanatrial.getJSONObject(HANATRIAL_CONTENTS_INDEX);
		return hanatrialContents.getJSONObject(CREDENTIALS);
	}
	
	private String getEnvironmentVariable(String variableName) {
		return System.getenv(variableName);
	}
}
