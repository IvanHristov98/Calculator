package com.calculator.web.tests.pageObjects.resources;

import java.net.*;

import javax.ws.rs.client.*;
import javax.ws.rs.core.*;

import com.calculator.web.tests.authorization.*;

public class CalculationResultsResourcePage extends ResourcePage {

	public static final String CALCULATION_RESULTS_PATH = "/calculationResults";
	
	public CalculationResultsResourcePage(URL baseUrl, AuthorizationHeader authorizationHeader) {
		super(baseUrl, authorizationHeader);
	}

	@Override
	public Response getResourceContent() throws Exception {
		URL calculationResultsResourceUrl = getCalculationResultsResourceUrl();
		WebTarget webTarget = getWebTarget(calculationResultsResourceUrl);
		
		String authorizationHeaderContent = authorizationHeader.getAuthorizationHeaderValue();
		
		return webTarget
				.request(MediaType.APPLICATION_JSON)
				.header(authorizationHeader.getAuthorizationHeader(), authorizationHeaderContent)
				.get(Response.class);
	}
	
	private URL getCalculationResultsResourceUrl() throws MalformedURLException {
		String urlAfterBase = BASE_API_PATH + CALCULATION_RESULTS_PATH;
		return new URL(baseUrl, urlAfterBase);
	}
}
