package com.calculator.web.tests.pageObjects;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

import org.apache.http.HttpResponse;
import org.apache.http.client.*;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.client.methods.HttpGet;

public class CalculationServletPage {
	public static String CALCULATIONS_URL = "/calculator-web/v1/calculations";
	public static String URL_QUERY_SEPARATOR = "?";
	public static String CALCULATIONS_URL_EXPRESSION_PARAMETER = "expression";
	
	private URL baseUrl;
	
	public CalculationServletPage(URL baseUrl) {
		this.baseUrl = baseUrl;
	}
	
	public BufferedReader getPageResponseOnCalculationRequest(String expressionContent) throws ClientProtocolException, IOException {
		URL calculationServiceUrl = getCalculationRequestURL(expressionContent);
    	
    	HttpClient client  = HttpClientBuilder.create().build();
    	HttpResponse response = client.execute(new HttpGet(URI.create(calculationServiceUrl.toExternalForm())));
    	
    	return getBufferedReaderFromInputStream(response.getEntity().getContent());
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
	
	private BufferedReader getBufferedReaderFromInputStream(InputStream inputStream) {
    	return getBufferedReaderFromInputStreamReader(new InputStreamReader(inputStream));
	}
	
	private BufferedReader getBufferedReaderFromInputStreamReader(InputStreamReader inputStreamReader) {
		return new BufferedReader(inputStreamReader);
	}
}
