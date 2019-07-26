package com.calculator.cli.coreWrapper;

import com.calculator.core.*;
import com.calculator.core.exception.CalculatorException;

public class CalculatorWrapper {
	private String expressionContent;
	private CalculatorSupplier calculatorSupplier;
	
	public CalculatorWrapper(String expressionContent, CalculatorSupplier calculatorSupplier) {
		this.expressionContent = expressionContent;
		this.calculatorSupplier = calculatorSupplier;
	}
	
	public double calculate() throws CalculatorException {
		Calculator calculator = this.calculatorSupplier.supplyCalculator(this.expressionContent);
		
		return calculator.calculate();
	}
}

