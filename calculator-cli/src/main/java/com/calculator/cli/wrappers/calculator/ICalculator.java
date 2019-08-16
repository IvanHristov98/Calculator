package com.calculator.cli.wrappers.calculator;

import com.calculator.cli.wrappers.calculator.exception.CliCalculatorException;
import com.calculator.core.Expression;

public interface ICalculator {
	public Double calculate(Expression expression) throws CliCalculatorException;
}
