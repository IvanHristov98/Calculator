package com.calculator.core;

import com.calculator.core.exception.CalculatorException;

public class Calculator implements iCalculator {
	private InfixExpressionFilter infixExpressionFilter;
	private PostfixExpressionCalculator postfixCalculator;

	public Calculator(InfixExpressionFilter infixExpressionFilter, PostfixExpressionCalculator postfixCalculator) {
		this.infixExpressionFilter = infixExpressionFilter;
		this.postfixCalculator = postfixCalculator;
	}

	public Double calculate(Expression expression) throws CalculatorException {
		Expression resultExpression = getCalculationResult(expression);
		return Double.valueOf(resultExpression.getContent());
	}

	private Expression getCalculationResult(Expression expression) throws CalculatorException {
		expression = infixExpressionFilter.validateExpression(expression);
		expression = infixExpressionFilter.formatExpression(expression);
		expression = infixExpressionFilter.translateToPostfix(expression);
		return postfixCalculator.process(expression);
	}
}