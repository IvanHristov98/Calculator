package com.calculator.web.wrapper;

import com.calculator.core.Expression;

public interface ICalculator {
	public Double calculate(Expression expression) throws Exception;
}
