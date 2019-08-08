package com.calculator.web.tests.pageObjects;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

public class MainPage {
	public static String MAIN_CONTROLLER_URL = "calculator-web/main";
	public static String CALCULATION_GET_REQUEST = "calculate";
	
	private URL baseUrl;
	
	public MainPage(URL baseUrl) {
		this.baseUrl = baseUrl;
	}
	
	public BufferedReader getPageResponseOnCalculationRequest(String expressionContent) throws ClientProtocolException, IOException {
		URL test = new URL(baseUrl, MAIN_CONTROLLER_URL + "?" + CALCULATION_GET_REQUEST + "=" + expressionContent);
    	
    	HttpClient client  = HttpClientBuilder.create().build();
    	HttpResponse response = client.execute(new HttpGet(URI.create(test.toExternalForm())));
    	
    	return getBufferedReaderFromInputStream(response.getEntity().getContent());
	}
	
	private BufferedReader getBufferedReaderFromInputStream(InputStream inputStream) {
    	return getBufferedReaderFromInputStreamReader(new InputStreamReader(inputStream));
	}
	
	private BufferedReader getBufferedReaderFromInputStreamReader(InputStreamReader inputStreamReader) {
		return new BufferedReader(inputStreamReader);
	}
}
