package com.calculator.web.security.jwt;

import java.util.Base64;
import java.util.Base64.Encoder;

import org.json.JSONObject;

import org.junit.*;

import static org.junit.Assert.assertThat;

import static org.hamcrest.Matchers.equalTo;

public class JsonWebTokenTest {
	
	public static final String ALG_FIELD = "alg";
	public static final String EMAIL_FIELD = "email";
	
	public static final String STUB_ALG = "HS256";
	public static final String STUB_EMAIL = "test@mail.com";
	public static final String STUB_SIGNATURE = "signature";
	
	private JsonWebToken jwt;
	
	@Before
	public void setUp() {
		String jwtContent = getMockedJwt();
		jwt = new JsonWebToken();
		jwt.setContent(jwtContent);
	}
	
	@Test
	public void verifyHeaderRetrieving() {
		String jwtHeaderContent = jwt.getHeader();
		JSONObject jwtHeader = new JSONObject(jwtHeaderContent);
		
		assertThat(jwtHeader.get(ALG_FIELD), equalTo(STUB_ALG));
	}
	
	@Test
	public void verifyPayloadRetrieving() {
		String jwtPayloadContent = jwt.getPayload();
		JSONObject jwtPayload = new JSONObject(jwtPayloadContent);
		
		assertThat(jwtPayload.get(EMAIL_FIELD), equalTo(STUB_EMAIL));
	}
	
	@Test
	public void verifySignatureRetrieving() {
		String jwtSignature = jwt.getSignature();
		
		assertThat(jwtSignature, equalTo(STUB_SIGNATURE));
	}
	
	@Test(expected = JwtException.class)
	public void verifyWrongJwtToken() throws JwtException {
		final String jwtContent = "a.b.c.d";
		jwt.setContent(jwtContent);
		
		jwt.validateJwt();
	}
	
	@Test
	public void verifyCorrectJwtToken() throws JwtException {
		jwt.validateJwt();
	}
	
	private String getMockedJwt() {
		Encoder base64Encoder = Base64.getEncoder();
		
		String jwt = base64Encoder.encodeToString(getMockedJwtHeader().toString().getBytes());
		jwt += ".";
		jwt += base64Encoder.encodeToString(getMockedJwtPayload().toString().getBytes());
		jwt += ".";
		jwt += base64Encoder.encodeToString(getMockedJwtSignature().getBytes());
		
		return jwt;
	}
	
	private JSONObject getMockedJwtHeader() {
		JSONObject jwtHeader = new JSONObject();
		jwtHeader.put(ALG_FIELD, STUB_ALG);
		
		return jwtHeader;
	}
	
	private JSONObject getMockedJwtPayload() {
		JSONObject jwtPayload = new JSONObject();
		jwtPayload.put(EMAIL_FIELD, STUB_EMAIL);
		
		return jwtPayload;
	}
	
	private String getMockedJwtSignature() {
		return STUB_SIGNATURE;
	}
}
