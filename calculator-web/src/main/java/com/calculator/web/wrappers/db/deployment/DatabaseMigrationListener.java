package com.calculator.web.wrappers.db.deployment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.calculator.web.wrappers.db.LocalJdbcEnvironment;
import com.calculator.web.wrappers.db.jdbcDrivers.DriverFactory;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

public class DatabaseMigrationListener implements ServletContextListener {
	
	public static final String CHANGELOG_PATH = "liquibase/changelog/changelog.xml";

	@Override
	public void contextInitialized(ServletContextEvent contextEvent) {
		DriverFactory driverFactory = new DriverFactory();
		LocalJdbcEnvironment jdbcEnvironment = new LocalJdbcEnvironment(driverFactory);
		
		try {
			updateDb(jdbcEnvironment);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent contextEvent) {
	}
	
	private static void updateDb(LocalJdbcEnvironment jdbcEnvironment) throws SQLException, LiquibaseException, ClassNotFoundException {
		org.postgresql.Driver.isRegistered();
		Connection connection = DriverManager.getConnection(jdbcEnvironment.getDatabaseUrl(), jdbcEnvironment.getUser(), jdbcEnvironment.getPassword());
		
		updateDbViaLiquiBase(connection);
		
		connection.close();
	}
	
	private static void updateDbViaLiquiBase(Connection connection) throws LiquibaseException {
		Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
		Liquibase liquiBase = new liquibase.Liquibase(CHANGELOG_PATH, new ClassLoaderResourceAccessor(), database);
		
		liquiBase.update(new Contexts(), new LabelExpression());
	}
}
