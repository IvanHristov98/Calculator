package com.calculator.web.aspects;

import java.sql.SQLException;

import javax.persistence.EntityManager;

import com.calculator.web.aspects.annotations.InteractWithDb;
import com.calculator.web.aspects.interceptors.DbInteractionLogger;
import com.calculator.web.wrappers.db.DatabaseConnection;
import com.calculator.web.wrappers.db.DatabaseUri;
import com.calculator.web.wrappers.db.JdbcCredentials;
import com.calculator.web.wrappers.db.jdbcDrivers.DriverFactory;
import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;

public class DbInteractionModule extends AbstractModule {
	
	protected void configure () {
		bind(EntityManager.class).toInstance(makeEntityManager());
		
		bindInterceptor(Matchers.any(), Matchers.annotatedWith(InteractWithDb.class), new DbInteractionLogger());
	}
	
	private EntityManager makeEntityManager () {
		return makeDatabaseConnection().getEntityManager();
	}
	
	private DatabaseConnection makeDatabaseConnection () {
		try {
			return DatabaseConnection.getInstance(makeDatabaseUri());
		} catch (SQLException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	private DatabaseUri makeDatabaseUri () {
		JdbcCredentials jdbcCredentials = makeJdbcCredentials();
		DriverFactory driverFactory = makeDriverFactory();
		
		return new DatabaseUri(jdbcCredentials, driverFactory);
	}
	
	private JdbcCredentials makeJdbcCredentials () {
		return new JdbcCredentials();
	}
	
	private DriverFactory makeDriverFactory () {
		return new DriverFactory();
	}
}
