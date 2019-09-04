package com.calculator.web.wrappers.db;

import org.mockito.Mock;

import static org.mockito.MockitoAnnotations.initMocks;

import java.sql.SQLException;

import static org.mockito.Mockito.when;

import org.junit.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class DatabaseConnectionTest {
	
	@Mock private DatabaseUri databaseUri;
	
	@Before
	public void setUp() {
		initMocks(this);
		
		when(databaseUri.getDatabaseUrl()).thenReturn("jdbc:derby:memory:calculator_db;create=true");
		when(databaseUri.getUser()).thenReturn("");
		when(databaseUri.getPassword()).thenReturn("");
	}
	
	@Test
	public void verifyEntityManagerCreation() throws SQLException {
		DatabaseConnection connection = DatabaseConnection.getInstance(databaseUri);
		
		assertThat(connection.getEntityManager(), notNullValue());
	}
	
	@Test
	public void verifySingletonPattern() throws SQLException {
		DatabaseConnection firstConnection = DatabaseConnection.getInstance(databaseUri);
		DatabaseConnection secondConnection = DatabaseConnection.getInstance(databaseUri);
		
		assertThat(firstConnection, is(secondConnection));
	}
}
