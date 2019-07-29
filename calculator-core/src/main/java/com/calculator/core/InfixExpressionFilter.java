package com.calculator.core;

import com.calculator.core.exception.CalculatorException;

public class InfixExpressionFilter {
	private UnformattedInfixExpressionValidator infixValidator;
	private InfixExpressionFormatter infixFormatter;
	
	public InfixExpressionFilter(
			UnformattedInfixExpressionValidator infixValidator,
			InfixExpressionFormatter infixFormatter
			) {
		this.infixValidator = infixValidator;
		this.infixFormatter = infixFormatter;
	}
	
	public Expression validateExpression(Expression expression) throws CalculatorException {
		return this.infixValidator.process(expression);
	}
	
	public Expression formatExpression(Expression expression) throws CalculatorException {
		return this.infixFormatter.process(expression);
	}
}
