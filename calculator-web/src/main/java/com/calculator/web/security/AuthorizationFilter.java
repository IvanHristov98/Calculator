package com.calculator.web.security;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.container.*;
import javax.ws.rs.core.*;

import org.json.*;

import com.calculator.web.security.jwt.JsonWebToken;

public class AuthorizationFilter implements ContainerRequestFilter {
	
	public static final String AUTHORIZATION_PROPERTY = "Authorization";
	public static final int JWT_INDEX = 0;
	public static final String EMAIL_PROPERTY = "email";

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
			JSONObject jwtPayload = getJwtPayload(rawJwt);
			getJsonProperty(jwtPayload, propertyName);
		} catch (JSONException exception) {
			return false;
		}
		
		return true;
	}
	
	private Object getJsonProperty(JSONObject jsonObject, String propertyName) {
		return jsonObject.get(propertyName);
	}
	
	private JSONObject getJwtPayload(String rawJwt) {
		JsonWebToken jwt = getParsedJwt(rawJwt);
		String jwtPayloadContent = jwt.getPayload();
		return new JSONObject(jwtPayloadContent);
	}
	
	private JsonWebToken getParsedJwt(String authorizationToken) {
		String jwtContent = authorizationToken;
		JsonWebToken jwt = new JsonWebToken();
		jwt.setContent(jwtContent);
		
		return jwt;
	}
}
