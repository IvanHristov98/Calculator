package com.calculator.web.tests.authorization;

public abstract class AuthorizationHeader {
	
	public static final String AUTHORIZATION_HEADER = "Authorization";
	public static final String BEARER = "Bearer";
	
	protected JwtGenerator jwtGenerator;
	
	public AuthorizationHeader(JwtGenerator jwtGenerator) {
		this.jwtGenerator = jwtGenerator;
	}
	
	public abstract String getAuthorizationHeaderValue();

	public String getAuthorizationHeader() {
		return AUTHORIZATION_HEADER;
	}
}
