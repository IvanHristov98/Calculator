package com.calculator.web.tests;

import com.calculator.web.tests.authorization.StubAuthorizationHeader;
import com.calculator.web.tests.authorization.JwtGenerator;
import com.calculator.web.tests.pageObjects.resources.*;

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
	private JwtGenerator jwtGenerator;
	private StubAuthorizationHeader authorizationHeader;
    
    @Before
    public void setUp() throws Exception {
    	jwtGenerator = new JwtGenerator();
    	authorizationHeader = new StubAuthorizationHeader(jwtGenerator);
    	
    	resourcePage = new CalculateResourcePage(baseUrl, authorizationHeader);
    	dbPage.useDataSet(Datasets.EMPTY_DATA_SET.getPath());
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
    	dbPage.compareActualToExpectedTable(Datasets.PENDING_SINGLE_ITEM_DATA_SET.getPath());
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