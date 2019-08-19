package com.calculator.web.wrappers.db.dto;

public class InvalidExpressionCalculation extends Calculation {
	
	private String incorrectnessReason;

	public InvalidExpressionCalculation() {
		super();
	}
	
	public String getIncorrectnessReason() {
		return incorrectnessReason;
	}

	public void setIncorrectnessReason(String incorrectnessReason) {
		this.incorrectnessReason = incorrectnessReason;
	}
}
