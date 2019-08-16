package com.calculator.web.resources.representations;

public class CalculationResult {
	private String calculationResult;
	
	public CalculationResult(String calculationResult) {
		this.setCalculationResult(calculationResult);
	}

	public String getCalculationResult() {
		return calculationResult;
	}

	public void setCalculationResult(String calculationResult) {
		this.calculationResult = calculationResult;
	}
}
