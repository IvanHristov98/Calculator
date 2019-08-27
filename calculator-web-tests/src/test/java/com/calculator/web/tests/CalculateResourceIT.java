package com.calculator.web.tests;

import com.calculator.web.tests.archive.WebCalculatorArchiveFactory;
import com.calculator.web.tests.pageObjects.db.DatabasePage;
import com.calculator.web.tests.pageObjects.resources.CalculateResourcePage;

import static com.calculator.web.tests.DatasetPaths.*;

import java.io.*;
import java.net.URL;
import java.sql.*;

import javax.ws.rs.core.Response;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import org.junit.*;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(Arquillian.class)
public class CalculateResourceIT {
	public static DatabasePage dbPage;
	
	@ArquillianResource
	private URL baseUrl;
	private CalculateResourcePage resourcePage;
	
    @Deployment(testable = false)
    public static WebArchive createDeployment() {
    	WebCalculatorArchiveFactory webCalculatorArchive = new WebCalculatorArchiveFactory();
        return webCalculatorArchive.makeArchive();
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception {
    	dbPage = new DatabasePage();
    	dbPage.startDatabaseServer();
    	dbPage.createSchema();
    }
    
    @AfterClass
    public static void tearDownClass() throws Exception {
    	dbPage.dropTable();
    	dbPage.shutDownDatabaseServer();
    }
    
    @Before
    public void setUp() throws Exception {
    	resourcePage = new CalculateResourcePage(baseUrl);
    	dbPage.useDataSet(EMPTY_DATA_SET);
    }
 
    @Test
    public void calculateValidExpression() throws IOException, InterruptedException, SQLException {
    	resourcePage.setExpressionParameter("(1+2)*3 + 2^2 + 4/2");
    	Response calculationResponse = resourcePage.getResourceContent();
    	assertCorrectCalculation(calculationResponse.readEntity(String.class), "15.0");
    }
    
    @Test
    public void verifyCalculationSaving() throws Exception {
    	resourcePage.setExpressionParameter("1+1");
    	resourcePage.getResourceContent();
    	dbPage.compareActualToCurrentTable(SINGLE_ITEM_DATA_SET);
    }
    
    @Test
    public void verifyDivisionByZeroException() throws IOException, InterruptedException {
    	resourcePage.setExpressionParameter("1/0");
    	Response calculationResponse = resourcePage.getResourceContent();
    	assertBadRequest(calculationResponse, "400", "Expression error. Division by zero encountered.");
    }
    
    @Test
    public void verifyBracketsException() throws IOException {
    	resourcePage.setExpressionParameter("(1+2");
    	Response calculationResponse = resourcePage.getResourceContent();
    	assertBadRequest(calculationResponse, "400", "Expression error. Brackets misplacement has been encountered.");
    }
    
    @Test
    public void verifyOperatorMisplacementException() throws IOException {
    	resourcePage.setExpressionParameter("1+2+");
    	Response calculationResponse = resourcePage.getResourceContent();
    	assertBadRequest(calculationResponse, "400", "Expression error. Operator misplacement has been encountered.");
    }
    
    @Test
    public void verifyEmptyExpressionException() throws IOException {
    	resourcePage.setExpressionParameter("");
    	Response calculationResponse = resourcePage.getResourceContent();
    	assertBadRequest(calculationResponse, "400", "Expression error. Empty expressions are not permitted.");
    }
    
    @Test
    public void verifyInvalidOperatorException() throws IOException {
    	resourcePage.setExpressionParameter("1A2");
    	Response calculationResponse = resourcePage.getResourceContent();
    	assertBadRequest(calculationResponse, "400", "Expression error. An invalid operator has been encountered.");
    }
    
    @Test
    public void verifyNumberMisplacementException() throws IOException {
    	resourcePage.setExpressionParameter("1 2");
    	Response calculationResponse = resourcePage.getResourceContent();
    	assertBadRequest(calculationResponse, "400", "Expression error. An invalid number ordering has been encountered.");
    }
    
    private void assertBadRequest(Response calculationResponse, String code, String message) {
    	assertThat(getHttpStatusCodeOfResponse(calculationResponse), equalTo(getBadRequestStatusCode()));
    	
    	// After first read it empties the buffer
    	String entityOfResponse = getEntityOfResponse(calculationResponse);
    	assertErrorCode(entityOfResponse, code);
    	assertErrorMessage(entityOfResponse, message);
    }
    
    private int getHttpStatusCodeOfResponse(Response response) {
    	return response.getStatus();
    }
    
    private int getBadRequestStatusCode() {
    	return Response.Status.BAD_REQUEST.getStatusCode();
    }
    
    private String getEntityOfResponse(Response response) {
    	return response.readEntity(String.class);
    }
    
    private void assertCorrectCalculation(String pageResponce, String value) {
    	assertThat(pageResponce, containsString(value));
    }
    
    private void assertErrorCode(String pageResponse, String code) {
    	assertThat(pageResponse, containsString("\"code\":\"" + code + "\""));
    }
    
    private void assertErrorMessage(String pageResponse, String message) {
    	assertThat(pageResponse, containsString("\"message\":\"" + message + "\""));
    }
}