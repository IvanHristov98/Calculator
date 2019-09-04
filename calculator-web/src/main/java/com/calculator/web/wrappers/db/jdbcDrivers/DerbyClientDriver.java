package com.calculator.web.wrappers.db.jdbcDrivers;

public class DerbyClientDriver extends Driver {

	public static final String DRIVER_NAME = "org.apache.derby.jdbc.ClientDriver";
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
