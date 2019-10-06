package com.calculator.web.tests;

import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.AfterClass;
import org.junit.ClassRule;
import org.junit.contrib.java.lang.system.EnvironmentVariables;

import com.calculator.web.tests.archive.WebCalculatorArchiveFactory;
import com.calculator.web.tests.pageObjects.db.DatabasePage;
import com.calculator.web.tests.pageObjects.resources.EnvironmentVariablesPage;

public abstract class ResourceIT {
	@ClassRule
	public static final EnvironmentVariables environmentVariables;
	
	protected static DatabasePage dbPage;
	private static EnvironmentVariablesPage environmentVariablesPage;
	
	static {
		environmentVariables = new EnvironmentVariables();
	}
	
	@ArquillianResource
	protected URL baseUrl;
	
    @Deployment(testable = false)
    public static WebArchive createDeployment() {
    	setUpEnvironmentVariables();
    	startDbServer();
    	
    	WebCalculatorArchiveFactory webCalculatorArchive = new WebCalculatorArchiveFactory();
        return webCalculatorArchive.makeArchive();
    }
    
    @AfterClass
    public static void tearDownClass() throws Exception {
    	dbPage.useDataSet(Datasets.EMPTY_DATA_SET.getPath());
    	dbPage.shutDownDatabaseServer();
    }
    
    private static void setUpEnvironmentVariables() {
    	environmentVariablesPage = new EnvironmentVariablesPage(environmentVariables);
    	environmentVariablesPage.mockEnvironmentVariables();
    }
    
    private static void startDbServer() {
    	try {
    		dbPage = new DatabasePage();
			dbPage.startDatabaseServer();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
    }
}
