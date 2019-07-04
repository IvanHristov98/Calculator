package com.testCalculator;

import java.util.Stack;
import java.util.regex.Pattern;

public class MathematicalExpressionParser<T extends Number> 
{
	private Stack<T> numbers;
	private Stack<String> operations;
	
	private MathematicalExpressionParser()
	{
		this.numbers = new Stack<T>();
		this.operations = new Stack<String>();
	}
	
	private MathematicalExpressionParser(String expression)
	{
		this();
		
		
	}
	
	public static <E extends Number> MathematicalExpressionParser<E> constructFromString(String expression)
	{
		return new MathematicalExpressionParser<E>(expression);
	}
	
	private parse(String expression) throws Exception, NumberFormatException
	{
		boolean prevWasANum = false;
		
		String[] expressionElements = expression.split(" ");
		
		// clear function
	
		for (String element : expressionElements)
		{
			boolean isNum = Pattern.matches("^[0-9]+$", element);
			
			if (!prevWasANum)
			{
				if (!isNum)
				{
					
					prevWasANum = false;
				}
				else 
				{
					
					prevWasANum = true;
				}
			}
			else
			{
				// don't permit two consecutive numbers
				if (isNum)
				{
					throw new NumberFormatException("Number must consist only of digits.");
				}
				
				prevWasANum = false;
			}
		}
	}
}
