package com.calculator.web.wrappers.db.jdbcDrivers;

public class DerbyEmbeddedDriver extends Driver {
	
	public static final String DRIVER_NAME = "org.apache.derby.jdbc.EmbeddedDriver";

	@Override
	public String getDriverName() {
		return DRIVER_NAME;
	}
}
