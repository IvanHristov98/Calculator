package com.calculator.web.resources;

import java.sql.Timestamp;
import java.time.Instant;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import com.calculator.core.CalculatorFactory;
import com.calculator.core.Expression;
import com.calculator.web.resources.representations.*;
import com.calculator.web.wrappers.db.dao.dbMappers.CalculationResult;
import com.calculator.web.wrappers.calculator.*;
import com.calculator.web.wrappers.calculator.exception.WebCalculatorException;
import com.calculator.web.wrappers.db.*;
import com.calculator.web.wrappers.db.dao.CalculationResultsDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import liquibase.exception.LiquibaseException;

import javax.ws.rs.GET;

@Path("/calculate")
public class CalculateResource {
	@Inject private ObjectMapper objectMapper;
	@Inject private DatabaseUri databaseUri;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCalculationResult(@QueryParam("expression") String expressionContent) throws JsonProcessingException, InterruptedException, LiquibaseException {
		CalculationResult calculationResult = null;
		Double evaluation = null;
		String message = null;
		
		try {
			evaluation = calculate(expressionContent);
			calculationResult = getCalculationResultAsPojo(expressionContent, evaluation, message);
			
			return Response.status(Response.Status.OK).entity(objectMapper.writeValueAsString(calculationResult)).build();
		} catch (WebCalculatorException exception) {
			calculationResult = getCalculationResultAsPojo(expressionContent, evaluation, exception.getMessage());
			
			Integer statusCode = Response.Status.BAD_REQUEST.getStatusCode();
			HttpError httpError = new HttpError(statusCode.toString(), exception.getMessage());
			
			return Response.status(statusCode).entity(objectMapper.writeValueAsString(httpError)).build();
		} finally {
			saveCalculationResult(calculationResult);
		}
	}
	
	private Double calculate(String expessionContent) throws WebCalculatorException  {
		CalculatorFactory factory = new CalculatorFactory();
		ICalculator calc = new CalculatorAdapter(factory.makeCalculator());
		Expression expression = new Expression(expessionContent);
		
		return calc.calculate(expression);
	}
	
	private CalculationResult getCalculationResultAsPojo(String expression, Double result, String message) {
		CalculationResult calculationResult = new CalculationResult();
		calculationResult.setExpression(expression);
		calculationResult.setEvaluation(result);
		calculationResult.setMoment(new Timestamp(Instant.now().toEpochMilli()));
		calculationResult.setMessage(message);
		
		return calculationResult;
	}
	
	private void saveCalculationResult(CalculationResult calculationResult) {
		try {
			DatabaseConnection managerSupplier = DatabaseConnection.getInstance(databaseUri);
			EntityManager entityManager = managerSupplier.getEntityManager();
			CalculationResultsDao calculationResultsDao = new CalculationResultsDao(entityManager);
			
			calculationResultsDao.save(calculationResult);
		} catch (Exception exception) {	
		}
	}
}
