package com.calculator.web.resources;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.calculator.web.resources.representations.HttpError;
import com.calculator.web.wrappers.calculator.exception.WebCalculatorException;
import com.calculator.web.wrappers.db.LocalJdbcEnvironment;
import com.calculator.web.wrappers.db.dao.dbMappers.CalculationResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import liquibase.exception.LiquibaseException;

@Path("/save")
public class HanaResource {
	@Inject private ObjectMapper objectMapper;
	@Inject private LocalJdbcEnvironment jdbcEnvironment;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCalculationResult(@QueryParam("expression") String expressionContent) throws JsonProcessingException, InterruptedException, LiquibaseException, SQLException {
		
		return Response.status(Response.Status.OK).entity(objectMapper.writeValueAsString(jdbcEnvironment.getUser() + " " + jdbcEnvironment.getPassword() + " " + jdbcEnvironment.getDriverName())).build();
	}
}
