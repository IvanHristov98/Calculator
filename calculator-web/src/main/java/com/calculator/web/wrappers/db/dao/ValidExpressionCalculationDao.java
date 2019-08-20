package com.calculator.web.wrappers.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.calculator.web.wrappers.db.IDbConnection;
import com.calculator.web.wrappers.db.dto.ValidExpressionCalculation;
import com.calculator.web.wrappers.db.exception.*;
import com.calculator.web.wrappers.db.time.TimestampTranslator;;

public class ValidExpressionCalculationDao implements IDao<ValidExpressionCalculation, String> {

	private Connection connection;
	private TimestampTranslator timestampTranslator;
	
	public ValidExpressionCalculationDao(IDbConnection dbConnection, TimestampTranslator timestampStranslator) {
		this.connection = dbConnection.getConnection();
		this.timestampTranslator = timestampStranslator;
	}
	
	@Override
	public ValidExpressionCalculation getItem(String key) throws DbException {
		try {
			return getValidExpressionCalculation(key);
		} catch (SQLException exception) {
			throw new DbException(exception.getMessage(), exception);
		}
	}

	@Override
	public Collection<ValidExpressionCalculation> getItems() throws DbException {
		try {
			return getValidExpressionCalculations();
		} catch (SQLException exception) {
			throw new DbException(exception.getMessage(), exception);
		}
	}

	@Override
	public void save(ValidExpressionCalculation item) throws DbException {
		try {
			saveValidExpressionCalculation(item);
		} catch (SQLException exception) {
			throw new DbException(exception.getMessage(), exception);
		}
	}

	@Override
	public void update(ValidExpressionCalculation item) throws DbException {
		try {
			updateValidExpressionCalculation(item);
		} catch (SQLException exception) {
			throw new DbException(exception.getMessage(), exception);
		}
	}

	@Override
	public void delete(ValidExpressionCalculation item) throws DbException {
		try {
			deleteValidExpressionCalculation(item);
		} catch (SQLException exception) {
			throw new DbException(exception.getMessage(), exception);
		}
	}
	
	private ValidExpressionCalculation getValidExpressionCalculation(String key) throws SQLException, NotFoundException {
		String sql = "SELECT date, result FROM valid_expression_calculations WHERE expression = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		statement.setString(1, key);
		
		ResultSet resultSet = statement.executeQuery();
		
		if (!resultSet.next()) {
			throw new NotFoundException("No corresponding item found in the database.");
		}
		
		ValidExpressionCalculation calculation = getValidExpressionCalculationFromResultSet(resultSet);
		
		statement.close();
		return calculation;
	}
	
	private ValidExpressionCalculation getValidExpressionCalculationFromResultSet(ResultSet resultSet) throws SQLException {
		ValidExpressionCalculation calculation = new ValidExpressionCalculation();
		calculation.setExpression(resultSet.getString("expression"));
		calculation.setResult(resultSet.getDouble("result"));
		calculation.setDate(timestampTranslator.toInstant(resultSet.getTimestamp("date")));
		
		return calculation;
	}

	private Collection<ValidExpressionCalculation> getValidExpressionCalculations() throws SQLException {
		String sql = "SELECT expression, date, result FROM valid_expression_calculations";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultSet = statement.executeQuery();
		Collection<ValidExpressionCalculation> calculations = new ArrayList<>();
		
		while (resultSet.next()) {
			calculations.add(getValidExpressionCalculationFromResultSet(resultSet));
		}
		
		statement.close();
		return calculations;
	}

	private void saveValidExpressionCalculation(ValidExpressionCalculation item) throws SQLException {
		String sql = "INSERT INTO valid_expression_calculations (expression, result) VALUES (?, ?)";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		statement.setString(1, item.getExpression());
		statement.setDouble(2, item.getResult());
		
		statement.executeUpdate();
		
		statement.close();
	}

	private void updateValidExpressionCalculation(ValidExpressionCalculation item) throws SQLException {
		String sql = "UPDATE valid_expression_calculations SET result=? WHERE expression=?";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		statement.setDouble(1, item.getResult());
		statement.setString(1, item.getExpression());
		
		statement.executeUpdate();
		
		statement.close();
	}

	private void deleteValidExpressionCalculation(ValidExpressionCalculation item) throws SQLException {
		String sql = "DELETE FROM valid_expression_calculations WHERE expression=?";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		statement.setString(1, item.getExpression());
		
		statement.close();
	}
}
