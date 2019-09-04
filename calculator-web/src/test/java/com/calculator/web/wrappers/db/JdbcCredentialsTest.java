package com.calculator.web.wrappers.db;

import static org.junit.Assert.assertThat;

import org.junit.*;
import org.junit.contrib.java.lang.system.EnvironmentVariables;

import static org.hamcrest.Matchers.equalTo;

public class JdbcCredentialsTest {
	
	public static String SERVICES = "VCAP_SERVICES";
	
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
		environmentVariables.set(SERVICES, "{ \"elephantsql\": [ { \"credentials\": { \"uri\": \"" + uri + "\" } } ] }");
	}
	
	private String getStubbedUri() {
		return "postgres://username:password@host:port/database";
	}
}
