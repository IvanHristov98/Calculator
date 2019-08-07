package com.calculator.web.tests;

import java.net.URL;

import javax.inject.Inject;
 
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.exporter.StreamExporter;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
 
@RunWith(Arquillian.class)
public class GreeterTest {
 
    @Inject
    Greeter greeter;
    
    //@ArquillianResource
    //private URL base;
 
    @Deployment
    public static WebArchive createDeployment() {
    	JavaArchive jar = ShrinkWrap.create(JavaArchive.class).addAsDirectories("target/lib/calculator-web-1.0-SNAPSHOT-classes.jar");
        WebArchive war =  ShrinkWrap.create(WebArchive.class)
            .addClass(Greeter.class)
            .addClass(PhraseBuilder.class)
            .addAsLibraries(jar)
            .addAsManifestResource("arquillian.xml")
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
            .setWebXML("WEB-INF/web.xml");
 
        System.out.println(war.toString(true));
 
        return war;
    }
 
    @Test
    public void should_create_greeting() {
    	Assert.assertNotNull(greeter);
       // Assert.assertEquals("Hello, Earthling!", greeter.createGreeting("Earthling"));
        //greeter.greet(System.out, "Earthling");
    }
}