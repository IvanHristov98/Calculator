package com.calculator;

import java.util.regex.Pattern;

import com.calculator.exception.*;

public class ExpressionParser 
{	
	private Expression expression;
	
	private ExpressionParser()
	{}

	/**
	 * Given an expression it validates, modifies and saves it as a local field.
	 *
	 * @param  expression
	 * @return ExpressionParser
	 * @throws CalculatorException
	 */
	public static ExpressionParser constructFromExpression(Expression expression) throws CalculatorException
	{
		return new ExpressionParser(expression);
	}
	
	private ExpressionParser(Expression expression)
	{
		this.setExpression(expression);
	}
	
	public Expression getParsedExpression() throws CalculatorException
	{
		return Expression.constructFromExpressionContent(this.parseAndValidate(this.expression.getContent()));
	}
	
	private String parseAndValidate(String expression) throws CalculatorException
	{	
		expression = this.stripSpaces(expression);
		
		this.validateIfEmpty(expression);
		this.validateOperatorSequence(expression);
		this.validateNumberSequence(expression);
		this.validateTokens(expression);
		this.validateIfAnyNumbersAreGluedAroundBracketedExpression(expression);
		
		expression = this.recursivelyValidateAndStripEndings(expression);
		expression = this.wrapWithBrackets(expression);
		
		expression = this.splitTokensWithIntervals(expression).trim();
		
		return expression;
	}
	
	private void validateIfEmpty(String expression) throws EmptyExpressionException
	{
		if (expression.length() == 0)
		{
			throw new EmptyExpressionException("An empty expression is not a valid one.");
		}
	}

	private String stripSpaces(String expression)
	{
		return expression.replaceAll(" ", "");
	}
	
	private void validateOperatorSequence(String expression) throws OperatorMisplacementException
	{
		if (this.hasConsequentialOperators(expression))
		{
			throw new OperatorMisplacementException("The given expression contains consecutive operators.");
		}
	}
	
	private boolean hasConsequentialOperators(String expression)
	{
		// There should be no two consecutive operators -, /, +, ^
		return  Pattern.matches(".*[-*/+^]{2,}.*", expression);
	}
	
	private void validateNumberSequence(String expression) throws OperatorMisplacementException
	{
		if (this.hasConsequentialNumbers(expression))
		{
			throw new OperatorMisplacementException("The given expression contains consecutive numbers.");
		}
	}
	
	private boolean hasConsequentialNumbers(String expression)
	{
		// checks if there are two floating point numbers one after another
		// following the mantissa_1.exponent_1mantissa_2.exponent_2
		return Pattern.matches(".*[0-9]+\\.[0-9]*\\..*", expression);
	}

	private void validateTokens(String expression) throws OperatorMisplacementException
	{
		if (this.hasInvalidTokens(expression))
		{
			throw new OperatorMisplacementException("The given expression contains invalid tokens.");
		}
	}
	
	private boolean hasInvalidTokens(String expression)
	{
		// An expression should only contain the symbols -, +, *, /, ^, (, ), .,  0-9 
		return Pattern.matches(".*[^-+*/^()0-9.].*", expression);
	}
	
	private void validateIfAnyNumbersAreGluedAroundBracketedExpression(String expression) throws BracketsException
	{
		if (this.hasNumbersGluedAroundBracketExpression(expression))
		{
			throw new BracketsException(
					"It is not allowed to have numbers straight before or after a bracket symbol within an expression."
					);
		}
	}
	
	private boolean hasNumbersGluedAroundBracketExpression(String expression)
	{
		// Prevents having expressions with the format number1([sub_expression])number2
		return expression.matches(".*([0-9.]\\(|\\)[0-9]).*");
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
	
	public Expression getExression()
	{
		return this.expression.clone();
	}
	
	public void setExpression(Expression expression)
	{
		this.expression = expression;
	}
}
