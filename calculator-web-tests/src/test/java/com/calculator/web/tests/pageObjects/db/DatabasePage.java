package com.calculator.web.tests.pageObjects.db;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.sql.*;

import org.apache.derby.drda.NetworkServerControl;
import org.dbunit.Assertion;
import org.dbunit.DatabaseUnitException;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.CachedDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;

public class DatabasePage {
	public static final String TABLE_NAME = "calculation_results";
	public static final String CREATE_CALCULATION_RESULTS_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + " ("
			+ " expression character varying(128) NOT NULL,"
			+ " moment timestamp DEFAULT CURRENT TIMESTAMP NOT NULL, "
			+ " evaluation real,"
			+ " message character varying(256)"
			+ "	)";
	public static final String DROP_CALCULATION_RESULTS_TABLE_SQL = "DROP TABLE calculation_results";
	
	public static final String DATABASE_URL = "jdbc:derby://localhost:1527/calculator_db;create=true";
	public static final String DATABASE_DRIVER = "org.apache.derby.jdbc.ClientDriver";
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
	
	public void createSchema() throws SQLException {
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
	
	public void useDataSet(String fileName) throws Exception {
		IDatabaseTester databaseTester = new JdbcDatabaseTester(DATABASE_DRIVER, DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
        
        IDataSet dataSet = getDataSet(fileName);
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();
	}
	
	public void compareActualToExpectedTable(String expecteDataSetFileName) throws SQLException, DatabaseUnitException {
		IDatabaseConnection connection = getDatabaseConnection();
		
		IDataSet databaseDataSet = connection.createDataSet(new String[] {TABLE_NAME});
		IDataSet actualDataSet = new CachedDataSet(databaseDataSet);
        IDataSet expectedDataSet = getDataSet(expecteDataSetFileName);
        
        ITable expectedTable = expectedDataSet.getTable(TABLE_NAME);
        ITable actualTable = actualDataSet.getTable(TABLE_NAME);
        ITable filteredActualTable = DefaultColumnFilter.includedColumnsTable(actualTable, expectedTable.getTableMetaData().getColumns());
        
        verifyTablesEquality(expectedTable, filteredActualTable);
	}
	
	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
	}
	
	private void closeConnection(Connection connection) throws SQLException {
		connection.close();
	}
	
	private IDataSet getDataSet(String dataSetFileName) throws DataSetException {
		return new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(dataSetFileName));
	}
	
	private IDatabaseConnection getDatabaseConnection() throws DatabaseUnitException, SQLException {
		Connection connection = getConnection();
		return new DatabaseConnection(connection);
	}
	
	private void verifyTablesEquality(ITable expectedTable, ITable actualTable) throws DatabaseUnitException {
		Assertion.assertEquals(expectedTable, actualTable);
	}
}
