package com.calculator.web.tests;

import com.calculator.web.tests.pageObjects.CalculateResourcePage;

import java.io.*;
import java.net.URL;

import org.apache.http.client.ClientProtocolException;
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
	public static int END_OF_BUFFER = -1;
	
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
    
    @Before
    public void setUp() {
    	resourcePage = new CalculateResourcePage(baseUrl);
    }
 
    @Test
    public void calculateValidExpression() throws IOException, InterruptedException {
    	String calculationResponse = resourcePage.getPageResponseOnCalculationRequest("(1+2)*3 + 2^2");
    	assertCorrectCalculation(calculationResponse, "13.0");
    }
    
    @Test
    public void verifyDivisionByZeroException() throws IOException, InterruptedException {
    	String calculationResponse = resourcePage.getPageResponseOnCalculationRequest("1/0");
    	
    	assertErrorCode(calculationResponse, "400");
    	assertErrorMessage(calculationResponse, "Expression error. Division by zero encountered.");
    }
    
    @Test
    public void verifyBracketsException() throws ClientProtocolException, IOException {
    	String calculationResponse = resourcePage.getPageResponseOnCalculationRequest("(1+2");
    	
    	assertErrorCode(calculationResponse, "400");
    	assertErrorMessage(calculationResponse, "Expression error. Brackets misplacement has been encountered.");
    }
    
    @Test
    public void verifyOperatorMisplacementException() throws ClientProtocolException, IOException {
    	String calculationResponse = resourcePage.getPageResponseOnCalculationRequest("1+2+");
    	
    	assertErrorCode(calculationResponse, "400");
    	assertErrorMessage(calculationResponse, "Expression error. Operator misplacement has been encountered.");
    }
    
    @Test
    public void verifyEmptyExpressionException() throws ClientProtocolException, IOException {
    	String calculationResponse = resourcePage.getPageResponseOnCalculationRequest("");
    	
    	assertErrorCode(calculationResponse, "400");
    	assertErrorMessage(calculationResponse, "Expression error. Empty expressions are not permitted.");
    }
    
    @Test
    public void verifyInvalidOperatorException() throws ClientProtocolException, IOException {
    	String calculationResponse = resourcePage.getPageResponseOnCalculationRequest("1A2");
    	
    	assertErrorCode(calculationResponse, "400");
    	assertErrorMessage(calculationResponse, "Expression error. An invalid operator has been encountered.");
    }
    
    @Test
    public void verifyNumberMisplacementException() throws ClientProtocolException, IOException {
    	String calculationResponse = resourcePage.getPageResponseOnCalculationRequest("1 2");
    	
    	assertErrorCode(calculationResponse, "400");
    	assertErrorMessage(calculationResponse, "Expression error. An invalid number ordering has been encountered.");
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