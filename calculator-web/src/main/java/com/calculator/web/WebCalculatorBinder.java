package com.calculator.web;

import org.glassfish.jersey.internal.inject.AbstractBinder;

import com.calculator.web.wrappers.db.DatabaseUri;
import com.calculator.web.wrappers.db.JdbcCredentials;
import com.calculator.web.wrappers.db.dao.dbMappers.CalculationResult;
import com.calculator.web.wrappers.db.jdbcDrivers.DriverFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WebCalculatorBinder extends AbstractBinder {

	@Override
	protected void configure() {
		bind(ObjectMapper.class).to(ObjectMapper.class);
		bind(DriverFactory.class).to(DriverFactory.class);
		bind(JdbcCredentials.class).to(JdbcCredentials.class);
		bind(DatabaseUri.class).to(DatabaseUri.class);
		bind(CalculationResult.class).to(CalculationResult.class);
	}
}
