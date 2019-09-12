package com.calculator.web.tests;

import com.calculator.web.tests.pageObjects.resources.CalculationResultsResourcePage;

import static com.calculator.web.tests.DatasetPaths.*;
import static com.calculator.web.tests.pageObjects.db.CalculationResultsTable.*;

import java.sql.SQLException;

import javax.ws.rs.core.Response;

import org.jboss.arquillian.junit.Arquillian;
import org.json.JSONArray;
import org.json.JSONObject;
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
		String pageContent = pageResponse.readEntity(String.class);
		JSONArray calculationResults = new JSONArray(pageContent);
		
		final int firstCalculationResultIndex = 0;
		final String firstExpression = "1";
		verifyThatEntityContainsExpression(calculationResults.getJSONObject(firstCalculationResultIndex), firstExpression);
		
		final int secondCalculationResultIndex = 1;
		final String secondExpression = "2";
		verifyThatEntityContainsExpression(calculationResults.getJSONObject(secondCalculationResultIndex), secondExpression);
	}
	
	private void verifyThatEntityContainsExpression(JSONObject calculationResult, String expectedExpression) {
		assertThat(calculationResult.getString(EXPRESSION), containsString(expectedExpression));
	}
}
