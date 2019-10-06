package com.calculator.web.tests.pageObjects.resources;

import java.io.*;
import java.net.*;

import javax.ws.rs.client.*;
import javax.ws.rs.core.*;

import com.calculator.web.tests.authorization.*;

public class CalculateResourcePage extends ResourcePage {
	
	public static String CALCULATE_EXPRESSION_SPECIFIC_PATH = "/calculate";
	public static String CALCULATE_EXPRESSION_URL_EXPRESSION_PARAMETER = "expression";
	
	private String expressionContent;
	
	public CalculateResourcePage(URL baseUrl, AuthorizationHeader authorizationHeader) {
		super(baseUrl, authorizationHeader);
	}
	
	public void setExpressionParameter(String expressionContent) {
		this.expressionContent = expressionContent;
	}
	
	public Response getResourceContent() throws IOException {
		URL calculationServiceUrl = getCalculationRequestURL(expressionContent);
    	WebTarget webTarget = getWebTarget(calculationServiceUrl);
    	
    	String authorizationHeaderContent = authorizationHeader.getAuthorizationHeaderValue();
    	
    	return webTarget
    			.request(MediaType.APPLICATION_JSON)
    			.header(authorizationHeader.getAuthorizationHeader(), authorizationHeaderContent)
    			.post(Entity.entity(expressionContent, MediaType.APPLICATION_JSON));
	}
	
	private URL getCalculationRequestURL(String expressionContent) throws UnsupportedEncodingException, MalformedURLException {
		String urlAfterBase = BASE_API_PATH + CALCULATE_EXPRESSION_SPECIFIC_PATH;
		
		return new URL(baseUrl, urlAfterBase);
	}
}
