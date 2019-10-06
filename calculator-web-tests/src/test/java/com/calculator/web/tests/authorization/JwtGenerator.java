package com.calculator.web.tests.authorization;

import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.HashMap;

import org.json.JSONObject;

public class JwtGenerator {
	
	public String getMockedJwt(HashMap<String, String> header, HashMap<String, String> payload, String signature) {
		Encoder base64Encoder = Base64.getEncoder();
		
		JSONObject jwtHeader = generateJsonObjectFromMap(header);
		JSONObject jwtPayload = generateJsonObjectFromMap(payload);
		
		String jwt = base64Encoder.encodeToString(jwtHeader.toString().getBytes());
		jwt += ".";
		jwt += base64Encoder.encodeToString(jwtPayload.toString().getBytes());
		jwt += ".";
		jwt += base64Encoder.encodeToString(signature.getBytes());
		
		return jwt;
	}
	
	private JSONObject generateJsonObjectFromMap(HashMap<String, String> fields) {
		JSONObject jsonObject = new JSONObject();
		
		fields.forEach((key, value) -> {
			jsonObject.put(key, value);
		});
		
		return jsonObject;
	}
}
