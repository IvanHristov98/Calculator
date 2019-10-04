package com.calculator.web.security.jwt;

import java.util.Base64;
import java.util.Base64.Decoder;

public class JsonWebToken {
	
	public static final int NUMBER_OF_JWT_PARTS = 3;
	
	public static final String EMAIL_FIELD = "email";
	public static final String JWT_STRUCTURE = "^[^\\.]+\\.[^\\.]+\\.[^\\.]+$";
	public static final String INVALID_STRUCTURE_MESSAGE = "Error encountered. Invalid structure.";
	
	private String jwt;
	
	public JsonWebToken() {
	}
	
	public String getHeader() {
		String jwtHeaderContent = getDecodedJwtPart(JwtParts.Header.getIndex());
		
		return jwtHeaderContent;
	}
	
	public String getPayload() {
		String jwtPayloadContent = getDecodedJwtPart(JwtParts.Payload.getIndex());
		
		return jwtPayloadContent;
	}
	
	public String getSignature() {
		return getDecodedJwtPart(JwtParts.Signature.getIndex());
	}
	
	public void setContent(String jwt) {
		this.jwt = jwt;
	}
	
	public void validateJwt() throws JwtException {
		this.validateJwtStructure();
	}
	
	private String getDecodedJwtPart(int index) {
		Decoder base64Decoder = getBase64Decoder();
		String jwtPart = getJwtPart(index);
		
		return new String(base64Decoder.decode(jwtPart));
	}
	
	private String getJwtPart(int index) {
		String[] jwtParts = jwt.split("\\.");
		return jwtParts[index];
	}
	
	private Decoder getBase64Decoder() {
		return Base64.getDecoder();
	}
	
	private void validateJwtStructure() throws JwtException {
		if (!jwt.matches(JWT_STRUCTURE)) {
			throw new JwtException(INVALID_STRUCTURE_MESSAGE);
		}
	}
}
