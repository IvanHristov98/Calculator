package com.calculator.core;

import com.calculator.core.exception.*;

public abstract class ExpressionFilter {
	public ExpressionFilter() {
	}

	public abstract Expression process(Expression expression) throws CalculatorException;
}
