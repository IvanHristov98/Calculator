package com.calculator.web.resources;

import com.calculator.web.CorsHttpHeaders;
import com.calculator.web.aspects.DbInteractionModule;
import com.calculator.web.wrappers.db.dao.CalculationResultsDao;

import java.sql.SQLException;

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
	
	@Inject ObjectMapper objectMapper;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCalculations() throws JsonProcessingException, SQLException {
		Injector injector = Guice.createInjector(new DbInteractionModule());
		CalculationResultsDao calculationResultsDao = injector.getInstance(CalculationResultsDao.class);
		
		return Response.ok()
				.entity(objectMapper.writeValueAsString(calculationResultsDao.getItems()))
				.header(CorsHttpHeaders.AccessControlAllowOrigin.getHeaderName(), ALL_ORIGINS)
				.build();
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
