package com.calculator.web.tests.pageObjects.resources;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class CalculateResourcePage extends ResourcePage {
	
	public static String CALCULATE_EXPRESSION_SPECIFIC_PATH = "/calculate";
	public static String CALCULATE_EXPRESSION_URL_EXPRESSION_PARAMETER = "expression";
	
	public CalculateResourcePage(URL baseUrl) {
		super(baseUrl);
	}
	
	public Response getResourceContent(String expressionContent) throws IOException {
		URL calculationServiceUrl = getCalculationRequestURL(expressionContent);
    	
    	Client client= ClientBuilder.newClient();
    	WebTarget webTarget = client.target(URI.create(calculationServiceUrl.toExternalForm()));
    	return webTarget.request(MediaType.APPLICATION_JSON).get(Response.class);
	}
	
	private URL getCalculationRequestURL(String expressionContent) throws UnsupportedEncodingException, MalformedURLException {
		String urlEncodedExpression = getUrlEncodedExpression(expressionContent);
		String expressionQuery = buildUrlQueryFrom(CALCULATE_EXPRESSION_URL_EXPRESSION_PARAMETER, urlEncodedExpression);
		String urlAfterBase = BASE_API_PATH + CALCULATE_EXPRESSION_SPECIFIC_PATH + URL_QUERY_SEPARATOR + expressionQuery;
		
		return new URL(baseUrl, urlAfterBase);
	}
	
	private String buildUrlQueryFrom(String parameterName, String parameterValue) {
		return parameterName + "=" + parameterValue;
	}
	
	private String getUrlEncodedExpression(String expressionContent) throws UnsupportedEncodingException {
		return URLEncoder.encode(expressionContent, StandardCharsets.UTF_8.toString());
	}
}
