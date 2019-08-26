package com.calculator.web;

import org.glassfish.jersey.internal.inject.AbstractBinder;

import com.calculator.web.wrappers.db.LocalJdbcEnvironment;
import com.calculator.web.wrappers.db.jdbcDrivers.DriverFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WebCalculatorBinder extends AbstractBinder {

	@Override
	protected void configure() {
		bind(ObjectMapper.class).to(ObjectMapper.class);
		bind(DriverFactory.class).to(DriverFactory.class);
		bind(LocalJdbcEnvironment.class).to(LocalJdbcEnvironment.class);
	}
}
