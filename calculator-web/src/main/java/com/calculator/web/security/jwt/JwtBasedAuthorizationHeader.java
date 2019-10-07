package com.calculator.web.security.jwt;

import javax.inject.Inject;

import org.json.JSONObject;

public class JwtBasedAuthorizationHeader {
	
	private JsonWebToken jwt;
	
	@Inject
	public JwtBasedAuthorizationHeader(JsonWebToken jwt) {
		this.jwt = jwt;
	}
	
	public String getJwtPayloadField(String authorizationHeader, String propertyName) {
		String jwtPart = extractJwtFromHeader(authorizationHeader);
		JSONObject jwtPayload = getJwtPayload(jwtPart);
		
		return jwtPayload.getString(propertyName);
	}
	
	private String extractJwtFromHeader(String authorizationHeader) {
		String[] authorizationHeaderParts = authorizationHeader.split(" ");
		return authorizationHeaderParts[1];
	}
	
	private JSONObject getJwtPayload(String rawJwt) {
		JsonWebToken jwt = getParsedJwt(rawJwt);
		String jwtPayloadContent = jwt.getPayload();
		
		return new JSONObject(jwtPayloadContent);
	}
	
	private JsonWebToken getParsedJwt(String authorizationToken) {
		String jwtContent = authorizationToken;
		jwt.setContent(jwtContent);
		
		return jwt;
	}
}
