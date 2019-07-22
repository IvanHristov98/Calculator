package com.calculator.core;

import com.calculator.core.exception.CalculatorException;

public class Calculator
{
	Expression expression;

	public Calculator(String expressionContent)
	{
		this.expression = new Expression(expressionContent);
	}

	public Double calculate() throws CalculatorException
	{
		Expression resultExpression = this.expression.clone();

		UnformattedInfixExpressionValidator validator = new UnformattedInfixExpressionValidator(resultExpression);
		resultExpression = validator.process();

		InfixExpressionFormatUnifier parser = new InfixExpressionFormatUnifier(resultExpression);
		resultExpression = parser.process();

		InfixToPostfixExpressionTranslator translator = new InfixToPostfixExpressionTranslator(resultExpression);
		resultExpression = translator.process();

		PostfixExpressionCalculator calculator = new PostfixExpressionCalculator(resultExpression);
		return Double.valueOf(calculator.process().getContent());
	}
}