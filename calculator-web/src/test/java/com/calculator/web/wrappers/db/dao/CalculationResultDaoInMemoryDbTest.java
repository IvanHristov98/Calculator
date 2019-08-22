package com.calculator.web.wrappers.db.dao;

import com.calculator.web.resources.representations.CalculationResult;
import com.calculator.web.wrappers.db.DbConnection;
import com.calculator.web.wrappers.db.time.TimestampTranslator;

import static com.calculator.web.wrappers.db.dao.tableRepresentations.CalculationResults.*;

import org.mockito.*;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.*;

import static org.hamcrest.Matchers.*;

import java.sql.*;
import java.time.Instant;
import java.util.Collection;

public class CalculationResultDaoInMemoryDbTest {
	
	public static final String CONNECTION_URL = "jdbc:derby:memory:calculator_db;create=true";
	public static final String CREATE_CALCULATION_RESULTS_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + " ("
			+ " " + EXPRESSION_COLUMN +" character varying(128) NOT NULL,"
			+ " " + DATE_COLUMN + " timestamp DEFAULT CURRENT TIMESTAMP NOT NULL, "
			+ " " + RESULT_COLUMN + " real,"
			+ " " + MESSAGE_COLUMN + " character varying(256)"
			+ "	 )";
	public static final String EMPTY_CALCULATION_RESULTS_TABLE_SQL = "DELETE FROM " + TABLE_NAME;
	public static final String GET_CALCULATION_RESULTS_SQL = "SELECT " + EXPRESSION_COLUMN + ", "
															+ DATE_COLUMN + ", "
															+ RESULT_COLUMN + ", "
															+ MESSAGE_COLUMN + " FROM " + TABLE_NAME;
	public static final String GET_CALCULATION_RESULT_SQL = "SELECT * FROM " + TABLE_NAME + " WHERE " + EXPRESSION_COLUMN + "=?";
	public static final String SAVE_CALCULATION_RESULT_SQL = "INSERT INTO " + TABLE_NAME
															+ "(" + EXPRESSION_COLUMN 
															+ ", " + RESULT_COLUMN
															+ ", " + MESSAGE_COLUMN + ") VALUES (?, ?, ?)";
	
	@Mock private DbConnection dbConnection;
	@Mock private TimestampTranslator timestampTranslator;
	private Instant nowAsInstant;
	private Timestamp nowAsTimestamp;
	private CalculationResultsDao calculationResultsDao;
	
	@BeforeClass
	public static void setUpDatabase() throws SQLException {
		// Create testing database and table
		Connection connection = DriverManager.getConnection(CONNECTION_URL);
		PreparedStatement statement = connection.prepareStatement(CREATE_CALCULATION_RESULTS_TABLE_SQL);
		
		statement.executeUpdate();
		connection.close();
	}
	
	@Before
	public void setUp() throws SQLException {
		initMocks(this);
		
		applyCurrentTime();
		mockTimeFields();
		
		Connection connection = getConnection();
		mockDbConnection(connection);
		
		calculationResultsDao = getCalculationResultsDao(dbConnection, timestampTranslator);
		
		emptyCalculationResultsTable();
	}
	
	@After
	public void tearDown() throws SQLException {
		tearDownDbConnection();
	}
	
	@Test
	public void verifyItemSaving() throws SQLException {
		CalculationResult item = new CalculationResult();
		item.setExpression("1+1");
		
		calculationResultsDao.save(item);
		
		ResultSet fetchedItems = fetchCalculationResult(item);
		
		assertThat(getNumberOfFetchedRows(fetchedItems), equalTo(1));
	}
	
	@Test
	public void verifyItemGetting() throws SQLException {
		CalculationResult item = new CalculationResult();
		item.setExpression("1");
		
		uploadCalculationResult(item);

		CalculationResult fetchedItem = calculationResultsDao.getItem(item.getExpression());
		
		assertThat(item.getExpression(), equalTo(fetchedItem.getExpression()));
		assertThat(item.getResult(), equalTo(fetchedItem.getResult()));
	}
	
	@Test
	public void verifyNumberOfFetchedItems() throws SQLException {
		CalculationResult itemOne = new CalculationResult();
		itemOne.setExpression("1");
		CalculationResult itemTwo = new CalculationResult();
		itemTwo.setExpression("2");
		
		uploadCalculationResult(itemOne);
		uploadCalculationResult(itemTwo);
		
		Collection<CalculationResult> fetchedItems = calculationResultsDao.getItems();
		
		assertThat(fetchedItems.size(), equalTo(2));
	}
	
	@Test
	public void verifyItemUpdate() throws SQLException {
		CalculationResult item = new CalculationResult();
		item.setExpression("1");
		item.setResult(2.0d);
		item.setDate(nowAsInstant);
		uploadCalculationResult(item);
		
		// change item
		item.setResult(1.0d);
		
		calculationResultsDao.update(item);
		
		ResultSet fetchedItems = fetchCalculationResult(item);
		
		fetchedItems.next();
		assertThat(fetchedItems.getDouble(RESULT_COLUMN), equalTo(item.getResult()));
	}
	
	@Test
	public void verifyItemDelete() throws SQLException {
		CalculationResult item = new CalculationResult();
		item.setExpression("5");
		uploadCalculationResult(item);
		
		calculationResultsDao.delete(item);
		
		ResultSet fetchedItems = fetchCalculationResult(item);
		assertThat(getNumberOfFetchedRows(fetchedItems), equalTo(0));
	}
	
	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(CONNECTION_URL);
	}
	
	private void mockDbConnection(Connection connection) {
		when(dbConnection.getConnection()).thenReturn(connection);
	}
	
	private void applyCurrentTime() {
		nowAsInstant = Instant.now();
		nowAsTimestamp = new Timestamp(nowAsInstant.toEpochMilli());
	}
	
	private void mockTimeFields() {
		when(timestampTranslator.toInstant(nowAsTimestamp)).thenReturn(nowAsInstant);
		when(timestampTranslator.toTimestamp(nowAsInstant)).thenReturn(nowAsTimestamp);
	}
	
	private void emptyCalculationResultsTable() throws SQLException {
		Connection connection = dbConnection.getConnection();
		PreparedStatement statement = connection.prepareStatement(EMPTY_CALCULATION_RESULTS_TABLE_SQL);
		statement.executeUpdate();
	}
	
	private void tearDownDbConnection() throws SQLException {
		dbConnection.getConnection().close();
	}

	private CalculationResultsDao getCalculationResultsDao(DbConnection dbConnection, TimestampTranslator timestampTranslator) {
		return new CalculationResultsDao(dbConnection, timestampTranslator);
	}
	
	private PreparedStatement getPreparedStatement(String sql) throws SQLException {
		Connection connection = dbConnection.getConnection();
		return connection.prepareStatement(sql);
	}
	
	private void uploadCalculationResult(CalculationResult item) throws SQLException {		
		PreparedStatement statement = getPreparedStatement(SAVE_CALCULATION_RESULT_SQL);
		
		statement.setString(1, item.getExpression());
		statement.setDouble(2, item.getResult());
		statement.setString(3, item.getMessage());
		
		statement.executeUpdate();
	}
	
	private ResultSet fetchCalculationResult(CalculationResult item) throws SQLException {
		PreparedStatement statement = getPreparedStatement(GET_CALCULATION_RESULT_SQL);
		statement.setString(1, item.getExpression());
		
		return statement.executeQuery();
	}
	
	private int getNumberOfFetchedRows(ResultSet result) throws SQLException {
		int numberOfRows = 0;
		
		while (result.next()) {
			numberOfRows++;
		}
		
		return numberOfRows;
	}
}
