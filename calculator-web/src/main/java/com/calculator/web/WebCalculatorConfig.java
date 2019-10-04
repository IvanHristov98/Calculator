package com.calculator.web;

import org.glassfish.jersey.server.ResourceConfig;

import com.calculator.web.security.AuthenticationFilter;

public class WebCalculatorConfig extends ResourceConfig {
	public WebCalculatorConfig() {
		register(new WebCalculatorBinder());
		register(AuthenticationFilter.class);
		
		packages(true, "com.calculator.web.resources");
	}
}
