package com.calculator.web.wrappers.db.dao;

import java.sql.*;

public class CalculationResultsTable {
	
	public static final String TABLE_NAME = "calculation_results";
	
	public static final String EXPRESSION_COLUMN = "expression";
	public static final String EVALUATION_COLUMN = "evaluation";
	public static final String MOMENT_COLUMN = "moment";
	public static final String MESSAGE_COLUMN = "message";
	
	public static final String CREATE_CALCULATION_RESULTS_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + " ("
			+ " " + EXPRESSION_COLUMN +" character varying(128) NOT NULL,"
			+ " " + MOMENT_COLUMN + " timestamp DEFAULT CURRENT TIMESTAMP NOT NULL, "
			+ " " + EVALUATION_COLUMN + " real,"
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
