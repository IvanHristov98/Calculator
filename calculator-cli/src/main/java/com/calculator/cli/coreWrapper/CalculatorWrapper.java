package com.calculator.cli.coreWrapper;

import com.calculator.core.*;

public class CalculatorWrapper {
	private Expression expression;
	private CalculatorSupplier calculatorSupplier;
	
	public CalculatorWrapper(String expressionContent, CalculatorSupplier calculatorSupplier) {
		this.expression = new Expression(expressionContent);
		this.calculatorSupplier = calculatorSupplier;
	}
	
	public double calculate() {
		Calculator calculator;
		
		return 0.0d;
	}
}

