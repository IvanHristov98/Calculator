package com.calculator.web.wrappers.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.calculator.web.resources.representations.CalculationResult;
import com.calculator.web.wrappers.db.DbConnection;
import com.calculator.web.wrappers.db.time.TimestampTranslator;

public class CalculationResultsDao implements IDao<CalculationResult, String> {

	public static final String GET_ITEM_QUERY = "SELECT expression, date, result, message FROM calculation_results WHERE expression=?;";
	public static final String GET_ITEMS_QUERY = "SELECT expression, date, result, message FROM calculation_results;";
	public static final String SAVE_ITEM_QUERY = "INSERT INTO calculation_results (expression, result, message) VALUES (?, ?, ?);";
	public static final String UPDATE_ITEM_QUERY = "UPDATE calculation_results SET date=?, result=?, message=? WHERE expression=?;";
	public static final String DELETE_ITEM_QUERY = "DELETE FROM calculation_results WHERE expression=?;";
	
	private Connection connection;
	private TimestampTranslator timestampTranslator;
	
	public CalculationResultsDao(DbConnection dbConnection, TimestampTranslator timestampTranslator) {
		this.connection = dbConnection.getConnection();
		this.timestampTranslator = timestampTranslator;
	}
	
	@Override
	public CalculationResult getItem(String key) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(GET_ITEM_QUERY);
		statement.setString(1, key);
		
		ResultSet resultSet = statement.executeQuery();
		
		resultSet.next();
		CalculationResult item = extractCalculationResultFromResultSet(resultSet);
		
		statement.close();
		return item;
	}

	@Override
	public Collection<CalculationResult> getItems() throws SQLException {
		PreparedStatement statement = connection.prepareStatement(GET_ITEMS_QUERY);
		
		ResultSet resultSet = statement.executeQuery();
		
		Collection<CalculationResult> items = new ArrayList<>();
		
		while (resultSet.next()) {
			CalculationResult item = extractCalculationResultFromResultSet(resultSet);
			items.add(item);
		}
		
		statement.close();
		return items;
	}

	@Override
	public void save(CalculationResult item) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(SAVE_ITEM_QUERY);
		statement.setString(1, item.getExpression());
		statement.setDouble(2, item.getResult());
		statement.setString(3, item.getMessage());
		
		statement.executeUpdate();
		statement.close();
	}

	@Override
	public void update(CalculationResult item) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(UPDATE_ITEM_QUERY);
		statement.setTimestamp(1, timestampTranslator.toTimestamp(item.getDate()));
		statement.setDouble(2, item.getResult());
		statement.setString(3, item.getMessage());
		statement.setString(4, item.getExpression());
		
		statement.executeUpdate();
		statement.close();
	}

	@Override
	public void delete(CalculationResult item) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(DELETE_ITEM_QUERY);
		statement.setString(1, item.getExpression());
		
		statement.executeUpdate();
		statement.close();
	}
	
	private CalculationResult extractCalculationResultFromResultSet(ResultSet resultSet) throws SQLException {
		CalculationResult result = new CalculationResult();
		result.setExpression(resultSet.getString("expression"));
		result.setDate(timestampTranslator.toInstant(resultSet.getTimestamp("date")));
		result.setResult(resultSet.getDouble("result"));
		result.setMessage(resultSet.getString("message"));
		
		return result;
	}
}
