package com.calculator.web.tests.pageObjects.resources;

import java.net.URL;

import javax.ws.rs.core.Response;

public abstract class ResourcePage {
	
	public static String BASE_API_PATH = "/calculator-web/v1";
	
	protected URL baseUrl;
	
	public ResourcePage(URL baseUrl) {
		this.baseUrl = baseUrl;
	}
	
	public abstract Response getResourceContent() throws Exception;
}
