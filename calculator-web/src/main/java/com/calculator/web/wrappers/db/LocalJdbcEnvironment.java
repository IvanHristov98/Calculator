package com.calculator.web.wrappers.db;

import javax.inject.Inject;

import com.calculator.web.wrappers.db.jdbcDrivers.Driver;
import com.calculator.web.wrappers.db.jdbcDrivers.DriverFactory;

public class LocalJdbcEnvironment {
	
	public static final String DATABASE = "CALCULATOR_DATABASE";
	public static final String JDBC_URL = "CALCULATOR_JDBC_URL";
	public static final String JDBC_USER = "CALCULATOR_JDBC_USER";
	public static final String JDBC_PASSWORD = "CALCULATOR_JDBC_PASSWORD";
	
	private DriverFactory driverFactory;
	
	@Inject public LocalJdbcEnvironment(DriverFactory driverFactory) {
		this.driverFactory = driverFactory;
	}
	
	public String getDatabaseUrl() {
		return 	getJvmProperty(JDBC_URL);
	}
	
	public String getUser() {
		return getJvmProperty(JDBC_USER);
	}
	
	public String getPassword() {
		return getJvmProperty(JDBC_PASSWORD);
	}
	
	public String getDriverName() {
		return getDriver().getDriverName();
	}
	
	public Driver getDriver() {
		return driverFactory.makeDriver(getJvmProperty(DATABASE));
	}
	
	private String getJvmProperty(String variableName) {
		return System.getenv(variableName);
	}
}
