package com.calculator.web.wrapper;

import com.calculator.core.Expression;
import com.calculator.web.wrapper.exception.WebCalculatorException;

public interface ICalculator {
	public Double calculate(Expression expression) throws WebCalculatorException;
}
