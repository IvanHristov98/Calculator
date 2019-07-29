package com.calculator.core;

import com.calculator.core.exception.CalculatorException;

public class Calculator {
	private InfixExpressionFilter infixExpressionFilter;
	private InfixToPostfixExpressionTranslator postfixTranslator;
	private PostfixExpressionCalculator postfixCalculator;

	public Calculator(InfixExpressionFilter infixExpressionFilter, InfixToPostfixExpressionTranslator postfixTranslator,
			PostfixExpressionCalculator postfixCalculator) {
		this.infixExpressionFilter = infixExpressionFilter;
		this.postfixTranslator = postfixTranslator;
		this.postfixCalculator = postfixCalculator;
	}

	public Double calculate(Expression expression) throws CalculatorException {
		Expression resultExpression = this.getCalculationResult(expression);
		return Double.valueOf(resultExpression.getContent());
	}

	private Expression getCalculationResult(Expression expression) throws CalculatorException {
		expression = this.infixExpressionFilter.validateExpression(expression);
		expression = this.infixExpressionFilter.formatExpression(expression);
		expression = this.postfixTranslator.process(expression);
		return this.postfixCalculator.process(expression);
	}
}