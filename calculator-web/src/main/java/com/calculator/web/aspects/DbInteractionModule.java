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
		try {
			bind(EntityManager.class).toInstance(makeEntityManager());
			
			bindInterceptor(Matchers.any(), Matchers.annotatedWith(InteractWithDb.class), new DbInteractionLogger());
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
	}
	
	private EntityManager makeEntityManager () throws SQLException {
		return makeDatabaseConnection().getEntityManager();
	}
	
	private DatabaseConnection makeDatabaseConnection () throws SQLException {
		return DatabaseConnection.getInstance(makeDatabaseUri());
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
