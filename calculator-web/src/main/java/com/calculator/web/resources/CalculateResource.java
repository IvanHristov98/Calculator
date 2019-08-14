package com.calculator.web.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import com.calculator.core.CalculatorFactory;
import com.calculator.core.Expression;
import com.calculator.web.resourceRepresentations.*;
import com.calculator.web.wrapper.*;
import com.calculator.web.wrapper.exception.WebCalculatorException;

import javax.ws.rs.GET;

@Path("/calculate")
public class CalculateResource {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCalculationResult(@QueryParam("expression") String expressionContent) {
		try {
			Double calculationResult = calculate(expressionContent);
			CalculationResult calculationResultAsPojo = new CalculationResult(calculationResult.toString());
			
			return Response.status(Response.Status.OK).entity(calculationResultAsPojo).build();
		} catch (WebCalculatorException exception) {
			HttpError httpError = new HttpError(Response.Status.BAD_REQUEST.toString(), exception.getMessage());
			return Response.status(Response.Status.BAD_REQUEST).entity(httpError).build();
		}
	}
	
	private Double calculate(String expessionContent) throws WebCalculatorException  {
		CalculatorFactory factory = new CalculatorFactory();
		ICalculator calc = new CalculatorAdapter(factory.makeCalculator());
		Expression expression = new Expression(expessionContent);
		
		return calc.calculate(expression);
	}
}
