package com.calculator.core;

public abstract class CalculatorDecorator implements iCalculator {
	protected iCalculator calculator;

	public CalculatorDecorator(iCalculator calculator) {
		this.calculator = calculator;
	}
}
