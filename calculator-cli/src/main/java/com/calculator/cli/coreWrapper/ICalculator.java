package com.calculator.cli.coreWrapper;

import com.calculator.cli.coreWrapper.exception.CliCalculatorException;
import com.calculator.core.Expression;

public interface ICalculator {
	public Double calculate(Expression expression) throws CliCalculatorException;
}
