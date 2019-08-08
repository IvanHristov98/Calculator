package com.calculator.web.tests;

import static org.junit.Assert.assertThat;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
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
import static org.hamcrest.CoreMatchers.equalTo;

import com.calculator.web.MainController;
 
@RunWith(Arquillian.class)
public class ServletGreeterTest {
	@ArquillianResource(MainController.class)
	private URL baseUrl;
	
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
    public void should_create_greeting() throws IOException, InterruptedException {
    	URL test = new URL(baseUrl, "calculator-web/main?calculate=5%2B5");
    	test.openStream();
    	
    	HttpClient client  = HttpClientBuilder.create().build();
    	HttpResponse response = client.execute(new HttpGet(URI.create(test.toExternalForm())));
    	
    	InputStream is = response.getEntity().getContent();
    	InputStreamReader isr = new InputStreamReader(is);
    	BufferedReader br = new BufferedReader(isr);
    	
    	assertThat(Double.parseDouble(br.readLine()), equalTo(10.0));
    	assertThat(br.read(), equalTo(-1));
    }
}