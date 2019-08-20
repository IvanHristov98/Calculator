package com.calculator.web.wrappers.db.dto;

public class ValidExpressionCalculation extends Calculation {
	
	private Double result;
	
	public ValidExpressionCalculation() {
		super();
	}

	public Double getResult() {
		return result;
	}

	public void setResult(Double result) {
		this.result = result;
	}
}
