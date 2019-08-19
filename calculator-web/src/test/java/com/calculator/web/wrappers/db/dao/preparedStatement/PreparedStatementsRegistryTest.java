package com.calculator.web.wrappers.db.dao;

import java.sql.PreparedStatement;

import org.mockito.Mock;

import org.junit.*;
import static org.junit.Assert.assertThat;

import static org.hamcrest.Matchers.equalTo;

public class PreparedStatementsRegistryTest {
	
	@Mock
	private PreparedStatement preparedStatement;
	
	private PreparedStatementsRegistry preparedStatementsRegistry;
	
	@Before
	public void setUp() {
		preparedStatementsRegistry = new PreparedStatementsRegistry();
	}
	
	@Test
	public void verifyPreparedStatementInsertion() {
		assertThat(preparedStatementsRegistry.getPreparedStatements().size(), equalTo(0));
		
		preparedStatementsRegistry.addPreparedStatement("save", preparedStatement);
		
		assertThat(preparedStatementsRegistry.getPreparedStatements().size(), equalTo(1));
	}
}
