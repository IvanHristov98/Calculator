package com.calculator.web.resources;

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

@Path("/calculationResults")
public class CalculationResultsResource {
	@Inject ObjectMapper objectMapper;
	@Inject DatabaseUri databaseUri;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCalculations() throws JsonProcessingException, SQLException {
		DatabaseConnection managerSupplier = DatabaseConnection.getInstance(databaseUri);
		EntityManager entityManager = managerSupplier.getEntityManager();
		CalculationResultsDao calculationResultsDao = new CalculationResultsDao(entityManager);
		return Response.ok().entity(objectMapper.writeValueAsString(calculationResultsDao.getItems())).build();
	}
}
