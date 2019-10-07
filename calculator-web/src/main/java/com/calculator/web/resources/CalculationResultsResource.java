package com.calculator.web.resources;

import com.calculator.web.aspects.DbInteractionModule;
import com.calculator.web.security.jwt.JwtBasedAuthorizationHeader;
import com.calculator.web.wrappers.db.dao.CalculationResultsDao;
import com.calculator.web.wrappers.db.dao.dbMappers.CalculationResult;

import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Guice;
import com.google.inject.Injector;

@Path("/calculationResults")
public class CalculationResultsResource {
	public static final String ALL_ORIGINS = "*";
	public static final String ALLOWED_HEADERS = "cache-control, content-type";
	public static final String ALLOWED_HTTP_METHODS = "GET";
	public static final String EMAIL_PROPERTY = "email";
	
	@Inject ObjectMapper objectMapper;
	@Inject private JwtBasedAuthorizationHeader authorizationHeader;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCalculations(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorization) throws JsonProcessingException, SQLException {
		Injector injector = Guice.createInjector(new DbInteractionModule());
		CalculationResultsDao calculationResultsDao = injector.getInstance(CalculationResultsDao.class);

		CalculationResult result = new CalculationResult();
		
		String email = authorizationHeader.getJwtPayloadField(authorization, EMAIL_PROPERTY);
		result.setEmail(email);
		
		List<CalculationResult> calculationResults = calculationResultsDao.getItems(result);
		
		return Response.ok()
				.entity(objectMapper.writeValueAsString(calculationResults))
				.build();
	}
}
