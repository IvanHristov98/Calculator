package com.calculator.web.tests.pageObjects;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

import javax.ws.rs.core.MediaType;

import org.apache.http.client.ClientProtocolException;
import com.sun.jersey.api.client.*;

public class CalculateResourcePage {
	public static String CALCULATIONS_URL = "/calculator-web/v1/calculate";
	public static String URL_QUERY_SEPARATOR = "?";
	public static String CALCULATIONS_URL_EXPRESSION_PARAMETER = "expression";
	
	private URL baseUrl;
	
	public CalculateResourcePage(URL baseUrl) {
		this.baseUrl = baseUrl;
	}
	
	public String getPageResponseOnCalculationRequest(String expressionContent) throws ClientProtocolException, IOException {
		URL calculationServiceUrl = getCalculationRequestURL(expressionContent);
    	
    	Client client= Client.create();
    	WebResource webResource = client.resource(URI.create(calculationServiceUrl.toExternalForm()));
    	ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
    	
    	return response.getEntity(String.class);
	}
	
	private URL getCalculationRequestURL(String expressionContent) throws UnsupportedEncodingException, MalformedURLException {
		String urlEncodedExpression = getUrlEncodedExpression(expressionContent);
		String expressionQuery = buildUrlQueryFrom(CALCULATIONS_URL_EXPRESSION_PARAMETER, urlEncodedExpression);
		String urlAfterBase = CALCULATIONS_URL + URL_QUERY_SEPARATOR + expressionQuery;
		
		return new URL(baseUrl, urlAfterBase);
	}
	
	private String buildUrlQueryFrom(String parameterName, String parameterValue) {
		return parameterName + "=" + parameterValue;
	}
	
	private String getUrlEncodedExpression(String expressionContent) throws UnsupportedEncodingException {
		return URLEncoder.encode(expressionContent, StandardCharsets.UTF_8.toString());
	}
}
