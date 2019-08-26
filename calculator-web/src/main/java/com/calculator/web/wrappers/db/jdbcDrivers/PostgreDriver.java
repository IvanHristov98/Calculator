package com.calculator.web.wrappers.db.jdbcDrivers;

public class PostgreDriver extends Driver {
	
	public static final String DRIVER_NAME = "org.postgresql.Driver";

	@Override
	public String getDriverName() {
		return DRIVER_NAME;
	}
}
