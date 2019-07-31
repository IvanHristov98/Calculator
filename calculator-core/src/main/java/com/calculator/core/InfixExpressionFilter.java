package com.calculator.core;

import com.calculator.core.exception.CalculatorException;

public class InfixExpressionFilter {
	private UnformattedInfixExpressionValidator infixValidator;
	private InfixExpressionFormatter infixFormatter;
	private InfixToPostfixExpressionTranslator postfixTranslator;

	public InfixExpressionFilter(UnformattedInfixExpressionValidator infixValidator,
			InfixExpressionFormatter infixFormatter, InfixToPostfixExpressionTranslator postfixTranslator) {
		this.infixValidator = infixValidator;
		this.infixFormatter = infixFormatter;
		this.postfixTranslator = postfixTranslator;
	}

	public Expression validateExpression(Expression expression) throws CalculatorException {
		return infixValidator.process(expression);
	}

	public Expression formatExpression(Expression expression) throws CalculatorException {
		return infixFormatter.process(expression);
	}

	public Expression translateToPostfix(Expression expression) throws CalculatorException {
		return postfixTranslator.process(expression);
	}
}
