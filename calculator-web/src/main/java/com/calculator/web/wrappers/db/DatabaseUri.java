package com.calculator.web.wrappers.db;

import com.calculator.web.wrappers.db.jdbcDrivers.Driver;
import com.calculator.web.wrappers.db.jdbcDrivers.DriverFactory;

public class DatabaseUri {
	
	private JdbcCredentials jdbcCredentials;
	private DriverFactory driverFactory;
	
	public DatabaseUri(JdbcCredentials jdbcCredentials, DriverFactory driverFactory) {
		this.jdbcCredentials = jdbcCredentials;
		this.driverFactory = driverFactory;
	}
	
	public String getDatabaseUrl() {
		String[] uriParameters = getUriParameters();
		Driver driver = getDriver();
		
		String databaseName = driver.getDBMSNameSpecificToJdbc();
		String hostName = uriParameters[UriComponents.HOST.getIndex()];
		String port = uriParameters[UriComponents.PORT.getIndex()];
		String database = uriParameters[UriComponents.DATABASE.getIndex()];
		
		return "jdbc:" + databaseName + "://" + hostName + ":" + port + "/" + database;
	}
	
	public String getUser() {
		return getUriParameters()[UriComponents.USERNAME.getIndex()];
	}
	
	public String getPassword() {
		return getUriParameters()[UriComponents.PASSWORD.getIndex()];
	}
	
	public String getDriverName() {
		Driver driver = getDriver();
		return driver.getDriverName();
	}
	
	private String[] getUriParameters() {
		return jdbcCredentials.getUri().split(":\\/\\/|:|@|\\/");
	}
	
	private Driver getDriver() {
		return driverFactory.makeDriver(getUriParameters()[UriComponents.DBMS.getIndex()]);
	}
}
