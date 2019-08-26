package com.calculator.web.wrappers.db.jdbcDrivers;

public class DerbyClientDriver extends Driver {

	public static final String DRIVER_NAME = "org.apache.derby.jdbc.ClientDriver";
	
	@Override
	public String getDriverName() {
		return DRIVER_NAME;
	}
}
