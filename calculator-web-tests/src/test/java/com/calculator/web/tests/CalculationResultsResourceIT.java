package com.calculator.web.tests;

import com.calculator.web.tests.archive.WebCalculatorArchiveFactory;
import com.calculator.web.tests.pageObjects.db.DatabasePage;
import com.calculator.web.tests.pageObjects.resources.CalculationResultsResourcePage;

import static com.calculator.web.tests.DatasetPaths.*;

import java.net.URL;

import javax.ws.rs.core.Response;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import org.junit.*;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertThat;

import static org.hamcrest.Matchers.containsString;

@RunWith(Arquillian.class)
public class CalculationResultsResourceIT {
	public static DatabasePage dbPage;
	
	@ArquillianResource
	private URL baseUrl;
	private CalculationResultsResourcePage resourcePage;
	
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
	public void SetUp() throws Exception {
		resourcePage = new CalculationResultsResourcePage(baseUrl);
		dbPage.useDataSet(EMPTY_DATA_SET);
	}
	
	@Test
	public void verifyCalculationResultsFetching() throws Exception {
		dbPage.useDataSet(POPULATED_DATA_SET);
		Response pageResponse = resourcePage.getResourceContent();
		String pageEntity = pageResponse.readEntity(String.class);
		
		verifyThatEntityContainsExpression(pageEntity, "1");
		verifyThatEntityContainsExpression(pageEntity, "2");
	}
	
	private void verifyThatEntityContainsExpression(String pageEntity, String expressionContent) {
		assertThat(pageEntity, containsString("{\"expression\":\"" + expressionContent + "\""));
	}
}
