package com.calculator.web.tests.authorization;

import java.util.HashMap;

public class StubAuthorizationHeader extends AuthorizationHeader {
	
	public static final String ALG_FIELD = "alg";
	public static final String EMAIL_FIELD = "email";
	
	public static final String STUB_ALG = "HS256";
	public static final String STUB_EMAIL = "test@mail.com";
	public static final String STUB_SIGNATURE = "signature";
	
	public StubAuthorizationHeader(JwtGenerator jwtGenerator) {
		super(jwtGenerator);
	}
	
	@Override
	public String getAuthorizationHeaderValue() {
		HashMap<String, String> header = getMockedHeader();
		HashMap<String, String> payload = getMockedPayload();
		
		return BEARER + " " + jwtGenerator.getMockedJwt(header, payload, STUB_SIGNATURE);
	}
	
	private HashMap<String, String> getMockedHeader() {
		HashMap<String, String> header = new HashMap<>();
		
		header.put(ALG_FIELD, STUB_ALG);
		
		return header;
	}
	
	private HashMap<String, String> getMockedPayload() {
		HashMap<String, String> payload = new HashMap<>();
		
		payload.put(EMAIL_FIELD, STUB_EMAIL);
		
		return payload;
	}
}
