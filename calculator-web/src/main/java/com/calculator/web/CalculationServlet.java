package com.calculator.web;

import com.calculator.core.CalculatorFactory;
import com.calculator.core.Expression;
import com.calculator.web.wrapper.*;
import com.calculator.web.wrapper.exception.WebCalculatorException;
import com.google.gson.Gson;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class CalculationServlet extends HttpServlet {
	
	public static final int NUMBER_OF_URL_PATH_ITEMS = 5;
	// Gson objects are stateless thus is it safe
	private Gson gson = new Gson();
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		
		PrintWriter printWriter = response.getWriter();
		
		String expressionContent = getExpressionContentFromHttpRequest(request);
		String urlDecodedExpressionContent = decodeURL(expressionContent);
		
		printWriter.print(urlDecodedExpressionContent);
		
		try {
			CalculationResult result = new CalculationResult(getCaclulationResult(request));
			printWriter.print(gson.toJson(result));
		} catch (Exception e) {
			HttpError httpError = new HttpError("400", e.getMessage());
			printWriter.print(gson.toJson(httpError));
		}
		finally {
			printWriter.flush();
		}
	}
	
	private String getCaclulationResult(HttpServletRequest request) throws MalformedURLException, WebCalculatorException, UnsupportedEncodingException {
		String expressionContent = getExpressionContentFromHttpRequest(request);
		String urlDecodedExpressionContent = decodeURL(expressionContent);
		return calculate(urlDecodedExpressionContent).toString();
	}
	
	private String getExpressionContentFromHttpRequest(HttpServletRequest request) throws MalformedURLException {
		URL requestUrl = new URL(request.getRequestURL().toString());
		return getExpressionContentFromRequestURL(requestUrl);
	}
	
	private String getExpressionContentFromRequestURL(URL requestUrl) {
		String[] requestUrlPathElements = requestUrl.getPath().split("/", NUMBER_OF_URL_PATH_ITEMS);
		return requestUrlPathElements[getExpressionIndexInUrlPathArray()];
	}
	
	private int getExpressionIndexInUrlPathArray() {
		return NUMBER_OF_URL_PATH_ITEMS-1;
	}
	
	private String decodeURL(String url) throws UnsupportedEncodingException {
		return URLDecoder.decode(url, "UTF-8");
	}
	
	private Double calculate(String expessionContent) throws WebCalculatorException  {
		CalculatorFactory factory = new CalculatorFactory();
		ICalculator calc = new CalculatorAdapter(factory.makeCalculator());
		Expression expression = new Expression(expessionContent);
		
		return calc.calculate(expression);
	}
}
