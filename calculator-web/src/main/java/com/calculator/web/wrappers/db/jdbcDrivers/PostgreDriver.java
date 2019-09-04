package com.calculator.web.wrappers.db.jdbcDrivers;

public class PostgreDriver extends Driver {
	
	public static final String DRIVER_NAME = "org.postgresql.Driver";
	public static final String DB_NAME = "postgresql";

	@Override
	public String getDriverName() {
		return DRIVER_NAME;
	}

	@Override
	public String getDBMSNameSpecificToJdbc() {
		return DB_NAME;
	}
}
