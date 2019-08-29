package com.calculator.web.wrappers.db.jdbcDrivers;

public class SapHanaDriver extends Driver {
	
	public static final String DRIVER_NAME = "com.sap.db.jdbc.Driver";

	@Override
	public String getDriverName() {
		return DRIVER_NAME;
	}
}
