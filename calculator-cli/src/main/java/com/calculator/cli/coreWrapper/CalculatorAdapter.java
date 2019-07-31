package com.calculator.cli.coreWrapper;

import com.calculator.core.Expression;
import com.calculator.core.ICalculator;

public class CalculatorAdapter implements ICliCalculator{
	private ICalculator calculator;
	
	public CalculatorAdapter(ICalculator calculator) {
		this.calculator = calculator;
	}
	
	@Override
	public Double calculate(Expression expression) throws Exception {
		return calculator.calculate(expression);
	}
}
