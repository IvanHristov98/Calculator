package com.calculator.core;

import com.calculator.core.exception.CalculatorException;

public interface ICalculator {
	public Double calculate(Expression expression) throws CalculatorException;
}
