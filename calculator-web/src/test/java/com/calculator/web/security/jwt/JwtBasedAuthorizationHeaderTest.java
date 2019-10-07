package com.calculator.web.security.jwt;

import org.junit.*;

import static org.junit.Assert.assertThat;

import static org.hamcrest.Matchers.*;

import org.mockito.Mock;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.Mockito.when;

import org.json.JSONObject;


public class JwtBasedAuthorizationHeaderTest {
	
	@Mock private JsonWebToken jwt;
	private JwtBasedAuthorizationHeader authorizationHeader;
	
	
	@Before
	public void setUp() {
		initMocks(this);
		
		authorizationHeader = new JwtBasedAuthorizationHeader(jwt);
	}
	
	@Test
	public void verifyJwtFieldExtraction() {
		final String mockedHeader = "Bearer abc.abc.abc";
		final String field = "email";
		final String fieldValue = "test@mail.com";
		
		JSONObject jwtPayload = new JSONObject();
		jwtPayload.put(field, fieldValue);
		
		when(jwt.getPayload()).thenReturn(jwtPayload.toString());
		
		assertThat(authorizationHeader.getJwtPayloadField(mockedHeader, field), equalTo(fieldValue));
	}
}
