package com.calculator.web.wrappers.db.dto;

import java.time.Instant;

public abstract class Calculation {
	
	private String expression;
	private Instant date;
	
	public Calculation() {
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
}
