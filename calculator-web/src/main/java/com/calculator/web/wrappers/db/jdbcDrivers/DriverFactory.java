package com.calculator.web.wrappers.db.jdbcDrivers;

import com.calculator.web.wrappers.db.jdbcDrivers.exception.DriverException;

public class DriverFactory {
	
	public static final String POSTGRES = "postgres";
	public static final String DERBY_EMBEDDED = "derbyEmbedded";
	public static final String DERBY_CLIENT = "derbyClient";
	
	public Driver makeDriver(String dbmsName) {
		switch (dbmsName) {
		case POSTGRES:
			return new PostgreDriver();
		case DERBY_CLIENT:
			return new DerbyClientDriver();
		case DERBY_EMBEDDED:
			return new DerbyEmbeddedDriver();
		default:
			throw new DriverException("Unable to find an appropriate jdbc driver.");
		}
	}
}
