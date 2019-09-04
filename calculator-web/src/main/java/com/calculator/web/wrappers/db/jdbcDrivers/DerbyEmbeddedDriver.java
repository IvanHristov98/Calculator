package com.calculator.web.wrappers.db.jdbcDrivers;

public class DerbyEmbeddedDriver extends Driver {
	
	public static final String DRIVER_NAME = "org.apache.derby.jdbc.EmbeddedDriver";
	public static final String DB_NAME = "derby";

	@Override
	public String getDriverName() {
		return DRIVER_NAME;
	}

	@Override
	public String getDBMSNameSpecificToJdbc() {
		return DB_NAME;
	}
}
