package com.calculator.web.resources;

import com.calculator.web.resources.representations.HttpError;
import com.calculator.web.wrappers.db.*;
import com.calculator.web.wrappers.db.dao.CalculationResultsDao;

import java.sql.SQLException;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import liquibase.exception.LiquibaseException;

@Path("/calculationResults")
public class CalculationResultsResource {
	@Inject ObjectMapper objectMapper;
	@Inject LocalJdbcEnvironment jdbcEnvironment;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCalculations() throws JsonProcessingException, SQLException, LiquibaseException {
		try {
			DatabaseConnection managerSupplier = DatabaseConnection.getInstance(jdbcEnvironment);
			EntityManager entityManager = managerSupplier.getEntityManager();
			CalculationResultsDao calculationResultsDao = new CalculationResultsDao(entityManager);
			return Response.ok().entity(objectMapper.writeValueAsString(calculationResultsDao.getItems())).build();
			
		} catch (SQLException exception) {
			Integer statusCode = Response.Status.BAD_REQUEST.getStatusCode();
			HttpError httpError = new HttpError(statusCode.toString(), exception.getMessage());
			
			return Response.status(statusCode).entity(objectMapper.writeValueAsString(httpError)).build();
		}
	}
}
