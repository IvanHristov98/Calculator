package com.calculator.core;

import com.calculator.core.exception.CalculatorException;

public class Calculator {
	private Expression expression;
	private UnformattedInfixExpressionValidator infixValidator;
	private InfixExpressionFormatter infixFormatter;
	private InfixToPostfixExpressionTranslator postfixTranslator;
	private PostfixExpressionCalculator postfixCalculator;

	public Calculator(Expression expression, UnformattedInfixExpressionValidator infixValidator,
			InfixExpressionFormatter infixFormatter, InfixToPostfixExpressionTranslator postfixTranslator,
			PostfixExpressionCalculator postfixCalculator) {
		this.expression = expression;
		this.infixValidator = infixValidator;
		this.infixFormatter = infixFormatter;
		this.postfixTranslator = postfixTranslator;
		this.postfixCalculator = postfixCalculator;
	}

	public Double calculate() throws CalculatorException {
		Expression resultExpression = this.getCalculationResult(this.expression.clone());
		return Double.valueOf(resultExpression.getContent());
	}

	private Expression getCalculationResult(Expression expression) throws CalculatorException {
		this.infixValidator.setExpression(expression);
		expression = this.infixValidator.process();

		this.infixFormatter.setExpression(expression);
		expression = this.infixFormatter.process();

		this.postfixTranslator.setExpression(expression);
		expression = this.postfixTranslator.process();

		this.postfixCalculator.setExpression(expression);
		return this.postfixCalculator.process();
	}
}