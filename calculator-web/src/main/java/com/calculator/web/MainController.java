package com.calculator.web;

import com.calculator.core.CalculatorFactory;
import com.calculator.core.Expression;
import com.calculator.web.wrapper.*;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class MainController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		PrintWriter printWriter = response.getWriter();
		
		try {
			printWriter.print(getCalculationResult(request.getParameter("calculate")));
		} catch (Exception e) {
			printWriter.print(e.getMessage());
		}
	}
	
	private String getCalculationResult(String expressionContent) throws Exception {
		if (expressionContent == null) {
			return "";
		}
		
		return calculate(expressionContent).toString();
	}
	
	private Double calculate(String expessionContent) throws Exception {
		CalculatorFactory factory = new CalculatorFactory();
		ICalculator calc = new CalculatorAdapter(factory.makeCalculator());
		Expression expression = new Expression(expessionContent);
		
		return calc.calculate(expression);
	}
}
