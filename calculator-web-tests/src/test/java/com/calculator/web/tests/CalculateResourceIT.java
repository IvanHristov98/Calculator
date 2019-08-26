package com.calculator.web.tests;

import com.calculator.web.tests.pageObjects.*;

import java.io.*;
import java.net.URL;
import java.sql.*;

import javax.ws.rs.core.Response;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
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
    	WebArchive calculatorWeb = ShrinkWrap.create(ZipImporter.class, "calculator-web.war")
    			.importFrom(new File("target"+File.separator+"wars"+File.separator+"calculator-web.war"))
    			.as(WebArchive.class)
    			.addAsManifestResource("arquillian.xml");
 
        return calculatorWeb;
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception {
    	dbPage = new DatabasePage();
    	dbPage.startDatabaseServer();
    	dbPage.createTable();
    }
    
    @AfterClass
    public static void tearDownClass() throws Exception {
    	dbPage.dropTable();
    	dbPage.shutDownDatabaseServer();
    }
    
    @Before
    public void setUp() throws Exception {
    	resourcePage = new CalculateResourcePage(baseUrl);
    	dbPage.useDataSet("/datasets/emptySet.xml");
    }
 
    @Test
    public void calculateValidExpression() throws IOException, InterruptedException, SQLException {
    	Response calculationResponse = resourcePage.getPageResponseOnCalculationRequest("(1+2)*3 + 2^2 + 4/2");
    	assertCorrectCalculation(calculationResponse.readEntity(String.class), "15.0");
    }
    
    @Test
    public void verifyCalculationSaving() throws Exception {
    	resourcePage.getPageResponseOnCalculationRequest("1+1");
    	dbPage.compareActualToCurrentTable("/datasets/expected-CalculateResourceIT#verifyCalculationSaving.xml");
    }
    
    @Test
    public void verifyDivisionByZeroException() throws IOException, InterruptedException {
    	Response calculationResponse = resourcePage.getPageResponseOnCalculationRequest("1/0");
    	assertBadRequest(calculationResponse, "400", "Expression error. Division by zero encountered.");
    }
    
    @Test
    public void verifyBracketsException() throws IOException {
    	Response calculationResponse = resourcePage.getPageResponseOnCalculationRequest("(1+2");
    	assertBadRequest(calculationResponse, "400", "Expression error. Brackets misplacement has been encountered.");
    }
    
    @Test
    public void verifyOperatorMisplacementException() throws IOException {
    	Response calculationResponse = resourcePage.getPageResponseOnCalculationRequest("1+2+");
    	assertBadRequest(calculationResponse, "400", "Expression error. Operator misplacement has been encountered.");
    }
    
    @Test
    public void verifyEmptyExpressionException() throws IOException {
    	Response calculationResponse = resourcePage.getPageResponseOnCalculationRequest("");
    	assertBadRequest(calculationResponse, "400", "Expression error. Empty expressions are not permitted.");
    }
    
    @Test
    public void verifyInvalidOperatorException() throws IOException {
    	Response calculationResponse = resourcePage.getPageResponseOnCalculationRequest("1A2");
    	assertBadRequest(calculationResponse, "400", "Expression error. An invalid operator has been encountered.");
    }
    
    @Test
    public void verifyNumberMisplacementException() throws IOException {
    	Response calculationResponse = resourcePage.getPageResponseOnCalculationRequest("1 2");
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