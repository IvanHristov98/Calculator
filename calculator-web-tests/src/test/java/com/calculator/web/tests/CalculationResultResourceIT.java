package com.calculator.web.tests;

import com.calculator.web.tests.authorization.*;
import com.calculator.web.tests.pageObjects.resources.*;

import static com.calculator.web.tests.pageObjects.db.CalculationResultsTable.*;

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
	private JwtGenerator jwtGenerator;
	private AuthorizationHeader authorizationHeader;
	
	@Before
	public void setUp() throws Exception {
		jwtGenerator = new JwtGenerator();
		authorizationHeader = new StubAuthorizationHeader(jwtGenerator);
		
		resourcePage = new CalculationResultResourcePage(baseUrl, authorizationHeader);
		dbPage.useDataSet(Datasets.EMPTY_DATA_SET.getPath());
	}
	
	@After
	public void tearDown() throws Exception {
		dbPage.restartAutoIncrementation();
	}
	
	@Test
	public void verifyExpressionGetting() throws Exception {
		dbPage.useDataSet(Datasets.COMPLETED_SINGLE_ITEM_DATA_SET.getPath());
		
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
