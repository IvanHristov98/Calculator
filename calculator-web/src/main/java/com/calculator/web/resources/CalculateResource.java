package com.calculator.web.resources;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.calculator.core.CalculatorFactory;
import com.calculator.core.Expression;
import com.calculator.web.CalculationResult;
import com.calculator.web.resourceRepresentations.HttpError;
import com.calculator.web.wrapper.CalculatorAdapter;
import com.calculator.web.wrapper.ICalculator;
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
