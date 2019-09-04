package com.calculator.web.wrappers.db;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DatabaseConnection {
	
	public static final String JDBC_URL_PROPERTY_NAME = "javax.persistence.jdbc.url";
	public static final String JDBC_USER_PROPERTY_NAME = "javax.persistence.jdbc.user";
	public static final String JDBC_PASSWORD_PROPERTY_NAME = "javax.persistence.jdbc.password";
	public static final String JDBC_DRIVER_PROPERTY_NAME = "javax.persistence.jdbc.driver";
	
	public static final String PERSISTENCE_UNIT_NAME = "CalculationResults";
	
	private static DatabaseConnection instance;
	
	private EntityManager entityManager;
	
	private DatabaseConnection(LocalJdbcEnvironment jdbcEnvironment) throws SQLException {
		this.entityManager = getEntityManager(jdbcEnvironment);
	}
	
	public static synchronized DatabaseConnection getInstance(LocalJdbcEnvironment jdbcEnvironment) throws SQLException {
		if (instance == null) {
			instance = new DatabaseConnection(jdbcEnvironment);
		}
		
		return instance;
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	private EntityManager getEntityManager(LocalJdbcEnvironment jdbcEnvironment) {
		Map<String, String> persistenceMap = getPersistenceMap(jdbcEnvironment);
		EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, persistenceMap);
		
		return managerFactory.createEntityManager();
	}
	
	private Map<String, String> getPersistenceMap(LocalJdbcEnvironment jdbcEnvironment) {
		Map<String, String> persistenceMap = new HashMap<>();
		
		persistenceMap.put(JDBC_URL_PROPERTY_NAME, jdbcEnvironment.getDatabaseUrl());
		persistenceMap.put(JDBC_USER_PROPERTY_NAME, jdbcEnvironment.getUser());
		persistenceMap.put(JDBC_PASSWORD_PROPERTY_NAME, jdbcEnvironment.getPassword());
		persistenceMap.put(JDBC_DRIVER_PROPERTY_NAME, jdbcEnvironment.getDriverName());
		
		return persistenceMap;
	}
}
