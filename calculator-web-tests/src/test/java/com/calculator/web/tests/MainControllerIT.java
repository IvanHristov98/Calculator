package com.calculator.web.tests;

import com.calculator.web.MainController;
import com.calculator.web.tests.pageObjects.MainPage;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
 
@RunWith(Arquillian.class)
public class MainControllerIT {
	@ArquillianResource(MainController.class)
	private URL baseUrl;
	
    @Deployment(testable = false)
    public static WebArchive createDeployment() {
    	JavaArchive calculatorWeb = ShrinkWrap.create(ZipImporter.class)
    			.importFrom(new File("target"+File.separator+"lib"+File.separator+"calculator-web-1.0-SNAPSHOT-classes.jar"))
    			.as(JavaArchive.class);
        WebArchive war =  ShrinkWrap.create(WebArchive.class, "calculator-web.war")
        	.addClass(MainPage.class)
            .addAsLibraries(calculatorWeb)
            .addAsManifestResource("arquillian.xml")
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
            .setWebXML("web.xml");
 
        System.out.println(war.toString(true));
 
        return war;
    }
 
    @Test
    public void calculateValidExpression() throws IOException, InterruptedException {
    	MainPage mainPage = new MainPage(baseUrl);
    	BufferedReader calculationResponse = mainPage.getPageResponseOnCalculationRequest("5%2B5");
    	
    	assertThat(Double.parseDouble(calculationResponse.readLine()), equalTo(10.0));
    	assertThat(calculationResponse.read(), equalTo(-1));
    }
    
    @Test
    public void divisionByZeroExpression() throws IOException, InterruptedException {
    	MainPage mainPage = new MainPage(baseUrl);
    	BufferedReader calculationResponse = mainPage.getPageResponseOnCalculationRequest("1/0");
    	
    	assertThat(calculationResponse.readLine(), equalTo("Expression error. Division by zero encountered."));
    	assertThat(calculationResponse.read(), equalTo(-1));
    }
}