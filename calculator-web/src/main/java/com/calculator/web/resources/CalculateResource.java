package com.calculator.web.resources;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.calculator.web.CalculationResult;

import javax.ws.rs.GET;

@Path("/calculate")
public class CalculateResource {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public CalculationResult getCalculationResult() {
		CalculationResult result = new CalculationResult("5");
		
		return result;
	}
}
