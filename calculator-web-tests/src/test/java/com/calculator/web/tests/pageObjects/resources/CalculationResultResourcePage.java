package com.calculator.web.tests.pageObjects.resources;

import java.net.MalformedURLException;
import java.net.URL;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.calculator.web.tests.authorization.*;

public class CalculationResultResourcePage extends ResourcePage {
	
	public static final String CALCULATION_RESULT_PATH = "/calculationResults/";
	
	private String id;

	public CalculationResultResourcePage(URL baseUrl, AuthorizationHeader authorizationHeader) {
		super(baseUrl, authorizationHeader);
	}
	
	public void setIdParameter(String id) {
		this.id = id;
	}

	@Override
	public Response getResourceContent() throws Exception {
		URL calculationResultUrl = getCalculationResultUrl(id);
		WebTarget webTarget = getWebTarget(calculationResultUrl);
    	
    	String authorizationHeaderContent = authorizationHeader.getAuthorizationHeaderValue();
		
		return webTarget
				.request(MediaType.APPLICATION_JSON)
				.header(authorizationHeader.getAuthorizationHeader(), authorizationHeaderContent)
				.get(Response.class);
	}
	
	private URL  getCalculationResultUrl(String id) throws MalformedURLException {
		String urlAfterBase = BASE_API_PATH + CALCULATION_RESULT_PATH + id;
		return new URL(baseUrl, urlAfterBase);
	}
}
