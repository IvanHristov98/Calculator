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

		ExpressionValidator validator = new ExpressionValidator(resultExpression);
		resultExpression = validator.process();

		ExpressionParser parser = new ExpressionParser(resultExpression);
		resultExpression = parser.process();



		ReversePolishNotationTranslator translator = new ReversePolishNotationTranslator(resultExpression);
		resultExpression = translator.process();

		ReversePolishNotationCalculator calculator = new ReversePolishNotationCalculator(resultExpression);
		return Double.valueOf(calculator.process().getContent());
	}
}