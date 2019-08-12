package com.calculator.web;

import com.calculator.core.CalculatorFactory;
import com.calculator.core.Expression;
import com.calculator.web.wrapper.*;

import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class MainController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		PrintWriter printWriter = response.getWriter();
		
		try {
			printWriter.print(getCaclulationResult(request));
		} catch (Exception e) {
			printWriter.print(e.getMessage());
		}
	}
	
	private String getCaclulationResult(HttpServletRequest request) throws Exception {
		String expressionContent = request.getParameter("calculate");
		
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
