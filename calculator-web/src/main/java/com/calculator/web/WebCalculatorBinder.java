package com.calculator.web;

import org.glassfish.jersey.internal.inject.AbstractBinder;

import com.calculator.web.wrappers.db.dao.dbMappers.CalculationResult;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WebCalculatorBinder extends AbstractBinder {

	@Override
	protected void configure() {
		bind(ObjectMapper.class).to(ObjectMapper.class);
		bind(CalculationResult.class).to(CalculationResult.class);
	}
}
