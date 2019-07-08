package com.calculator;

import java.util.regex.Pattern;

import com.calculator.exception.*;

public class ExpressionParser 
{	
	private String expression;
	
	public static ExpressionParser constructFromExpression(String expression) throws CalculatorException
	{
		return new ExpressionParser(expression);
	}
	
	private ExpressionParser(String expression) throws CalculatorException
	{
		this.expression = parse(expression);
	}

	private ExpressionParser()
	{}
	
	private String parse(String expression) throws CalculatorException
	{
		this.validateIfEmpty(expression);
		
		expression = this.stripSpaces(expression);
		
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
	
	private String recursivelyValidateAndStripEndings(String expression) throws OperatorMisplacementException, BracketsException
	{
		this.validateExpressionBeginning(expression);
		expression = this.stripRendundantSymbolsAtBeginning(expression);
		
		this.validateExpressionAtEnd(expression);
		
		int nextLevelOpeningBracketPosition = 0;
		boolean foundNextLevel = false;
		
		for (int i = 0; i < expression.length(); ++i)
		{
			if (expression.charAt(i) == '(')
			{
				nextLevelOpeningBracketPosition = i;
				foundNextLevel = true;
			}
			
			if (expression.charAt(i) == ')')
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
	
	private String stripRendundantSymbolsAtBeginning(String expression)
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

	private void validateOperatorSequence(String expression) throws OperatorMisplacementException
	{
		if (this.hasConsequentialOperators(expression))
		{
			throw new OperatorMisplacementException("The given expression contains consecutive operators.");
		}
	}
	
	private boolean hasConsequentialOperators(String expression)
	{
		return  Pattern.matches(".*[-*\\/+]{2,}.*", expression);
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
		return Pattern.matches(".*[^-+*\\/^()0-9.].*", expression);
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
		return expression.matches(".*([0-9.]\\(|\\)[0-9]).*");
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
		return expression.replaceAll("([0-9.]+)", " $1");
	}
	
	private String splitOperatorsWithIntervals(String expression)
	{
		return expression.replaceAll("([-+*\\/^()]{1})", " $1");
	}

	private String mergeNegativeNumbersAtBeginning(String expression)
	{
		return expression.replaceFirst("(\\( -) ([0-9]+)", "$1$2");
	}
	
	public String getExression()
	{
		return this.expression;
	}
}
