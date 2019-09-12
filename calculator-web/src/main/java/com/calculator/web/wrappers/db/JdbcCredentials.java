package com.calculator.web.wrappers.db;

import org.json.JSONArray;
import org.json.JSONObject;

public class JdbcCredentials {
	
	public static final String VCAP_SERVICES = "VCAP_SERVICES";
	public static final String SERVICE = "postgresql";
	public static final int HANATRIAL_CONTENTS_INDEX = 0;
	public static final String CREDENTIALS = "credentials";
	public static final String URI = "uri";
	
	public JdbcCredentials() {
	}
	
	public String getUri() {
		return getCredentials().getString(URI);
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
