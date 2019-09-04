package com.calculator.web.wrappers.db.jdbcDrivers;

import com.calculator.web.wrappers.db.jdbcDrivers.exception.DriverException;

public class DriverFactory {
	
	public static final String POSTGRE_DB = "postgres";
	public static final String DERBY_EMBEDDED_DB = "derbyEmbedded";
	public static final String DERBY_CLIENT_DB = "derbyClient";
	
	public Driver makeDriver(String dbName) {
		switch (dbName) {
		case POSTGRE_DB:
			return new PostgreDriver();
		case DERBY_CLIENT_DB:
			return new DerbyClientDriver();
		case DERBY_EMBEDDED_DB:
			return new DerbyEmbeddedDriver();
		default:
			throw new DriverException("Unable to find an appropriate jdbc driver.");
		}
	}
}
