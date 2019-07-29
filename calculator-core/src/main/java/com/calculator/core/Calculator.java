package com.calculator.core;

import com.calculator.core.exception.CalculatorException;

public class Calculator {
	private UnformattedInfixExpressionValidator infixValidator;
	private InfixExpressionFormatter infixFormatter;
	private InfixToPostfixExpressionTranslator postfixTranslator;
	private PostfixExpressionCalculator postfixCalculator;

	public Calculator(UnformattedInfixExpressionValidator infixValidator,
			InfixExpressionFormatter infixFormatter, InfixToPostfixExpressionTranslator postfixTranslator,
			PostfixExpressionCalculator postfixCalculator) {
		this.infixValidator = infixValidator;
		this.infixFormatter = infixFormatter;
		this.postfixTranslator = postfixTranslator;
		this.postfixCalculator = postfixCalculator;
	}

	public Double calculate(Expression expression) throws CalculatorException {
		Expression resultExpression = this.getCalculationResult(expression);
		return Double.valueOf(resultExpression.getContent());
	}

	private Expression getCalculationResult(Expression expression) throws CalculatorException {
		expression = this.infixValidator.process(expression);
		expression = this.infixFormatter.process(expression);
		expression = this.postfixTranslator.process(expression);
		return this.postfixCalculator.process(expression);
	}
}