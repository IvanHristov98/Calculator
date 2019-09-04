package com.calculator.web.wrappers.db;

import org.mockito.Mock;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.*;

import org.junit.*;

import static org.hamcrest.Matchers.*;

import com.calculator.web.wrappers.db.jdbcDrivers.DriverFactory;
import com.calculator.web.wrappers.db.jdbcDrivers.PostgreDriver;

public class DatabaseUriTest {
	
	public static String DATABASE_URI = "postgres://username:password@host:port/database";
	
	@Mock private JdbcCredentials jdbcCredentials;
	@Mock private DriverFactory driverFactory;
	private PostgreDriver postgreDriver;
	
	private DatabaseUri databaseUri;
	
	@Before
	public void setUp() {
		initMocks(this);
		
		postgreDriver = new PostgreDriver();
		
		when(jdbcCredentials.getUri()).thenReturn(DATABASE_URI);
		when(driverFactory.makeDriver(any())).thenReturn(postgreDriver);
		
		databaseUri = new DatabaseUri(jdbcCredentials, driverFactory);
	}
	
	@Test
	public void verifyDatabaseUrlGetting() {
		final String expectedUrl = "jdbc:postgresql://host:port/database";
		
		assertThat(databaseUri.getDatabaseUrl(), equalTo(expectedUrl));
	}
	
	@Test
	public void verifyUserGetting() {
		final String expectedUser = "username";
		
		assertThat(databaseUri.getUser(), equalTo(expectedUser));
	}
	
	@Test
	public void verifyPasswordGetting() {
		final String expectedPassword = "password";
		
		assertThat(databaseUri.getPassword(), equalTo(expectedPassword));
	}
	
	@Test
	public void verifyDriverGetting() {
		assertThat(databaseUri.getDriverName(), equalTo(PostgreDriver.DRIVER_NAME));
	}
}
