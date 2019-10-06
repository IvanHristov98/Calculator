package com.calculator.web.tests;

import com.calculator.web.tests.authorization.StubAuthorizationHeader;
import com.calculator.web.tests.authorization.JwtGenerator;
import com.calculator.web.tests.pageObjects.resources.*;

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
	private JwtGenerator jwtGenerator;
	private StubAuthorizationHeader authorizationHeader;
	
	@Before
	public void SetUp() throws Exception {
		jwtGenerator = new JwtGenerator();
		authorizationHeader = new StubAuthorizationHeader(jwtGenerator);
		
		resourcePage = new CalculationResultsResourcePage(baseUrl, authorizationHeader);
		dbPage.useDataSet(Datasets.EMPTY_DATA_SET.getPath());
	}
	
	@After
    public void tearDown() throws SQLException {
    	dbPage.restartAutoIncrementation();
    }
	
	@Test
	public void verifyCalculationResultsFetching() throws Exception {
		dbPage.useDataSet(Datasets.PENDING_POPULATED_DATA_SET.getPath());
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
