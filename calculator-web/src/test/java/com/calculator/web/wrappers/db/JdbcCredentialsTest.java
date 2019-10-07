package com.calculator.web.wrappers.db;

import static org.junit.Assert.assertThat;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.*;
import org.junit.contrib.java.lang.system.EnvironmentVariables;

import static org.hamcrest.Matchers.equalTo;

public class JdbcCredentialsTest {
	
	public static final String VCAP_SERVICES = "VCAP_SERVICES";
	public static final String DB_SERVICE = "postgresql";
	public static final int DB_SERVICE_INDEX = 0;
	public static final String CREDENTIALS_FIELD = "credentials";
	public static final String URI_FIELD = "uri";
	
	@Rule public EnvironmentVariables environmentVariables = new EnvironmentVariables();
	
	@Before
	public void setUp() {
		String uri = getStubbedUri();
		setUpServicesServices(uri);
	}
	
	@Test
	public void verifyUriExtraction() {
		JdbcCredentials credentials = new JdbcCredentials();
		
		assertThat(credentials.getUri(), equalTo(getStubbedUri()));
	}
	
	private void setUpServicesServices(String uri) {
		JSONObject vcapServices = new JSONObject();
		JSONArray dbServices = new JSONArray();
		JSONObject dbService = new JSONObject();
		JSONObject credentials = new JSONObject();
		
		vcapServices.put(DB_SERVICE, dbServices);
		dbServices.put(DB_SERVICE_INDEX, dbService);
		dbService.put(CREDENTIALS_FIELD, credentials);
		credentials.put(URI_FIELD, uri);

		environmentVariables.set(VCAP_SERVICES, vcapServices.toString());
	}
	
	private String getStubbedUri() {
		return "postgres://username:password@host:port/database";
	}
}
