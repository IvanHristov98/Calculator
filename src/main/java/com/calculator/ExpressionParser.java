package com.calculator;

import java.util.regex.Pattern;

import com.calculator.exception.*;

public class ExpressionParser extends ExpressionContainer
{
	private ExpressionParser()
	{}
	
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
		expression = this.stripSpaces(expression); //



		
		expression = this.recursivelyValidateAndStripEndings(expression);
		expression = this.wrapWithBrackets(expression);
		
		expression = this.splitTokensWithIntervals(expression).trim();
		
		return expression;
	}
	
	private String recursivelyValidateAndStripEndings(String expression) throws OperatorMisplacementException, BracketsException
	{
		final char OPENING_BRACKET = '(';
		final char CLOSING_BRACKET = ')';
		
		this.validateExpressionBeginning(expression);
		expression = this.stripRedundantSymbolsAtBeginning(expression);
		
		this.validateExpressionAtEnd(expression);
		
		int nextLevelOpeningBracketPosition = 0;
		boolean foundNextLevel = false;
		
		for (int i = 0; i < expression.length(); ++i)
		{
			if (expression.charAt(i) == OPENING_BRACKET)
			{
				nextLevelOpeningBracketPosition = i;
				foundNextLevel = true;
			}
			
			if (expression.charAt(i) == CLOSING_BRACKET)
			{
				if (!foundNextLevel)
				{
					throw new BracketsException("The specified bracket ordering is invalid.");
				}
				
				foundNextLevel = false;
				
				expression = expression.substring(0, nextLevelOpeningBracketPosition + 1) +
						this.recursivelyValidateAndStripEndings(expression.substring(nextLevelOpeningBracketPosition + 1, i)) +
						expression.substring(i, expression.length());
			}
		}
		
		if (foundNextLevel)
		{
			throw new BracketsException("The specified bracket ordering is invalid.");
		}
		
		return expression;
	}
	
	private void validateExpressionBeginning(String expression) throws OperatorMisplacementException
	{
		// An expression should start only with -, +, (, 0-9
		String validExpressionBeginningPattern = "^[-+(0-9]+.*";
		
		this.validateStringByPattern(expression, validExpressionBeginningPattern);
	}
	
	private void validateStringByPattern(String expression, String validPattern) throws OperatorMisplacementException
	{
		if (!Pattern.matches(validPattern, expression))
		{
			throw new OperatorMisplacementException(
					"Expression is not ordered properly."
			);
		}
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
	
	private void validateExpressionAtEnd(String expression) throws OperatorMisplacementException
	{	
		// And expression should end only with 0-9, )
		String validEndPattern = ".*[0-9)]$";
		
		this.validateStringByPattern(expression, validEndPattern);
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
