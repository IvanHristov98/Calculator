package com.calculator.web.tests;

import com.calculator.web.tests.pageObjects.resources.CalculationResultResourcePage;

import static com.calculator.web.tests.pageObjects.db.CalculationResultsTable.*;

import static com.calculator.web.tests.DatasetPaths.*;
import static org.junit.Assert.assertThat;

import static org.hamcrest.Matchers.*;

import org.jboss.arquillian.junit.Arquillian;
import org.json.JSONObject;
import org.junit.*;
import org.junit.runner.RunWith;

import javax.ws.rs.core.Response;

@RunWith(Arquillian.class)
public class CalculationResultResourceIT extends ResourceIT {
	
	private CalculationResultResourcePage resourcePage;
	
	@Before
	public void setUp() throws Exception {
		resourcePage = new CalculationResultResourcePage(baseUrl);
		dbPage.useDataSet(EMPTY_DATA_SET);
	}
	
	@After
	public void tearDown() throws Exception {
		dbPage.restartAutoIncrementation();
	}
	
	@Test
	public void verifyExpressionGetting() throws Exception {
		dbPage.useDataSet(COMPLETED_SINGLE_ITEM_DATA_SET);
		
		final String calculationResultId = "1";
		
		resourcePage.setIdParameter(calculationResultId);
		Response pageResponse = resourcePage.getResourceContent();
		String pageEntity = pageResponse.readEntity(String.class);
		
		JSONObject calculationResult = new JSONObject(pageEntity);
		
		final String expectedExpression = "1";
		final Double expectedResult = 1.0d;
		final int expectedStatus = 2;
		
		assertThat(calculationResult.getString(EXPRESSION), equalTo(expectedExpression));
		assertThat(calculationResult.getDouble(EVALUATION), closeTo(expectedResult, 0.001));
		assertThat(calculationResult.getInt(STATUS), equalTo(expectedStatus));
	}
	
	@Test
	public void verifyHttpStatusCodeWhenResourceNotFound() throws Exception {
		final String invalidCalculationResultId = "12345";
		
		resourcePage.setIdParameter(invalidCalculationResultId);
		Response pageResponse = resourcePage.getResourceContent();
		
		assertThat(pageResponse.getStatus(), equalTo(Response.Status.NOT_FOUND.getStatusCode()));
	}
}
