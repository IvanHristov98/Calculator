package com.calculator.web;

import org.glassfish.jersey.internal.inject.AbstractBinder;

import com.calculator.web.wrappers.db.LocalJdbcEnvironment;
import com.calculator.web.wrappers.db.time.TimestampTranslator;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WebCalculatorBinder extends AbstractBinder {

	@Override
	protected void configure() {
		bind(ObjectMapper.class).to(ObjectMapper.class);
		bind(LocalJdbcEnvironment.class).to(LocalJdbcEnvironment.class);
		bind(TimestampTranslator.class).to(TimestampTranslator.class);
	}
}
