package com.calculator.web.wrappers.db.dao;

import java.sql.PreparedStatement;
import java.util.*;

public class PreparedStatementsRegistry {
	
	Map<String, PreparedStatement> preparedStatements;
	
	public PreparedStatementsRegistry() {
		this.preparedStatements = new HashMap<>();
	}
	
	public void addPreparedStatement(String name, PreparedStatement preparedStatement) {
		preparedStatements.put(name, preparedStatement);
	}
	
	public PreparedStatement getPreraredStatement(String name) {
		return preparedStatements.get(name);
	}
	
	public Collection<PreparedStatement> getPreparedStatements() {
		return preparedStatements.values();
	}
}
