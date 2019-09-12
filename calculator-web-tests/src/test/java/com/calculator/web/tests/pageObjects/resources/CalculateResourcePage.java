package com.calculator.web.tests.pageObjects.resources;

import java.io.*;
import java.net.*;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.*;

public class CalculateResourcePage extends ResourcePage {
	
	public static String CALCULATE_EXPRESSION_SPECIFIC_PATH = "/calculate";
	public static String CALCULATE_EXPRESSION_URL_EXPRESSION_PARAMETER = "expression";
	
	private String expressionContent;
	
	public CalculateResourcePage(URL baseUrl) {
		super(baseUrl);
	}
	
	public void setExpressionParameter(String expressionContent) {
		this.expressionContent = expressionContent;
	}
	
	public Response getResourceContent() throws IOException {
		URL calculationServiceUrl = getCalculationRequestURL(expressionContent);
    	
    	WebTarget webTarget = getWebTarget(calculationServiceUrl);
    	
    	return webTarget.request(MediaType.APPLICATION_JSON).post(Entity.entity(expressionContent, MediaType.APPLICATION_JSON));
	}
	
	private URL getCalculationRequestURL(String expressionContent) throws UnsupportedEncodingException, MalformedURLException {
		String urlAfterBase = BASE_API_PATH + CALCULATE_EXPRESSION_SPECIFIC_PATH;
		
		return new URL(baseUrl, urlAfterBase);
	}
}
