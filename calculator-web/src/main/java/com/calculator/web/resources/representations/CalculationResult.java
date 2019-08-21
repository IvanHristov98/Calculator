package com.calculator.web.resources.representations;

import java.time.Instant;

public class CalculationResult {
	private String expression;
	private Instant date;
	private double result;
	private String message;
	
	public CalculationResult() {
	}
	
	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public Instant getDate() {
		return date;
	}

	public void setDate(Instant date) {
		this.date = date;
	}

	public Double getResult() {
		return result;
	}

	public void setResult(Double result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
