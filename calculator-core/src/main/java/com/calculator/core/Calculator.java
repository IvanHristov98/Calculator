package com.calculator.core;

import com.calculator.core.exception.CalculatorException;

public class Calculator {
	private InfixExpressionFilter infixExpressionFilter;
	private PostfixExpressionCalculator postfixCalculator;

	public Calculator(InfixExpressionFilter infixExpressionFilter, PostfixExpressionCalculator postfixCalculator) {
		this.infixExpressionFilter = infixExpressionFilter;
		this.postfixCalculator = postfixCalculator;
	}

	public Double calculate(Expression expression) throws CalculatorException {
		Expression resultExpression = this.getCalculationResult(expression);
		return Double.valueOf(resultExpression.getContent());
	}

	private Expression getCalculationResult(Expression expression) throws CalculatorException {
		expression = this.infixExpressionFilter.validateExpression(expression);
		expression = this.infixExpressionFilter.formatExpression(expression);
		expression = this.infixExpressionFilter.translateToPostfix(expression);
		return this.postfixCalculator.process(expression);
	}
}