package com.calculator.core;

import com.calculator.core.exception.*;

public class InfixExpressionFormatUnifier extends ExpressionContainer
{
	public InfixExpressionFormatUnifier(Expression expression)
	{
		super(expression);
	}
	
	public Expression process() throws CalculatorException
	{
		return new Expression(this.unify(this.expression.getContent()));
	}
	
	private String unify(String expression) throws CalculatorException
	{
		expression = StringUtility.stripSpaces(expression);
		expression = StringUtility.wrapStringWithBrackets(expression);
		expression = this.stripRedundantSymbolsAtBeginning(expression);
		expression = this.splitTokensWithIntervals(expression).trim();
		
		return expression;
	}
	
	private String stripRedundantSymbolsAtBeginning(String expression)
	{
		// There is no need to have + operators at the beginning of a subexpression
		return expression.replaceAll("(\\()[+]+", "$1");
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
