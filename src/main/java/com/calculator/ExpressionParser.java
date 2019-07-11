package com.calculator;

import java.util.regex.Pattern;

import com.calculator.exception.*;

public class ExpressionParser extends ExpressionContainer
{
	protected ExpressionParser(Expression expression)
	{
		super(expression);
	}
	
	public Expression getParsedExpression() throws CalculatorException
	{
		return Expression.constructFromExpressionContent(this.parseAndValidate(this.expression.getContent()));
	}
	
	private String parseAndValidate(String expression) throws CalculatorException
	{
		expression = this.stripSpaces(expression);
		expression = this.operateOnExpression(expression, this::stripRedundantSymbolsAtBeginning);
		expression = this.wrapWithBrackets(expression);
		expression = this.splitTokensWithIntervals(expression).trim();
		
		return expression;
	}
	
	private String stripRedundantSymbolsAtBeginning(String expression)
	{
		// There is no need to have -, + operators at the beginning
		return this.stripStringByPattern(expression, "^[+]+");
	}
	
	private String stripStringByPattern(String expression, String pattern)
	{	
		return expression.replaceAll(pattern, "");
	}

	private String wrapWithBrackets(String expression) 
	{
		return "(" + expression + ")";
	}
	
	private String splitTokensWithIntervals(String expression)
	{
		expression = this.splitNumbersWithIntervals(expression);
		expression = this.splitOperatorsWithIntervals(expression);
		expression = this.mergeNegativeNumbersAtBeginning(expression);
		
		return expression;
	}
	
	private String splitNumbersWithIntervals(String expression)
	{
		// Whenever a number of the format mantissa.exponent is found
		// a whitespace is added in front of it.
		return expression.replaceAll("([0-9.]+)", " $1");
	}
	
	private String splitOperatorsWithIntervals(String expression)
	{
		// Whenever a valid operator is found 
		// a whitespace character is added in front of it.
		return expression.replaceAll("([-+*/^()])", " $1");
	}

	private String mergeNegativeNumbersAtBeginning(String expression)
	{
		// A negative number can only be found at the start of the expression
		// or after an opening bracket.
		return expression.replaceFirst("(\\( -) ([0-9]+)", "$1$2");
	}
}
