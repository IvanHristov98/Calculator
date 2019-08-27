package com.calculator.web.wrappers.db.dao;

import java.sql.*;

public class CalculationResultsTable {
	
	public static final String TABLE_NAME = "calculation_results";
	
	public static final String EXPRESSION_COLUMN = "expression";
	public static final String RESULT_COLUMN = "result";
	public static final String DATE_COLUMN = "date";
	public static final String MESSAGE_COLUMN = "message";
	
	public static final String CREATE_CALCULATION_RESULTS_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + " ("
			+ " " + EXPRESSION_COLUMN +" character varying(128) NOT NULL,"
			+ " " + DATE_COLUMN + " timestamp DEFAULT CURRENT TIMESTAMP NOT NULL, "
			+ " " + RESULT_COLUMN + " real,"
			+ " " + MESSAGE_COLUMN + " character varying(256)"
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
