package com.calculator.web.wrappers.db.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

import com.calculator.web.wrappers.db.IDbConnection;
import com.calculator.web.wrappers.db.dto.InvalidExpressionCalculation;
import com.calculator.web.wrappers.db.exception.*;
import com.calculator.web.wrappers.db.time.*;

public class InvalidExpressionCalculationDao implements IDao<InvalidExpressionCalculation, String> {
	
	private IDbConnection dbConnection;
	private TimestampTranslator timestampTranslator;
	
	public InvalidExpressionCalculationDao(IDbConnection dbConnection, TimestampTranslator timestampStranslator) {
		this.dbConnection = dbConnection;
		this.timestampTranslator = timestampStranslator;
	}
	
	@Override
	public InvalidExpressionCalculation getItem(String key) throws DbException {
		try {
			return getItemWithNoExceptionHandling(key);
		} catch (SQLException exception) {
			throw new DbException(exception.getMessage(), exception);
		}
	}

	@Override
	public Collection<InvalidExpressionCalculation> getItems() throws DbException {
		try {
			return getItemsWithNoExceptionHandling();
		} catch (SQLException exception) {
			throw new DbException(exception.getMessage(), exception);
		}
	}
	
	@Override
	public void save(InvalidExpressionCalculation item)  throws DbException {
		try {
			saveWithNoExceptionHandling(item);
		} catch (SQLException exception) {
			throw new DbException(exception.getMessage(), exception);
		}
	}

	@Override
	public void update(InvalidExpressionCalculation item)  throws DbException {
		try {
			updateWithNoExceptionHandling(item);
		} catch (SQLException exception) {
			throw new DbException(exception.getMessage(), exception);
		}
	}

	@Override
	public void delete(InvalidExpressionCalculation item)  throws DbException {
		try {
			deleteWithNoExceptionHandling(item);
		} catch (SQLException exception) {
			throw new DbException(exception.getMessage(), exception);
		}
	}
	
	private InvalidExpressionCalculation getItemWithNoExceptionHandling(String key) throws DbException, SQLException {
		String sql = "SELECT expression, date, incorrectness_reason "
				+ "FROM invalid_expression_calculations "
				+ "WHERE expression = ?";
		PreparedStatement statement = getPreparedStatement(dbConnection, sql);
		
		statement.setString(1, key);
		
		ResultSet resultSet = statement.executeQuery();
		// accessing the first and only row
		if (!resultSet.next()) {
			throw new NotFoundException("No corresponding item found in the database.");
		}
		InvalidExpressionCalculation calculation = getInvalidExpressionCalculationFromResultSet(resultSet);
		
		statement.close();
		return calculation;
	}
	
	private PreparedStatement getPreparedStatement(IDbConnection dbConnection, String sql) throws SQLException {
		Connection connection = dbConnection.getConnection();
		return connection.prepareStatement(sql);
	}
	
	private InvalidExpressionCalculation getInvalidExpressionCalculationFromResultSet(ResultSet resultSet) throws SQLException {
		InvalidExpressionCalculation calculation = new InvalidExpressionCalculation();
		
		calculation.setExpression(resultSet.getString("expression"));
		calculation.setDate(timestampTranslator.toInstant(resultSet.getTimestamp("date", LocalTimezone.TIME_ZONE_UTC)));
		calculation.setIncorrectnessReason(resultSet.getString("incorrectness_reason"));
		
		return calculation;
	}

	private Collection<InvalidExpressionCalculation> getItemsWithNoExceptionHandling() throws SQLException {
		String sql = "SELECT expression, date, incorrectness_reason FROM invalid_expression_calculations";
		PreparedStatement statement = getPreparedStatement(dbConnection, sql);
		
		ResultSet resultSet = statement.executeQuery();
		Collection<InvalidExpressionCalculation> calculations = new ArrayList<>();
		
		while (resultSet.next()) {
			InvalidExpressionCalculation calculation = getInvalidExpressionCalculationFromResultSet(resultSet);
			calculations.add(calculation);
		}
		
		return calculations;
	}
	
	private void saveWithNoExceptionHandling(InvalidExpressionCalculation item) throws SQLException {
		String sql = "INSERT INTO invalid_expression_calculations (expression, incorrectness_reason) VALUES (?, ?)";
		PreparedStatement statement = getPreparedStatement(dbConnection, sql);
		
		statement.setString(1, item.getExpression());
		statement.setString(2, item.getIncorrectnessReason());
		
		statement.executeUpdate();
	}
	
	private void updateWithNoExceptionHandling(InvalidExpressionCalculation item) throws SQLException {
		String sql = "UPDATE invalid_expression_calculations SET expression = ?";
		PreparedStatement statement = getPreparedStatement(dbConnection, sql);
		
		statement.setString(1, item.getIncorrectnessReason());
		statement.setString(2, item.getExpression());
		
		statement.executeUpdate();
	}
	
	private void deleteWithNoExceptionHandling(InvalidExpressionCalculation item) throws SQLException {
		String sql = "DELETE FROM invalid_expression_calculations WHERE expression = ?";
		PreparedStatement statement = getPreparedStatement(dbConnection, sql);
		
		statement.setString(1, item.getExpression());
		
		statement.executeUpdate();
	}
}
