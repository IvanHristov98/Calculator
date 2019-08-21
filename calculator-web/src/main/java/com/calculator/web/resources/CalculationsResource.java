package com.calculator.web.resources;

import com.calculator.web.resources.representations.HttpError;
import com.calculator.web.wrappers.db.*;
import com.calculator.web.wrappers.db.dao.CalculationResultsDao;
import com.calculator.web.wrappers.db.time.TimestampTranslator;

import java.sql.SQLException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/calculationResults")
public class CalculationsResource {
	@Inject ObjectMapper objectMapper;
	@Inject LocalJdbcEnvironment jdbcEnvironment;
	@Inject TimestampTranslator timestampTranslator;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCalculations() throws JsonProcessingException, SQLException {
		try {
			DbConnection dbConnection = DbConnection.getInstance(jdbcEnvironment);
			
			CalculationResultsDao calculationResultsDao = new CalculationResultsDao(dbConnection, timestampTranslator);
			return Response.ok().entity(objectMapper.writeValueAsString(calculationResultsDao.getItems())).build();
			
		} catch (SQLException exception) {
			Integer statusCode = Response.Status.BAD_REQUEST.getStatusCode();
			HttpError httpError = new HttpError(statusCode.toString(), exception.getMessage());
			
			return Response.status(statusCode).entity(objectMapper.writeValueAsString(httpError)).build();
		}
	}
}
