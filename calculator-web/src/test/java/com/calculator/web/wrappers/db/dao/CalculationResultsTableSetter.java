package com.calculator.web.wrappers.db.dao;

import static com.calculator.web.wrappers.db.dao.tableRepresentations.CalculationResults.*;

import java.sql.*;

public class CalculationResultsTableSetter {
	
	public static final String CREATE_CALCULATION_RESULTS_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + " ("
			+ " " + EXPRESSION_COLUMN +" character varying(128) NOT NULL,"
			+ " " + DATE_COLUMN + " timestamp DEFAULT CURRENT TIMESTAMP NOT NULL, "
			+ " " + RESULT_COLUMN + " real,"
			+ " " + MESSAGE_COLUMN + " character varying(256)"
			+ "	 )";
	public static final String DROP_CALCULATION_RESULTS_TABLE_SQL = "DROP TABLE " + TABLE_NAME;
	
	private Connection connection;
	
	public CalculationResultsTableSetter(Connection connection) {
		this.connection = connection;
	}
	
	public void createTable() throws SQLException {
		PreparedStatement statement = connection.prepareStatement(CREATE_CALCULATION_RESULTS_TABLE_SQL);		
		statement.executeUpdate();
	}
	
	public void dropTable() throws SQLException {
		PreparedStatement statement = connection.prepareStatement(DROP_CALCULATION_RESULTS_TABLE_SQL);
		statement.executeUpdate();
	}
}
