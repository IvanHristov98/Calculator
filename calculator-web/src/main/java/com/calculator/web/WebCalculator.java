package com.calculator.web;

import org.glassfish.jersey.server.ResourceConfig;

public class WebCalculator extends ResourceConfig {
	public WebCalculator() {
		register(new WebCalculatorBinder());
		packages(true, "com.calculator.web.resources");
	}
}
