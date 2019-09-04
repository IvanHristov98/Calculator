package com.calculator.web.wrappers.db;

import javax.inject.Inject;

import org.json.JSONArray;
import org.json.JSONObject;

import com.calculator.web.wrappers.db.jdbcDrivers.Driver;
import com.calculator.web.wrappers.db.jdbcDrivers.DriverFactory;

public class LocalJdbcEnvironment {
	
	public static final String VCAP_SERVICES = "VCAP_SERVICES";
	public static final String SERVICE = "elephantsql";
	public static final int HANATRIAL_CONTENTS_INDEX = 0;
	public static final String CREDENTIALS = "credentials";
	public static final String URI = "uri";
	
	public static final int DBMS = 0;
	public static final int USERNAME = 1;
	public static final int PASSWORD = 2;
	public static final int HOST = 3;
	public static final int PORT = 4;
	public static final int DATABASE = 5;
	
	private DriverFactory driverFactory;
	
	@Inject public LocalJdbcEnvironment(DriverFactory driverFactory) {
		this.driverFactory = driverFactory;
	}
	
	public String getDatabaseUrl() {
		String[] uriParameters = getUriParameters();
		
		return "jdbc:" + uriParameters[DBMS] + "ql://" + uriParameters[HOST] + ":" + uriParameters[PORT] + "/" + uriParameters[DATABASE];
	}
	
	public String getUser() {
		return getUriParameters()[USERNAME];
	}
	
	public String getPassword() {
		return getUriParameters()[PASSWORD];
	}
	
	public String getDriverName() {
		Driver driver = driverFactory.makeDriver(getUriParameters()[DBMS]);
		return driver.getDriverName();
	}
	
	private String[] getUriParameters() {
		return getUri().split(":\\/\\/|:|@|\\/");
	}
	
	private String getUri() {
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
