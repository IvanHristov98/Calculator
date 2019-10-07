package com.calculator.web.resources;

import java.sql.SQLException;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import com.calculator.web.aspects.DbInteractionModule;
import com.calculator.web.security.jwt.JwtBasedAuthorizationHeader;
import com.calculator.web.wrappers.db.dao.CalculationResultsDao;
import com.calculator.web.wrappers.db.dao.dbMappers.CalculationResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Guice;
import com.google.inject.Injector;

@Path("/calculationResults/{id}")
public class CalculationResultResource {
	public static final String ALL_ORIGINS = "*";
	public static final String ALLOWED_HEADERS = "cache-control, content-type";
	public static final String ALLOWED_HTTP_METHODS = "GET";
	public static final String EMAIL_PROPERTY = "email";
	
	@Inject ObjectMapper objectMapper;
	@Inject private JwtBasedAuthorizationHeader authorizationHeader;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCalculation(@NotNull(message = "Id must not be null.") @PathParam("id") int id, @HeaderParam(HttpHeaders.AUTHORIZATION) String authorization) 
			throws JsonProcessingException, SQLException {
		Injector injector = Guice.createInjector(new DbInteractionModule());
		CalculationResultsDao calculationResultsDao = injector.getInstance(CalculationResultsDao.class);
		
		CalculationResult result = new CalculationResult();
		result.setRequestId(id);
		
		String email = authorizationHeader.getJwtPayloadField(authorization, EMAIL_PROPERTY);
		result.setEmail(email);
		
		result = calculationResultsDao.getItem(result);
		
		if (result != null) {
			return Response.ok()
					.entity(objectMapper.writeValueAsString(result))
					.build();
		} else {
			return Response.status(Response.Status.NOT_FOUND)
					.build();
		}
	}
}
