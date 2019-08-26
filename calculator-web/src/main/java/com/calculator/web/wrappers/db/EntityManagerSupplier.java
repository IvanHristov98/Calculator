package com.calculator.web.wrappers.db;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerSupplier {
	
	public static final String JDBC_URL_PROPERTY_NAME = "javax.persistence.jdbc.url";
	public static final String JDBC_USER_PROPERTY_NAME = "javax.persistence.jdbc.user";
	public static final String JDBC_PASSWORD_PROPERTY_NAME = "javax.persistence.jdbc.password";
	public static final String JDBC_DRIVER_PROPERTY_NAME = "javax.persistence.jdbc.driver";
	
	public static final String PERSISTENCE_UNIT_NAME = "CalculationResults";
	
	private static EntityManagerSupplier instance;
	
	private EntityManager entityManager;
	
	private EntityManagerSupplier(LocalJdbcEnvironment jdbcEnvironment) {
		this.entityManager = getEntityManager(jdbcEnvironment);
	}
	
	public static synchronized EntityManagerSupplier getInstance(LocalJdbcEnvironment jdbcEnvironment) throws SQLException {
		if (instance == null) {
			instance = new EntityManagerSupplier(jdbcEnvironment);
		}
		
		return instance;
	}
	
	private EntityManager getEntityManager(LocalJdbcEnvironment jdbcEnvironment) {
		Map<String, String> persistenceMap = new HashMap<>();
		persistenceMap.put(JDBC_URL_PROPERTY_NAME, jdbcEnvironment.getDatabaseUrl());
		persistenceMap.put(JDBC_USER_PROPERTY_NAME, jdbcEnvironment.getUser());
		persistenceMap.put(JDBC_PASSWORD_PROPERTY_NAME, jdbcEnvironment.getPassword());
		// todo: extract driver logic to separate class
		persistenceMap.put(JDBC_DRIVER_PROPERTY_NAME, jdbcEnvironment.getDriverName());
		
		EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, persistenceMap);
		return managerFactory.createEntityManager();
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
}
