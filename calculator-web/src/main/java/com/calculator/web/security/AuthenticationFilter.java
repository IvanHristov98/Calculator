package com.calculator.web.security;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.container.*;
import javax.ws.rs.core.*;

import org.json.JSONObject;

import com.calculator.web.security.jwt.JsonWebToken;

public class AuthenticationFilter implements ContainerRequestFilter {
	
	public static final String AUTHORIZATION_PROPERTY = "Authorization";

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		
		final MultivaluedMap<String, String> headers = requestContext.getHeaders();
		
		if (!headers.containsKey(AUTHORIZATION_PROPERTY)) {
			abortUnauthorizedRequest(requestContext);
		}
		
		final List<String> authProperty = headers.get(AUTHORIZATION_PROPERTY);
		
		if (authProperty.isEmpty()) {
			abortUnauthorizedRequest(requestContext);
		}
		
		String jwtContent = authProperty.get(0);
		JsonWebToken jwt = new JsonWebToken();
		jwt.setContent(jwtContent);
		String jwtPayloadContent = jwt.getPayload();
		JSONObject jwtPayload = new JSONObject(jwtPayloadContent);
		
		System.out.println(jwtPayload.get("email"));
	}
	
	private void abortUnauthorizedRequest(ContainerRequestContext requestContext) {
		requestContext.abortWith(
				Response.status(Response.Status.UNAUTHORIZED)
				.build()
		);
	}
}
