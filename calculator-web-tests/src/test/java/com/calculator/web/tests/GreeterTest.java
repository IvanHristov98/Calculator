package com.calculator.web.tests;
 
import java.io.File;
 
import javax.inject.Inject;
 
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.impl.maven.MavenDependencyResolverSettings;;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
 
@RunWith(Arquillian.class)
public class GreeterTest {
 
    @Inject
    Greeter greeter;
 
    @Deployment
    public static WebArchive createDeployment() {
       /* File[] lib = MavenConverter.
                .resolve("org.jboss.weld.servlet:weld-servlet:1.1.9.Final")
                .withTransitivity().as(File.class);*/
 
        WebArchive war =  ShrinkWrap.create(WebArchive.class)
            .addClass(Greeter.class)
            .addClass(PhraseBuilder.class)
            .addAsManifestResource("arquillian.xml")
           // .addAsLibraries(lib)
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
            .setWebXML("WEB-INF/web.xml")
            ;
 
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