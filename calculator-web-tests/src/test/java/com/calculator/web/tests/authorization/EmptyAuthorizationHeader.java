package com.calculator.web.tests.authorization;

import java.util.HashMap;

public class EmptyAuthorizationHeader extends AuthorizationHeader {
	
	public static final String STUB_SIGNATURE = "signature";

	public EmptyAuthorizationHeader(JwtGenerator jwtGenerator) {
		super(jwtGenerator);
	}
	
	@Override
	public String getAuthorizationHeaderValue() {
		HashMap<String, String> header = new HashMap<>();
		HashMap<String, String> payload = new HashMap<>();
		
		return BEARER + " " + jwtGenerator.getMockedJwt(header, payload, STUB_SIGNATURE);
	}
}
