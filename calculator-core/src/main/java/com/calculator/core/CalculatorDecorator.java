package com.calculator.core;

public abstract class CalculatorDecorator implements ICalculator {
	protected ICalculator calculator;

	public CalculatorDecorator(ICalculator calculator) {
		this.calculator = calculator;
	}
}
