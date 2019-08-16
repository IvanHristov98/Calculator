package com.calculator.web.resources;

import com.calculator.web.wrappers.db.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/calculations")
public class CalculationsResource {
	@Inject
	ObjectMapper objectMapper;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCalculations() throws SQLException, JsonProcessingException, ClassNotFoundException {
		IDbConnection connection = DbConnection.getInstance();
		Statement statement = connection.getConnection().createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM valid_expression_calculations");
		
		return Response.ok().entity(objectMapper.writeValueAsString("asdas")).build();
	}
}
