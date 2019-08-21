package com.calculator.web.resources;

import java.sql.SQLException;
import java.time.Instant;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import com.calculator.core.CalculatorFactory;
import com.calculator.core.Expression;
import com.calculator.web.resources.representations.*;
import com.calculator.web.wrappers.calculator.*;
import com.calculator.web.wrappers.calculator.exception.WebCalculatorException;
import com.calculator.web.wrappers.db.DbConnection;
import com.calculator.web.wrappers.db.LocalJdbcEnvironment;
import com.calculator.web.wrappers.db.dao.CalculationResultsDao;
import com.calculator.web.wrappers.db.time.TimestampTranslator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.GET;

@Path("/calculate")
public class CalculateResource {
	@Inject private ObjectMapper objectMapper;
	@Inject private LocalJdbcEnvironment jdbcEnvironment;
	@Inject private TimestampTranslator timestampTranslator;
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCalculationResult(@QueryParam("expression") String expressionContent) throws JsonProcessingException {
		try {
			Double calculationResult = calculate(expressionContent);
			CalculationResult calculationResultAsPojo = getCalculationResultAsPojo(expressionContent, calculationResult);
			
			safeCalculationResult(calculationResultAsPojo);
			
			return Response.status(Response.Status.OK).entity(objectMapper.writeValueAsString(calculationResultAsPojo)).build();
		} catch (WebCalculatorException exception) {
			Integer statusCode = Response.Status.BAD_REQUEST.getStatusCode();
			HttpError httpError = new HttpError(statusCode.toString(), exception.getMessage());
			
			return Response.status(statusCode).entity(objectMapper.writeValueAsString(httpError)).build();
		}
	}
	
	private Double calculate(String expessionContent) throws WebCalculatorException  {
		CalculatorFactory factory = new CalculatorFactory();
		ICalculator calc = new CalculatorAdapter(factory.makeCalculator());
		Expression expression = new Expression(expessionContent);
		
		return calc.calculate(expression);
	}
	
	private CalculationResult getCalculationResultAsPojo(String expression, Double result) {
		CalculationResult calculationResult = new CalculationResult();
		calculationResult.setExpression(expression);
		calculationResult.setResult(result);
		calculationResult.setDate(Instant.now());
		
		return calculationResult;
	}
	
	private void safeCalculationResult(CalculationResult calculationResult) {
		try {
			DbConnection dbConnection = DbConnection.getInstance(jdbcEnvironment);
			CalculationResultsDao calculationResultsDao = new CalculationResultsDao(dbConnection, timestampTranslator);
			
			calculationResultsDao.save(calculationResult);
		} catch (SQLException exception) {	
		}
	}
}
