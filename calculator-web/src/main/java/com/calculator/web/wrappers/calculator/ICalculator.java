package com.calculator.web.wrappers.calculator;

import com.calculator.core.Expression;
import com.calculator.web.wrappers.calculator.exception.WebCalculatorException;

public interface ICalculator {
	public Double calculate(Expression expression) throws WebCalculatorException;
}
