package com.calculator.web.tests.pageObjects;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

import org.apache.http.HttpResponse;
import org.apache.http.client.*;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.client.methods.HttpGet;

public class MainPage {
	public static String MAIN_CONTROLLER_URL = "/calculator-web/main";
	public static String CALCULATION_GET_REQUEST = "calculate";
	
	private URL baseUrl;
	
	public MainPage(URL baseUrl) {
		this.baseUrl = baseUrl;
	}
	
	public BufferedReader getPageResponseOnCalculationRequest(String expressionContent) throws ClientProtocolException, IOException {
		String urlEncodedExpression = getUrlEncodedExpression(expressionContent);
		URL test = new URL(baseUrl, MAIN_CONTROLLER_URL + "?" + CALCULATION_GET_REQUEST + "=" + urlEncodedExpression);
    	
    	HttpClient client  = HttpClientBuilder.create().build();
    	HttpResponse response = client.execute(new HttpGet(URI.create(test.toExternalForm())));
    	
    	return getBufferedReaderFromInputStream(response.getEntity().getContent());
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
