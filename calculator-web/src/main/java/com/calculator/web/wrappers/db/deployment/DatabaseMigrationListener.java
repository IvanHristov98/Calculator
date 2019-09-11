package com.calculator.web.wrappers.db.deployment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.eclipse.persistence.sessions.Session;

import com.calculator.web.wrappers.db.DatabaseConnection;
import com.calculator.web.wrappers.db.DatabaseUri;
import com.calculator.web.wrappers.db.JdbcCredentials;
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
		DatabaseUri databaseUri = makeDatabaseUri();
		
		try {
			updateDb(databaseUri);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent contextEvent) {
	}
	
	private DatabaseUri makeDatabaseUri() {
		DriverFactory driverFactory = new DriverFactory();
		JdbcCredentials jdbcEnvironment = new JdbcCredentials();
		
		return new DatabaseUri(jdbcEnvironment, driverFactory);
	}
	
	private void updateDb(DatabaseUri databaseUri) throws SQLException, LiquibaseException, ClassNotFoundException {
		DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
		Connection connection = DriverManager.getConnection(databaseUri.getDatabaseUrl(), databaseUri.getUser(), databaseUri.getPassword());
		
		updateDbViaLiquiBase(connection);
		
		connection.close();
	}
	
	private void updateDbViaLiquiBase(Connection connection) throws LiquibaseException {
		Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
		Liquibase liquiBase = new liquibase.Liquibase(CHANGELOG_PATH, new ClassLoaderResourceAccessor(), database);
		
		liquiBase.update(new Contexts(), new LabelExpression());
	}
}
