package com.calculator.web;

import org.glassfish.jersey.server.ResourceConfig;

public class WebCalculatorConfig extends ResourceConfig {
	public WebCalculatorConfig() {
		register(new WebCalculatorBinder());
		packages(true, "com.calculator.web.resources");
	}
}
