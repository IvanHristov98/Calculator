package com.calculator.web.wrappers.db.dao;

import java.sql.*;

public class CalculationResultsTable {
	
	public static final String TABLE_NAME = "calculation_results";
	
	public static final String CREATE_CALCULATION_RESULTS_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + " ("
			+ " request_id int PRIMARY KEY,"
			+ " expression character varying(128) NOT NULL,"
			+ " moment timestamp DEFAULT CURRENT TIMESTAMP NOT NULL, "
			+ " evaluation real,"
			+ " message character varying(256)"
			+ "	 )";
	public static final String DROP_CALCULATION_RESULTS_TABLE_SQL = "DROP TABLE " + TABLE_NAME;
	
	private Connection connection;
	
	public CalculationResultsTable(Connection connection) {
		this.connection = connection;
	}
	
	public void create() throws SQLException {
		PreparedStatement statement = connection.prepareStatement(CREATE_CALCULATION_RESULTS_TABLE_SQL);		
		statement.executeUpdate();
	}
	
	public void drop() throws SQLException {
		PreparedStatement statement = connection.prepareStatement(DROP_CALCULATION_RESULTS_TABLE_SQL);
		statement.executeUpdate();
	}
}
