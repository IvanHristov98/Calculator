package com.calculator.web.tests;

import com.calculator.web.tests.pageObjects.resources.CalculationResultsResourcePage;

import static com.calculator.web.tests.DatasetPaths.*;

import java.sql.SQLException;

import javax.ws.rs.core.Response;

import org.jboss.arquillian.junit.Arquillian;

import org.junit.*;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertThat;

import static org.hamcrest.Matchers.containsString;

@RunWith(Arquillian.class)
public class CalculationResultsResourceIT extends ResourceIT {
	
	private CalculationResultsResourcePage resourcePage;
	
	@Before
	public void SetUp() throws Exception {
		resourcePage = new CalculationResultsResourcePage(baseUrl);
		dbPage.useDataSet(EMPTY_DATA_SET);
	}
	
	@After
    public void tearDown() throws SQLException {
    	dbPage.restartAutoIncrementation();
    }
	
	@Test
	public void verifyCalculationResultsFetching() throws Exception {
		dbPage.useDataSet(PENDING_POPULATED_DATA_SET);
		Response pageResponse = resourcePage.getResourceContent();
		String pageEntity = pageResponse.readEntity(String.class);
		
		verifyThatEntityContainsExpression(pageEntity, "1");
		verifyThatEntityContainsExpression(pageEntity, "2");
	}
	
	private void verifyThatEntityContainsExpression(String pageEntity, String expressionContent) {
		assertThat(pageEntity, containsString("\"expression\":\"" + expressionContent + "\""));
	}
}
