package com.calculator.web.tests.pageObjects;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.sql.*;

import org.apache.derby.drda.NetworkServerControl;

public class DatabasePage {
	public static final String CREATE_CALCULATION_RESULTS_TABLE_SQL = "CREATE TABLE calculation_results ("
			+ " expression character varying(128) NOT NULL,"
			+ " date timestamp DEFAULT CURRENT TIMESTAMP NOT NULL, "
			+ " result real,"
			+ " message character varying(256)"
			+ "	 )";
	public static final String DROP_CALCULATION_RESULTS_TABLE_SQL = "DROP TABLE calculation_results";
	public static final String DATABASE_URL = "jdbc:derby://localhost:1527/calculator_db;create=true";
	public static final String DATABASE_USER = "APP";
	public static final String DATABASE_PASSWORD = "APP";
	
	public static final PrintWriter DATABASE_SERVER_OUTPUT = null;
	
	private NetworkServerControl jdbcDatabase;
	
	public DatabasePage() {
	}
	
	public void startDatabaseServer() throws Exception {
		jdbcDatabase = new NetworkServerControl((InetAddress.getByName("localhost")), 1527);
    	jdbcDatabase.start(DATABASE_SERVER_OUTPUT);	
	}
	
	public void shutDownDatabaseServer() throws Exception {
		jdbcDatabase.shutdown();
	}
	
	public void createTable() throws SQLException {
		Connection connection = getConnection();
		
		PreparedStatement statement = connection.prepareStatement(CREATE_CALCULATION_RESULTS_TABLE_SQL);		
		statement.executeUpdate();
		
		closeConnection(connection);
	}
	
	
	public void dropTable() throws SQLException {
		Connection connection = getConnection();
		
		PreparedStatement statement = connection.prepareStatement(DROP_CALCULATION_RESULTS_TABLE_SQL);
		statement.executeUpdate();
		
		closeConnection(connection);
	}
	
	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
	}
	
	private void closeConnection(Connection connection) throws SQLException {
		connection.close();
	}
}
