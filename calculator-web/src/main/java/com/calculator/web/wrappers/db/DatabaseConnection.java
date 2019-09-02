package com.calculator.web.wrappers.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;



import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

public class DatabaseConnection {
	
	public static final String CHANGELOG_PATH = "liquibase/changelog/changelog.xml";
	
	public static final String JDBC_URL_PROPERTY_NAME = "javax.persistence.jdbc.url";
	public static final String JDBC_USER_PROPERTY_NAME = "javax.persistence.jdbc.user";
	public static final String JDBC_PASSWORD_PROPERTY_NAME = "javax.persistence.jdbc.password";
	public static final String JDBC_DRIVER_PROPERTY_NAME = "javax.persistence.jdbc.driver";
	
	public static final String PERSISTENCE_UNIT_NAME = "CalculationResults";
	
	private static DatabaseConnection instance;
	
	private EntityManager entityManager;
	
	private DatabaseConnection(LocalJdbcEnvironment jdbcEnvironment) throws SQLException, LiquibaseException {
		this.entityManager = getEntityManager(jdbcEnvironment);
		updateDb(jdbcEnvironment);
	}
	
	public static synchronized DatabaseConnection getInstance(LocalJdbcEnvironment jdbcEnvironment) throws SQLException, LiquibaseException {
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
	
	private void updateDb(LocalJdbcEnvironment jdbcEnvironment) throws SQLException, LiquibaseException {
		Connection connection = DriverManager.getConnection(jdbcEnvironment.getDatabaseUrl(), jdbcEnvironment.getUser(), jdbcEnvironment.getPassword());
		
		updateDbViaLiquiBase(connection);
		
		connection.close();
	}
	
	private void updateDbViaLiquiBase(Connection connection) throws LiquibaseException {
		Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
		Liquibase liquiBase = new liquibase.Liquibase(CHANGELOG_PATH, new ClassLoaderResourceAccessor(), database);
		
		liquiBase.update(new Contexts(), new LabelExpression());
	}
}
