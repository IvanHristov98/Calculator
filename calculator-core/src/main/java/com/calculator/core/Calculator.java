package com.calculator.core;

import com.calculator.core.exception.CalculatorException;

public class Calculator
{
	private Expression expression;
	private UnformattedInfixExpressionValidator infixValidator;
	private InfixExpressionFormatUnifier infixFormatter;
	private InfixToPostfixExpressionTranslator postfixTranslator;
	private PostfixExpressionCalculator postfixCalculator;

	public Calculator(
			Expression expression, 
			UnformattedInfixExpressionValidator infixValidator, 
			InfixExpressionFormatUnifier infixFormatter, 
			InfixToPostfixExpressionTranslator postfixTranslator,
			PostfixExpressionCalculator postfixCalculator
			)
	{
		this.expression = expression;
		this.infixValidator = infixValidator;
		this.infixFormatter = infixFormatter;
		this.postfixTranslator = postfixTranslator;
		this.postfixCalculator = postfixCalculator;
	}

	public Double calculate() throws CalculatorException
	{
		Expression resultExpression = this.expression.clone();

		this.infixValidator.setExpression(resultExpression);
		resultExpression = this.infixValidator.process();

		this.infixFormatter.setExpression(resultExpression);
		resultExpression = this.infixFormatter.process();

		this.postfixTranslator.setExpression(resultExpression);
		resultExpression = this.postfixTranslator.process();

		this.postfixCalculator.setExpression(resultExpression);
		resultExpression = this.postfixCalculator.process();
		
		return Double.valueOf(resultExpression.getContent());
	}
}