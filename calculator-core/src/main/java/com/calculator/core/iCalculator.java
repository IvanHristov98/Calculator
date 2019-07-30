package com.calculator.core;

import com.calculator.core.exception.CalculatorException;

public interface iCalculator {
	public Double calculate(Expression expression) throws CalculatorException;
}
