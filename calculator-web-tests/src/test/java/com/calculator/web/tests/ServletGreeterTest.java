package com.calculator.web.tests;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.exporter.StreamExporter;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import javax.transaction.UserTransaction;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.calculator.web.MainController;
 
@RunWith(Arquillian.class)
public class ServletGreeterTest {
	
    @Deployment(testable = false)
    public static WebArchive createDeployment() {
    	JavaArchive calculatorWeb = ShrinkWrap.create(ZipImporter.class)
    			.importFrom(new File("target/lib/calculator-web-1.0-SNAPSHOT-classes.jar"))
    			.as(JavaArchive.class);
        WebArchive war =  ShrinkWrap.create(WebArchive.class, "calculator-web.war")
            .addClass(Greeter.class)
            .addClass(PhraseBuilder.class)
            .addAsLibraries(calculatorWeb)
            .addAsManifestResource("arquillian.xml")
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
            .setWebXML("web.xml");
 
        System.out.println(war.toString(true));
 
        return war;
    }
 
    @Test
    public void should_create_greeting(@ArquillianResource(MainController.class) URL baseUrl) throws IOException, InterruptedException {
    	 //Client client = ClientBuilder.newClient();
         //WebTarget target = client.target(URI.create(new URL(base, "calculator-web/?calculate=4%2B5*5").toExternalForm()));
         
    	 //new URL(baseUrl, "/Test").openStream();
    	Assert.assertNotNull(baseUrl);
    	System.out.print((new URL(baseUrl, "/Test")).toString());
    	URL test = new URL(baseUrl, "calculator-web/main?calculate=5");
    	test.openStream();
    	

    	
    	//Client client = ClientBuilder.newClient();
    	//WebTarget target = client.target(URI.create(test.toExternalForm()));
    	
    	//Assert.assertNotNull(greeter);
    	//Assert.assertEquals("Hello, Earthling!", greeter.createGreeting("Earthling"));
        //greeter.greet(System.out, "Earthling");
    }
}