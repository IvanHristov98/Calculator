package com.calculator.web.tests;

import com.calculator.web.tests.authorization.*;
import com.calculator.web.tests.pageObjects.resources.*;

import java.io.IOException;
import java.sql.SQLException;

import javax.ws.rs.core.Response;

import org.jboss.arquillian.junit.Arquillian;

import org.junit.*;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(Arquillian.class)
public class AuthorizationIT extends ResourceIT{
	
	private JwtGenerator jwtGenerator;
	private AuthorizationHeader authorizationHeader;
	
	@Before
	public void setUp() throws Exception {
		jwtGenerator = new JwtGenerator();
		authorizationHeader = new EmptyAuthorizationHeader(jwtGenerator);
		
    	dbPage.useDataSet(Datasets.EMPTY_DATA_SET.getPath());
	}
	
    @After
    public void tearDown() throws SQLException {
    	dbPage.restartAutoIncrementation();
    }
	
	@Test
	public void verifyUnauthorizedAccessToCalculateResource() throws IOException {
		CalculateResourcePage calculateResource = new CalculateResourcePage(baseUrl, authorizationHeader);
		
		final String expression = "1+2";
		calculateResource.setExpressionParameter(expression);
		Response pageResponse = calculateResource.getResourceContent();
		
		assertThat(pageResponse.getStatus(), equalTo(Response.Status.UNAUTHORIZED.getStatusCode()));
	}
	
	@Test
	public void verifyUnauthorizedAccessToCalculationResultResource() throws Exception {
		CalculationResultResourcePage calculationResultResource = new CalculationResultResourcePage(baseUrl, authorizationHeader);
		
		final String calculationResultId = "1";
		calculationResultResource.setIdParameter(calculationResultId);
		Response pageResponse = calculationResultResource.getResourceContent();
		
		assertThat(pageResponse.getStatus(), equalTo(Response.Status.UNAUTHORIZED.getStatusCode()));
	}
	
	@Test
	public void verifyUnauthorizedAccessToCalculationResultsResource() throws Exception {
		CalculationResultsResourcePage calculationResultsResource = new CalculationResultsResourcePage(baseUrl, authorizationHeader);
		Response pageResponse = calculationResultsResource.getResourceContent();
		
		assertThat(pageResponse.getStatus(), equalTo(Response.Status.UNAUTHORIZED.getStatusCode()));
	}
}
