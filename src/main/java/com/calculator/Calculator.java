package com.calculator;

import com.calculator.exception.CalculatorException;

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
		validator.validateExpression();

		ExpressionParser parser = new ExpressionParser(resultExpression);
		resultExpression = parser.getParsedExpression();



		ReversePolishNotationTranslator translator = new ReversePolishNotationTranslator(resultExpression);
		resultExpression = translator.getConvertedExpression();

		ReversePolishNotationCalculator calculator = new ReversePolishNotationCalculator(resultExpression);
		return calculator.getExpressionResult();
	}
}