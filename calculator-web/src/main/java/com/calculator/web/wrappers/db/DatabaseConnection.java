package com.calculator.web.wrappers.db;

import java.sql.SQLException;
import java.util.*;

import javax.persistence.*;

public class DatabaseConnection {
	
	public static final String JDBC_URL_PROPERTY_NAME = "javax.persistence.jdbc.url";
	public static final String JDBC_USER_PROPERTY_NAME = "javax.persistence.jdbc.user";
	public static final String JDBC_PASSWORD_PROPERTY_NAME = "javax.persistence.jdbc.password";
	public static final String JDBC_DRIVER_PROPERTY_NAME = "javax.persistence.jdbc.driver";
	
	public static final String PERSISTENCE_UNIT_NAME = "CalculationResults";
	
	private static DatabaseConnection instance;
	
	private EntityManager entityManager;
	
	private DatabaseConnection(DatabaseUri databaseUri) throws SQLException {
		this.entityManager = getEntityManager(databaseUri);
	}
	
	public static synchronized DatabaseConnection getInstance(DatabaseUri databaseUri) throws SQLException {
		if (instance == null) {
			instance = new DatabaseConnection(databaseUri);
		}
		
		return instance;
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	private EntityManager getEntityManager(DatabaseUri databaseUri) {
		Map<String, String> persistenceMap = getPersistenceMap(databaseUri);
		EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, persistenceMap);
		
		return managerFactory.createEntityManager();
	}
	
	private Map<String, String> getPersistenceMap(DatabaseUri databaseUri) {
		Map<String, String> persistenceMap = new HashMap<>();
		
		persistenceMap.put(JDBC_URL_PROPERTY_NAME, databaseUri.getDatabaseUrl());
		persistenceMap.put(JDBC_USER_PROPERTY_NAME, databaseUri.getUser());
		persistenceMap.put(JDBC_PASSWORD_PROPERTY_NAME, databaseUri.getPassword());
		persistenceMap.put(JDBC_DRIVER_PROPERTY_NAME, databaseUri.getDriverName());
		
		return persistenceMap;
	}
}
