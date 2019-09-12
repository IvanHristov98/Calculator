package com.calculator.web.tests;

import com.calculator.web.tests.pageObjects.resources.CalculationResultResourcePage;

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
		
		resourcePage.setIdParameter("1");
		Response pageResponse = resourcePage.getResourceContent();
		String pageEntity = pageResponse.readEntity(String.class);
		
		JSONObject calculationResult = new JSONObject(pageEntity);
		
		assertThat(calculationResult.getString("expression"), equalTo("1"));
		assertThat(calculationResult.getDouble("evaluation"), closeTo(1.0, 0.001));
		assertThat(calculationResult.getInt("status"), equalTo(2));
	}
	
	
	@Test
	public void verifyHttpStatusCodeWhenResourceNotFound() throws Exception {
		resourcePage.setIdParameter("12345");
		Response pageResponse = resourcePage.getResourceContent();
		
		assertThat(pageResponse.getStatus(), equalTo(Response.Status.NOT_FOUND.getStatusCode()));
	}
}
