package com.calculator.web.wrappers.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection implements IDbConnection {

	private static volatile DbConnection instance;
	private Connection connection;
	
	public static synchronized DbConnection getInstance() throws SQLException {
		if (instance == null) {
			instance = new DbConnection();
		}
		
		return instance;
	}
	
	private DbConnection() throws SQLException {
		org.postgresql.Driver.isRegistered();
		this.connection = DriverManager.getConnection("jdbc:postgresql://localhost/calculator_db", "calculator", "calculator");
	}
	
	@Override
	public Connection getConnection() {
		return connection;
	}
	
}
