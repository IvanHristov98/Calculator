package com.calculator.web.tests.archive;

import java.io.File;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;

public class WebCalculatorArchiveFactory {

	public WebArchive makeArchive() {
		WebArchive calculatorWeb = ShrinkWrap.create(ZipImporter.class, "calculator-web.war")
    			.importFrom(new File("target"+File.separator+"wars"+File.separator+"calculator-web.war"))
    			.as(WebArchive.class)
    			.addAsManifestResource("arquillian.xml")
    			.addAsManifestResource("context.xml", "context.xml");
		
		calculatorWeb.delete("/WEB-INF/web.xml");
		calculatorWeb.delete("/WEB-INF/spring-security.xml");
		
		calculatorWeb.addAsWebInfResource("web.xml");
 
        return calculatorWeb;
	}
}
