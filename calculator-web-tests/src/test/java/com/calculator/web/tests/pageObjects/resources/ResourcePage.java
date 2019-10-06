package com.calculator.web.tests.pageObjects.resources;

import java.net.URI;
import java.net.URL;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import com.calculator.web.tests.authorization.AuthorizationHeader;

public abstract class ResourcePage {
	
	public static String BASE_API_PATH = "/calculator-web/api/v1";
	
	protected URL baseUrl;
	protected AuthorizationHeader authorizationHeader;
	
	public ResourcePage(URL baseUrl,  AuthorizationHeader authorizationHeader) {
		this.baseUrl = baseUrl;
		this.authorizationHeader = authorizationHeader;
	}
	
	public abstract Response getResourceContent() throws Exception;
	
	protected WebTarget getWebTarget(URL url) {
		Client client = ClientBuilder.newClient();
		return client.target(URI.create(url.toExternalForm()));
	}
}
