package com.calculator.web.resources;

import com.calculator.web.CorsHttpHeaders;
import com.calculator.web.wrappers.db.*;
import com.calculator.web.wrappers.db.dao.CalculationResultsDao;

import java.sql.SQLException;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/calculationResults")
public class CalculationResultsResource {
	public static final String ALL_ORIGINS = "*";
	public static final String ALLOWED_HEADERS = "cache-control, content-type";
	public static final String ALLOWED_HTTP_METHODS = "GET";
	
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
	
	@OPTIONS
	public Response getCorsHeaders() {
		return Response.ok()
				.header(CorsHttpHeaders.AccessControlAllowOrigin.getHeaderName(), ALL_ORIGINS)
				.header(CorsHttpHeaders.AccessControlAllowHeaders.getHeaderName(), ALLOWED_HEADERS)
				.header(CorsHttpHeaders.AccessControlAllowMethods.getHeaderName(), ALLOWED_HTTP_METHODS)
				.entity("")
				.build();
	}
}
