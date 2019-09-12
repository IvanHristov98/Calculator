package com.calculator.web.tests;

import com.calculator.web.tests.pageObjects.resources.CalculateResourcePage;

import static com.calculator.web.tests.DatasetPaths.*;

import java.io.*;
import java.sql.*;

import javax.ws.rs.core.Response;

import org.jboss.arquillian.junit.Arquillian;

import org.junit.*;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(Arquillian.class)
public class CalculateResourceIT extends ResourceIT {
	
	private CalculateResourcePage resourcePage;
    
    @Before
    public void setUp() throws Exception {
    	resourcePage = new CalculateResourcePage(baseUrl);
    	dbPage.useDataSet(EMPTY_DATA_SET);
    }
    
    @After
    public void tearDown() throws SQLException {
    	dbPage.restartAutoIncrementation();
    }
 
    @Test
    public void calculateExpressionAcceptance() throws IOException, InterruptedException, SQLException {
    	resourcePage.setExpressionParameter("(1+2)*3 + 2^2 + 4/2");
    	Response calculationResponse = resourcePage.getResourceContent();
    	
    	assertThat(calculationResponse.getStatus(), equalTo(Response.Status.ACCEPTED.getStatusCode()));
    }
    
    @Test
    public void verifyCalculationSaving() throws Exception {
    	resourcePage.setExpressionParameter("1+1");
    	resourcePage.getResourceContent();
    	dbPage.compareActualToExpectedTable(PENDING_SINGLE_ITEM_DATA_SET);
    }
    
    @Test
    public void verifyReturnedId() throws IOException {
    	resourcePage.setExpressionParameter("1+1");
    	
    	resourcePage.getResourceContent();
    	Response calculationResponse = resourcePage.getResourceContent();
    	
    	String recordId = calculationResponse.readEntity(String.class);
    	final String expectedRecordId = "2";
    	
    	assertThat(recordId, equalTo(expectedRecordId));
    }
}