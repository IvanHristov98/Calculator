package com.calculator.web.resources;

import java.sql.SQLException;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import com.calculator.web.CorsHttpHeaders;
import com.calculator.web.wrappers.db.DatabaseConnection;
import com.calculator.web.wrappers.db.DatabaseUri;
import com.calculator.web.wrappers.db.dao.CalculationResultsDao;
import com.calculator.web.wrappers.db.dao.dbMappers.CalculationResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/calculationResults/{id}")
public class CalculationResultResource {
	public static final String ALL_ORIGINS = "*";
	public static final String ALLOWED_HEADERS = "cache-control, content-type";
	public static final String ALLOWED_HTTP_METHODS = "GET";
	
	@Inject ObjectMapper objectMapper;
	@Inject DatabaseUri databaseUri;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCalculation(@NotNull(message = "Id must not be null.") @PathParam("id") int id) throws JsonProcessingException, SQLException {
		DatabaseConnection managerSupplier = DatabaseConnection.getInstance(databaseUri);
		EntityManager entityManager = managerSupplier.getEntityManager();
		CalculationResultsDao calculationResultsDao = new CalculationResultsDao(entityManager);
		CalculationResult result = calculationResultsDao.getItem(id);
		
		if (result != null) {
			return Response.ok().entity(objectMapper.writeValueAsString(result))
					.header(CorsHttpHeaders.AccessControlAllowOrigin.getHeaderName(), ALL_ORIGINS)
					.build();
		} else {
			return Response.status(Response.Status.NOT_FOUND)
					.header(CorsHttpHeaders.AccessControlAllowOrigin.getHeaderName(), ALL_ORIGINS)
					.build();
		}
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
