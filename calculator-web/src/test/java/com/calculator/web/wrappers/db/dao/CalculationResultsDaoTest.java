package com.calculator.web.wrappers.db.dao;

import com.calculator.web.wrappers.db.dao.dbMappers.CalculationResult;

import static com.calculator.web.wrappers.db.dao.tableRepresentations.CalculationResults.*;

import org.junit.*;
import org.mockito.Mock;

import java.sql.*;
import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.dbunit.*;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.*;
import org.dbunit.dataset.*;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

public class CalculationResultsDaoTest {
	
	public static final String JDBC_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
	public static final String JDBC_URL = "jdbc:derby:memory:calculator_db;create=true";
	public static final String JDBC_USER = "";
	public static final String JDBC_PASSWORD = "";
	
	public static final String JDBC_URL_PROPERTY_NAME = "javax.persistence.jdbc.url";
	public static final String JDBC_USER_PROPERTY_NAME = "javax.persistence.jdbc.user";
	public static final String JDBC_PASSWORD_PROPERTY_NAME = "javax.persistence.jdbc.password";
	public static final String JDBC_DRIVER_PROPERTY_NAME = "javax.persistence.jdbc.driver";
	
	@Mock private Timestamp mockedTimestamp;
	private EntityManager entityManager;
	private CalculationResultsDao calculationResultsDao;
	
	@BeforeClass
	public static void setUpClass() throws SQLException {
		Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
		
		CalculationResultsTableSetter dbSetter = new CalculationResultsTableSetter(connection);
		dbSetter.createTable();
		
		connection.close();
	}
	
	@AfterClass
	public static void tearDownClass() throws SQLException {
		Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
		
		CalculationResultsTableSetter dbSetter = new CalculationResultsTableSetter(connection);
		dbSetter.dropTable();
		
		connection.close();
	}
	
    @Before
    public void setUp() throws Exception {
    	initMocks(this);
    	
    	applyDataSet("/dbUnit/CalculationResultsDao#setUp.xml");
    	setUpEntityManger();
    	setUpCalculationResultsDao(entityManager);
    }
    
    @After
    public void tearDown() {
    	tearDownEntityManager();
    }
    
	@Test
	public void verifyItemFinding() throws Exception {
		applyDataSet("/dbUnit/CalculationResultsDao#verifyItemFinding.xml");
		
		String expression = "1+1";
		CalculationResult result = calculationResultsDao.getItem(expression);
		
		assertThat(result, notNullValue());
		assertThat(result.getExpression(), equalTo(expression));
	}
	
	@Test
	public void verifyNumberWhenGettingItems() throws Exception {
		applyDataSet("/dbUnit/CalculationResultsDao#verifyNumberWhenGettingItems.xml");
		final int totalRecordsInTable = 2;
		
		List<CalculationResult> result = calculationResultsDao.getItems();
		
		assertThat(result.size(), equalTo(totalRecordsInTable));
	}
	
	@Test
	public void verifyItemSaving() throws SQLException, DatabaseUnitException {
		CalculationResult item = new CalculationResult();
		item.setExpression("1+1");
		item.setDate(mockedTimestamp);
		item.setResult(2.0d);
		
		calculationResultsDao.save(item);
		
		compareActualToCurrentTable("/dbUnit/expected-CalculationResultsDao#verifyItemSaving.xml");
	}
	
	@Test
	public void verifyItemUpdating() throws Exception {
		applyDataSet("/dbUnit/CalculationResultsDao#verifyItemUpdating.xml");
		
		CalculationResult item = new CalculationResult();
		item.setExpression("1+1");
		item.setDate(mockedTimestamp);
		item.setResult(2.0d);
		
		calculationResultsDao.update(item);
		
		compareActualToCurrentTable("/dbUnit/expected-CalculationResultsDao#verifyItemUpdating.xml");
	}
	
	@Test
	public void verifyItemDeletion() throws Exception {
		applyDataSet("/dbUnit/CalculationResultsDao#verifyItemDeletion.xml");
		
		CalculationResult item = new CalculationResult();
		item.setExpression("1+1");
		item.setDate(mockedTimestamp);
		
		calculationResultsDao.delete(item);
		
		compareActualToCurrentTable("/dbUnit/expected-CalculationResultsDao#verifyItemDeletion.xml");
	}
	
	private void applyDataSet(String dataSetFileName) throws Exception {
		IDatabaseTester databaseTester = new JdbcDatabaseTester(JDBC_DRIVER, JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        
        IDataSet dataSet = getDataSet(dataSetFileName);
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();
	}
	
	private IDataSet getDataSet(String dataSetFileName) throws DataSetException {
		return new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(dataSetFileName));
	}
	
	private void setUpEntityManger() {
		Map<String, String> persistenceMap = new HashMap<>();
		persistenceMap.put(JDBC_URL_PROPERTY_NAME, JDBC_URL);
		persistenceMap.put(JDBC_USER_PROPERTY_NAME, JDBC_USER);
		persistenceMap.put(JDBC_PASSWORD_PROPERTY_NAME, JDBC_PASSWORD);
		// todo: extract driver logic to separate class
		persistenceMap.put(JDBC_DRIVER_PROPERTY_NAME, JDBC_DRIVER);
		
		EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("CalculationResults", persistenceMap);
		entityManager = managerFactory.createEntityManager();
	}
	
	private void setUpCalculationResultsDao(EntityManager entityManager) {
		calculationResultsDao = new CalculationResultsDao(entityManager);
	}
	
	private void tearDownEntityManager() {
		entityManager.close();
	}
	
	private void compareActualToCurrentTable(String expecteDataSetFileName) throws SQLException, DatabaseUnitException {
		IDatabaseConnection connection = getDatabaseConnection();
		
		IDataSet databaseDataSet = connection.createDataSet(new String[] {TABLE_NAME});
		IDataSet actualDataSet = new CachedDataSet(databaseDataSet);
        IDataSet expectedDataSet = getDataSet(expecteDataSetFileName);
        
        ITable expectedTable = expectedDataSet.getTable(TABLE_NAME);
        ITable actualTable = actualDataSet.getTable(TABLE_NAME);
        ITable filteredActualTable = DefaultColumnFilter.includedColumnsTable(actualTable, expectedTable.getTableMetaData().getColumns());
        
        verifyTablesEquality(expectedTable, filteredActualTable);
	}
	
	private IDatabaseConnection getDatabaseConnection() throws DatabaseUnitException, SQLException {
		Connection jdbcConnection = getJdbcConnection();
		return new DatabaseConnection(jdbcConnection);
	}
	
	private Connection getJdbcConnection() throws SQLException {
		return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
	}
	
	private void verifyTablesEquality(ITable expectedTable, ITable actualTable) throws DatabaseUnitException {
		Assertion.assertEquals(expectedTable, actualTable);
	}
}
