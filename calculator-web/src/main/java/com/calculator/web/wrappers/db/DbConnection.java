package com.calculator.web.wrappers.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection implements IDbConnection {

	private static volatile DbConnection instance;
	
	private LocalJdbcEnvironment jdbcEnvironment;
	private Connection connection;
	
	public static synchronized DbConnection getInstance(LocalJdbcEnvironment jdbcEnvironment) throws SQLException {
		if (instance == null) {
			instance = new DbConnection(jdbcEnvironment);
		}
		
		return instance;
	}
	
	private DbConnection(LocalJdbcEnvironment jdbcEnvironment) throws SQLException {
		this.jdbcEnvironment = jdbcEnvironment;
		
		loadConnection(this.jdbcEnvironment);
	}
	
	private void loadConnection(LocalJdbcEnvironment jdbcEnvironment) throws SQLException {
		org.postgresql.Driver.isRegistered();
		
		this.connection = DriverManager.getConnection(
				jdbcEnvironment.getDatabaseUrl(),
				jdbcEnvironment.getUser(),
				jdbcEnvironment.getPassword()
				);
	}
	
	@Override
	public Connection getConnection() {
		return connection;
	}
}
