package com.calculator.web.tests.pageObjects.resources;

import java.net.*;

import javax.ws.rs.client.*;
import javax.ws.rs.core.*;

public class CalculationResultsResourcePage extends ResourcePage {

	public static final String CALCULATION_RESULTS_PATH = "/calculationResults";
	
	public CalculationResultsResourcePage(URL baseUrl) {
		super(baseUrl);
	}

	@Override
	public Response getResourceContent() throws Exception {
		URL calculationResultsResourceUrl = getCalculationResultsResourceUrl();
		WebTarget webTarget = getWebTarget(calculationResultsResourceUrl);
		
		return webTarget.request(MediaType.APPLICATION_JSON).get(Response.class);
	}
	
	private URL getCalculationResultsResourceUrl() throws MalformedURLException {
		String urlAfterBase = BASE_API_PATH + CALCULATION_RESULTS_PATH;
		return new URL(baseUrl, urlAfterBase);
	}
}
