package com.calculator;

import java.util.regex.Pattern;

import com.calculator.exception.*;

public class ExpressionParser 
{
	private static final int OPERATOR_LENGTH_IN_CHARS = 1;
	
	private String expression;
	
	private ExpressionParser()
	{}
	
	private ExpressionParser(String expression) throws CalculatorException
	{
		expression = this.stripSpaces(expression);
		this.validate(expression);
		expression = this.splitTokensWithIntervals(expression).trim();
		
		this.expression = expression;
	}
	
	public static ExpressionParser constructFromExpression(String expression) throws CalculatorException
	{
		return new ExpressionParser(expression);
	}
	
	private void validate(String expression) throws CalculatorException
	{
		this.validateBeginning(expression);
	}
	
	private void validateBeginning(String expression) throws OperatorMisplacementException
	{
		if (!Pattern.matches("^[^.)*\\/^]+.*", expression))
		{
			final int FIRST_CHAR_INDEX = 0;
			
			throw new OperatorMisplacementException(
					"Expression should not begin with operators or special symbols.",
					expression.substring(FIRST_CHAR_INDEX, ExpressionParser.OPERATOR_LENGTH_IN_CHARS)
			);
		}
	}
	
	private void validateEnd(String expression) throws OperatorMisplacementException
	{
		if (!expression.matches(".*[0-9)]$"))
		{
			final int LAST_INDEX = expression.length() - 1 ;
			
			throw new OperatorMisplacementException(
					"Expression should not end nor end with operators or special symbols.",
					expression.substring(LAST_INDEX - ExpressionParser.OPERATOR_LENGTH_IN_CHARS, LAST_INDEX)
			);
		}
	}
	
	private String stripSpaces(String expression)
	{
		return expression.replaceAll(" ", "");
	}

	private String splitTokensWithIntervals(String expression)
	{
		expression = this.splitNumbersWithIntervals(expression);
		expression = this.splitOperatorsWithIntervals(expression);
		
		return expression;
	}
	
	private String splitNumbersWithIntervals(String expression)
	{
		return expression.replaceAll("([0-9.]+)", " $1");
	}
	
	private String splitOperatorsWithIntervals(String expression)
	{
		return expression.replaceAll("([-+*\\/^()]{1})", " $1");
	}

	public String getExression()
	{
		return this.expression;
	}
}
