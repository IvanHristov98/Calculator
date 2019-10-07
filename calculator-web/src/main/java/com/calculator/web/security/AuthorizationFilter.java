package com.calculator.web.security;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.container.*;
import javax.ws.rs.core.*;
import javax.ws.rs.ext.Provider;

import org.json.*;

import com.calculator.web.security.jwt.JwtBasedAuthorizationHeader;

@Provider
public class AuthorizationFilter implements ContainerRequestFilter {
	
	public static final int JWT_INDEX = 0;
	public static final String EMAIL_PROPERTY = "email";
	
	@Inject
	private JwtBasedAuthorizationHeader authorizationHeader;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		
		final MultivaluedMap<String, String> headers = requestContext.getHeaders();
		
		if (!headers.containsKey(HttpHeaders.AUTHORIZATION)) {
			abortUnauthorizedRequest(requestContext);
		}
		
		final List<String> authProperty = headers.get(HttpHeaders.AUTHORIZATION);
		
		if (authProperty.isEmpty()) {
			abortUnauthorizedRequest(requestContext);
		}
		
		String rawJwt = authProperty.get(JWT_INDEX);
		
		if (!isJwtPropertyPresent(rawJwt, EMAIL_PROPERTY)) {
			abortUnauthorizedRequest(requestContext);
		}
	}
	
	private void abortUnauthorizedRequest(ContainerRequestContext requestContext) {
		requestContext.abortWith(
				Response.status(Response.Status.UNAUTHORIZED)
				.build()
		);
	}
	
	private boolean isJwtPropertyPresent(String rawJwt, String propertyName) {
		try {
			authorizationHeader.getJwtPayloadField(rawJwt, propertyName);
		} catch (JSONException exception) {
			return false;
		}
		
		return true;
	}
}
