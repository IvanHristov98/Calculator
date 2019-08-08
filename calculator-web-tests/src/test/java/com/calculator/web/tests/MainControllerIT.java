package com.calculator.web.tests;

//import com.calculator.web.MainController;
import com.calculator.web.tests.pageObjects.MainPage;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.http.client.ClientProtocolException;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.closeTo;

@RunWith(Arquillian.class)
public class MainControllerIT {
	public static int END_OF_BUFFER = -1;
	
	@ArquillianResource
	private URL baseUrl;
	private MainPage mainPage;
	
    @Deployment(testable = false)
    public static WebArchive createDeployment() {
    	WebArchive calculatorWeb = ShrinkWrap.create(ZipImporter.class)
    			.importFrom(new File("target"+File.separator+"wars"+File.separator+"calculator-web.war"))
    			.as(WebArchive.class);
        WebArchive war =  ShrinkWrap.create(WebArchive.class, "calculator-web.war")
            .addAsLibraries(calculatorWeb)
            .addAsManifestResource("arquillian.xml")
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
            .setWebXML("web.xml");
        
        WebArchive mergedArchive = war.merge(calculatorWeb);
 
        System.out.println(mergedArchive.toString(true));
 
        return mergedArchive;
    }
    
    @Before
    public void setUp() {
    	mainPage = new MainPage(baseUrl);
    }
 
    @Test
    public void calculateValidExpression() throws IOException, InterruptedException {
    	BufferedReader calculationResponse = mainPage.getPageResponseOnCalculationRequest("(1+2)*3 + 2^2");
    	assertThat(Double.parseDouble(calculationResponse.readLine()), closeTo(13.0d, 0.0d));
    }
    
    @Test
    public void verifyDivisionByZeroException() throws IOException, InterruptedException {
    	BufferedReader calculationResponse = mainPage.getPageResponseOnCalculationRequest("1/0");
    	assertThat(calculationResponse.readLine(), equalTo("Expression error. Division by zero encountered."));
    }
    
    @Test
    public void verifyBracketsException() throws ClientProtocolException, IOException {
    	BufferedReader calculationResponse = mainPage.getPageResponseOnCalculationRequest("(1+2");
    	assertThat(calculationResponse.readLine(), equalTo("Expression error. Brackets misplacement has been encountered."));
    }
    
    @Test
    public void verifyOperatorMisplacementException() throws ClientProtocolException, IOException {
    	BufferedReader calculationResponse = mainPage.getPageResponseOnCalculationRequest("1+2+");
    	assertThat(calculationResponse.readLine(), equalTo("Expression error. Operator misplacement has been encountered."));
    }
    
    @Test
    public void verifyEmptyExpressionException() throws ClientProtocolException, IOException {
    	BufferedReader calculationResponse = mainPage.getPageResponseOnCalculationRequest("");
    	assertThat(calculationResponse.readLine(), equalTo("Expression error. Empty expressions are not permitted."));
    }
    
    @Test
    public void verifyInvalidOperatorException() throws ClientProtocolException, IOException {
    	BufferedReader calculationResponse = mainPage.getPageResponseOnCalculationRequest("1A2");
    	assertThat(calculationResponse.readLine(), equalTo("Expression error. An invalid operator has been encountered."));
    }
    
    @Test
    public void verifyNumberMisplacementException() throws ClientProtocolException, IOException {
    	BufferedReader calculationResponse = mainPage.getPageResponseOnCalculationRequest("1 2");
    	assertThat(calculationResponse.readLine(), equalTo("Expression error. An invalid number ordering has been encountered."));
    }
}