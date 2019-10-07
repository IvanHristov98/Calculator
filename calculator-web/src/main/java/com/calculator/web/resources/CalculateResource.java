package com.calculator.web.resources;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import com.calculator.web.wrappers.db.dao.dbMappers.CalculationResult;
import com.calculator.web.wrappers.db.dao.dbMappers.CalculationStatus;
import com.calculator.web.aspects.DbInteractionModule;
import com.calculator.web.security.jwt.JwtBasedAuthorizationHeader;
import com.calculator.web.wrappers.db.dao.CalculationResultsDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Guice;
import com.google.inject.Injector;

@Path("/calculate")
public class CalculateResource {
	public static final String ALL_ORIGINS = "*";
	public static final String ALLOWED_HEADERS = "cache-control, content-type";
	public static final String ALLOWED_HTTP_METHODS = "POST";
	public static final String EMAIL_PROPERTY = "email";
	
	@Inject private ObjectMapper objectMapper;
	@Inject private CalculationResult calculationResult;
	@Inject private JwtBasedAuthorizationHeader authorizationHeader;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCalculationResult(String expressionContent, @HeaderParam(HttpHeaders.AUTHORIZATION) String authorization)
			throws JsonProcessingException, InterruptedException, SQLException {
		calculationResult.setExpression(expressionContent);
		calculationResult.setMoment(new Timestamp(Instant.now().toEpochMilli()));
		calculationResult.setStatus(CalculationStatus.PENDING);
		
		String email = authorizationHeader.getJwtPayloadField(authorization, EMAIL_PROPERTY);
		calculationResult.setEmail(email);
		
		saveCalculationResult(calculationResult);
		
		return Response.status(Response.Status.ACCEPTED)
				.entity(objectMapper.writeValueAsString(calculationResult.getRequestId()))
				.build();
	}
	
	private void saveCalculationResult(CalculationResult calculationResult) throws SQLException {
		Injector injector = Guice.createInjector(new DbInteractionModule());
		CalculationResultsDao calculationResultsDao = injector.getInstance(CalculationResultsDao.class);
		
		calculationResultsDao.save(calculationResult);
	}
}
