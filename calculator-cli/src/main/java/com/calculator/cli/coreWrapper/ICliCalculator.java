package com.calculator.cli.coreWrapper;

import com.calculator.core.Expression;
import com.calculator.core.exception.CalculatorException;

public interface ICliCalculator {
	public Double calculate(Expression expression) throws Exception;
}
