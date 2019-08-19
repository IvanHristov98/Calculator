package com.calculator.web.resources;

import com.calculator.web.resources.representations.HttpError;
import com.calculator.web.wrappers.db.*;
import com.calculator.web.wrappers.db.dao.InvalidExpressionCalculationDao;
import com.calculator.web.wrappers.db.exception.DbException;
import com.calculator.web.wrappers.db.time.TimestampTranslator;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/calculations")
public class CalculationsResource {
	@Inject
	ObjectMapper objectMapper;
	@Inject
	LocalJdbcEnvironment jdbcEnvironment;
	@Inject
	TimestampTranslator timestampTranslator;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCalculations(
			@QueryParam("expression") String expression, 
			@QueryParam("invalidExpressions") String invalidExpressions,
			@QueryParam("validExpressions") String validExpressions) throws JsonProcessingException, DbException {
		try {
			if (shouldGetAllCalculations(expression, invalidExpressions, validExpressions)) {
				return getAllCalculations();
			} else if (shouldGetOnlyInvalidExpressionCalculations(expression, invalidExpressions, validExpressions)) {
				return getInvalidExpressionCalculations();
			} else if (shouldGetOnlyValidExpressionCalculations(expression, invalidExpressions, validExpressions)) {
				return getValidExpressionCalculations();
			} else if (shouldGetSpecificCalculation(expression, invalidExpressions, validExpressions)) {
				return null;
			} else {
				throw new DbException("Inappropriate argument grouping has been encountered.");
			}
		} catch (DbException exception) {
			Integer statusCode = Response.Status.BAD_REQUEST.getStatusCode();
			HttpError httpError = new HttpError(statusCode.toString(), exception.getMessage());
			
			return Response.status(statusCode).entity(objectMapper.writeValueAsString(httpError)).build();
		}
	}
	
	private InvalidExpressionCalculationDao getInvalidExpressionCalculationDao() throws DbException {
		IDbConnection connection = DbConnection.getInstance(jdbcEnvironment);
		return new InvalidExpressionCalculationDao(connection, timestampTranslator);
	}
	
	private boolean shouldGetAllCalculations(String expression, String invalidExpressions, String validExpressions) {
		return invalidExpressions == null && validExpressions == null && expression == null;
	}
	
	private Response getAllCalculations() throws JsonProcessingException, DbException {
		InvalidExpressionCalculationDao calculationDao = getInvalidExpressionCalculationDao();
		return Response.ok().entity(objectMapper.writeValueAsString(calculationDao.getItems())).build();
	}
	
	private boolean shouldGetOnlyInvalidExpressionCalculations(String expression, String invalidExpressions, String validExpressions) {
		return invalidExpressions != null && validExpressions == null && expression == null;
	}
	
	private Response getInvalidExpressionCalculations() throws DbException, JsonProcessingException {
		InvalidExpressionCalculationDao calculationDao = getInvalidExpressionCalculationDao();
		return Response.ok().entity(objectMapper.writeValueAsString(calculationDao.getItems())).build();
	}
	
	private boolean shouldGetOnlyValidExpressionCalculations(String expression, String invalidExpressions, String validExpressions) {
		return validExpressions != null && invalidExpressions == null && expression == null;
	}
	
	private Response getValidExpressionCalculations() {
		return null;
	}
	
	private boolean shouldGetSpecificCalculation(String expression, String invalidExpressions, String validExpressions) {
		return validExpressions == null && invalidExpressions == null && expression != null;
	}
}
