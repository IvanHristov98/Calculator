package com.calculator.web;

import org.glassfish.jersey.server.ResourceConfig;

import com.calculator.web.security.AuthorizationFilter;

public class WebCalculatorConfig extends ResourceConfig {
	public WebCalculatorConfig() {
		register(new WebCalculatorBinder());
		register(AuthorizationFilter.class);
		
		packages(true, "com.calculator.web.resources");
	}
}
