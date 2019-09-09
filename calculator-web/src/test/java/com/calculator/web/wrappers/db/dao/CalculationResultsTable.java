package com.calculator.web.wrappers.db.dao;

import java.sql.*;

public class CalculationResultsTable {
	
	public static final String TABLE_NAME = "calculation_results";
	
	public static final String CREATE_CALCULATION_RESULTS_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + " ("
			+ " request_id int PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
			+ " expression character varying(128) NOT NULL,"
			+ " moment timestamp DEFAULT CURRENT TIMESTAMP NOT NULL, "
			+ " evaluation real,"
			+ " message character varying(256)"
			+ "	 )";
	public static final String DROP_CALCULATION_RESULTS_TABLE_SQL = "DROP TABLE " + TABLE_NAME;
	public static final String RESTART_AUTO_INCREMENTATION = "ALTER TABLE " + TABLE_NAME + " ALTER COLUMN request_id RESTART WITH 1";
	
	private Connection connection;
	
	public CalculationResultsTable(Connection connection) {
		this.connection = connection;
	}
	
	public void create() throws SQLException {
		executePreparedStatement(CREATE_CALCULATION_RESULTS_TABLE_SQL);
	}
	
	public void drop() throws SQLException {
		executePreparedStatement(DROP_CALCULATION_RESULTS_TABLE_SQL);
	}
	
	public void restartAutoIncrementation() throws SQLException {
		executePreparedStatement(RESTART_AUTO_INCREMENTATION);
	}
	
	private void executePreparedStatement(String sql) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.executeUpdate();
	}
}
