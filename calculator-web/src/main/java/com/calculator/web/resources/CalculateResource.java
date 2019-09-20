package com.calculator.web.resources;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import com.calculator.web.wrappers.db.dao.dbMappers.CalculationResult;
import com.calculator.web.wrappers.db.dao.dbMappers.CalculationStatus;
import com.calculator.web.CorsHttpHeaders;
import com.calculator.web.wrappers.db.*;
import com.calculator.web.wrappers.db.dao.CalculationResultsDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/calculate")
public class CalculateResource {
	public static final String ALL_ORIGINS = "*";
	public static final String ALLOWED_HEADERS = "cache-control, content-type";
	public static final String ALLOWED_HTTP_METHODS = "POST";
	
	@Inject private ObjectMapper objectMapper;
	@Inject private DatabaseUri databaseUri;
	@Inject private CalculationResult calculationResult;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCalculationResult(String expressionContent)
			throws JsonProcessingException, InterruptedException, SQLException {
		calculationResult.setExpression(expressionContent);
		calculationResult.setMoment(new Timestamp(Instant.now().toEpochMilli()));
		calculationResult.setStatus(CalculationStatus.PENDING);
		
		saveCalculationResult(calculationResult);
		
		return Response.status(Response.Status.ACCEPTED)
				.header(CorsHttpHeaders.AccessControlAllowOrigin.getHeaderName(), ALL_ORIGINS)
				.entity(objectMapper.writeValueAsString(
				calculationResult.getRequestId()
				)).build();
	}
	
	@OPTIONS
	public Response getCorsHeaders() {
		return  Response.ok()
					.header(CorsHttpHeaders.AccessControlAllowOrigin.getHeaderName(), ALL_ORIGINS)
					.header(CorsHttpHeaders.AccessControlAllowHeaders.getHeaderName(), ALLOWED_HEADERS)
					.header(CorsHttpHeaders.AccessControlAllowMethods.getHeaderName(), ALLOWED_HTTP_METHODS)
					.entity("")
					.build();
	}
	
	private void saveCalculationResult(CalculationResult calculationResult) throws SQLException {
		DatabaseConnection managerSupplier = DatabaseConnection.getInstance(databaseUri);
		EntityManager entityManager = managerSupplier.getEntityManager();
		CalculationResultsDao calculationResultsDao = new CalculationResultsDao(entityManager);
		
		calculationResultsDao.save(calculationResult);
	}
}
