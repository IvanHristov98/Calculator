package com.calculator.cli.coreWrapper;

import com.calculator.core.Expression;
import com.calculator.core.exception.CalculatorException;

public interface ICalculator {
	public Double calculate(Expression expression) throws Exception;
}
