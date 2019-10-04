package com.calculator.web.resources;

import java.sql.SQLException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.calculator.web.CorsHttpHeaders;
import com.calculator.web.security.jwt.JsonWebToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/jwtDebug")
public class JwtDebugResource {
	
	@Inject private ObjectMapper objectMapper;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCalculationResult(@HeaderParam("Authorization") String jwtContent)
			throws JsonProcessingException, InterruptedException, SQLException {
		
		JsonWebToken jwt = new JsonWebToken();
		jwt.setContent(jwtContent);
		String jwtPayloadContent = jwt.getPayload();
		JSONObject jwtPayload = new JSONObject(jwtPayloadContent);
		
		return Response.status(Response.Status.OK)
				.header(CorsHttpHeaders.AccessControlAllowOrigin.getHeaderName(), "*")
				.entity(objectMapper.writeValueAsString(
						jwtPayload.get("email")
				)).build();
	}
}
