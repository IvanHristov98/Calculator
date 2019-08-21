package com.calculator.web.wrappers.db.dao;

import com.calculator.web.resources.representations.CalculationResult;
import com.calculator.web.wrappers.db.DbConnection;
import com.calculator.web.wrappers.db.time.TimestampTranslator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.*;

import static org.junit.Assert.assertThat;

import static org.hamcrest.Matchers.equalTo;

public class CalculationResultsDaoTest {
	
	@Mock private DbConnection dbConnection;
	@Mock private Connection connection;
	@Mock private PreparedStatement statement;
	@Mock private ResultSet resultSet;
	@Mock private TimestampTranslator timestampTranslator;
	@InjectMocks private CalculationResult calculationResult;
	
	private CalculationResultsDao calculationResultsDao;
	private Instant nowAsInstant;
	private Timestamp nowAsTimestamp;
	
	@Before
	public void setUp() throws SQLException {
		initMocks(this);
		
		when(dbConnection.getConnection()).thenReturn(connection);
		when(connection.prepareStatement(anyString())).thenReturn(statement);
		when(statement.executeQuery()).thenReturn(resultSet);
		
		calculationResultsDao = new CalculationResultsDao(dbConnection, timestampTranslator);
		nowAsInstant = Instant.now();
		nowAsTimestamp = Timestamp.from(nowAsInstant);
	}
	
	@Test
	public void verifyItemGetting() throws SQLException {
		CalculationResult expectedItem = new CalculationResult();
		expectedItem.setExpression("1+1");
		expectedItem.setResult(2.0d);
		expectedItem.setMessage(null);
		
		when(resultSet.getString("expression")).thenReturn(expectedItem.getExpression());
		when(timestampTranslator.toInstant(nowAsTimestamp)).thenReturn(nowAsInstant);
		when(resultSet.getTimestamp("date")).thenReturn(nowAsTimestamp);
		when(resultSet.getDouble("result")).thenReturn(expectedItem.getResult());
		when(resultSet.getString("message")).thenReturn(expectedItem.getMessage());
		
		CalculationResult item = calculationResultsDao.getItem(expectedItem.getExpression());
		
		assertThat(item.getExpression(), equalTo(expectedItem.getExpression()));
		assertThat(item.getDate(), equalTo(nowAsInstant));
		assertThat(item.getResult(), equalTo(expectedItem.getResult()));
		assertThat(item.getMessage(), equalTo(expectedItem.getMessage()));
	}
	
	@Test
	public void verifyPreparedStatementExecutionWhenGettingAnItem() throws SQLException {
		String expression = "1+1";
		calculationResultsDao.getItem(expression);
		
		Mockito.verify(connection).prepareStatement(CalculationResultsDao.GET_ITEM_QUERY);
		Mockito.verify(statement).setString(1, expression);
		Mockito.verify(statement).executeQuery();
		Mockito.verify(statement).close();
		
		Mockito.verifyNoMoreInteractions(connection, statement);
	}
	
	@Test
	public void verifyGettingOfAllItems() throws SQLException {
		when(resultSet.next()).thenReturn(true, true, false);
		when(timestampTranslator.toInstant(nowAsTimestamp)).thenReturn(nowAsInstant);
		
		CalculationResult resultOne = new CalculationResult();
		resultOne.setExpression("1+1");
		resultOne.setDate(nowAsInstant);
		resultOne.setResult(2.0d);
		resultOne.setMessage(null);
		
		CalculationResult resultTwo = new CalculationResult();
		resultTwo.setExpression("1+2");
		resultTwo.setDate(nowAsInstant);
		resultTwo.setResult(3.0d);
		resultTwo.setMessage(null);
		
		when(resultSet.getString("expression")).thenReturn(resultOne.getExpression(), resultTwo.getExpression());
		when(resultSet.getTimestamp("date")).thenReturn(nowAsTimestamp, nowAsTimestamp);
		when(resultSet.getDouble("result")).thenReturn(resultOne.getResult(), resultTwo.getResult());
		when(resultSet.getString("message")).thenReturn(resultOne.getMessage(), resultTwo.getMessage());
		
		Collection<CalculationResult> calculationResults = calculationResultsDao.getItems();
		
		calculationResults.contains(resultOne);
		calculationResults.contains(resultTwo);
	}
	
	@Test
	public void verifyPreparedStatementExecutionWhenGettingAllItems() throws SQLException {
		calculationResultsDao.getItems();
		
		Mockito.verify(connection).prepareStatement(CalculationResultsDao.GET_ITEMS_QUERY);
		Mockito.verify(statement).executeQuery();
		Mockito.verify(statement).close();
		
		Mockito.verifyNoMoreInteractions(connection, statement);
	}
	
	@Test
	public void verifyPreparedStatementExecutionWhenSavingAnItem() throws SQLException {
		calculationResultsDao.save(calculationResult);
		
		Mockito.verify(connection).prepareStatement(CalculationResultsDao.SAVE_ITEM_QUERY);
		Mockito.verify(statement).setString(1, calculationResult.getExpression());
		Mockito.verify(statement).setDouble(2, calculationResult.getResult());
		Mockito.verify(statement).setString(3, calculationResult.getMessage());
		Mockito.verify(statement).executeUpdate();
		Mockito.verify(statement).close();
		
		Mockito.verifyNoMoreInteractions(connection, statement);
	}

	@Test
	public void verifyPreparedStatementExecutionWhenUpdatingAnItem() throws SQLException {
		when(timestampTranslator.toTimestamp(calculationResult.getDate())).thenReturn(nowAsTimestamp);
		calculationResultsDao.update(calculationResult);
		
		Mockito.verify(connection).prepareStatement(CalculationResultsDao.UPDATE_ITEM_QUERY);
		Mockito.verify(statement).setTimestamp(1, timestampTranslator.toTimestamp(calculationResult.getDate()));
		Mockito.verify(statement).setDouble(2, calculationResult.getResult());
		Mockito.verify(statement).setString(3, calculationResult.getMessage());
		Mockito.verify(statement).setString(4, calculationResult.getExpression());
		Mockito.verify(statement).executeUpdate();
		Mockito.verify(statement).close();
		
		Mockito.verifyNoMoreInteractions(connection, statement);
	}
	
	@Test
	public void verifyPreparedStatementExecutionWhenDeletingAnItem() throws SQLException {
		calculationResultsDao.delete(calculationResult);
		
		Mockito.verify(connection).prepareStatement(CalculationResultsDao.DELETE_ITEM_QUERY);
		Mockito.verify(statement).setString(1, calculationResult.getExpression());
		Mockito.verify(statement).executeUpdate();
		Mockito.verify(statement).close();
		
		Mockito.verifyNoMoreInteractions(connection, statement);
	}
}
