package com.calculator.web.tests.pageObjects.resources;

import java.net.*;

import javax.ws.rs.client.*;
import javax.ws.rs.core.*;

public class CalculationResultsResourcePage extends ResourcePage {

	public static final String CALCULATION_RESULTS_SPECIFIC_PATH = "/calculationResults";
	
	public CalculationResultsResourcePage(URL baseUrl) {
		super(baseUrl);
	}

	@Override
	public Response getResourceContent(String expressionContent) throws Exception {
		URL calculationResultsResourceUrl = getCalculationResultsResourceUrl();
		
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(URI.create(calculationResultsResourceUrl.toExternalForm()));
		return webTarget.request(MediaType.APPLICATION_JSON).get(Response.class);
	}
	
	private URL getCalculationResultsResourceUrl() throws MalformedURLException {
		String urlAfterBase = BASE_API_PATH + CALCULATION_RESULTS_SPECIFIC_PATH;
		return new URL(baseUrl, urlAfterBase);
	}

}
