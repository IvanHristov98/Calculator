package com.testCalculator;

import java.util.regex.Pattern;

/**
 * This class accepts an expression and converts it into 
 * a reverse polish.
 * 
 * TODO Make it happen by the use of the Shunting Yard Algorithm.
 * 
 * @author I517939
 *
 */
class MathematicalExpressionParser 
{
	/**
	 * Reverse Polish Expression
	 */
	private ReversePolishExpression rpExpression;
	
	/**
	 * Initializer function.
	 */
	private void init()
	{
		this.rpExpression = new ReversePolishExpression();
	}
	
	/**
	 * Forcing only object construction via using an expression.
	 * Using this constructor only as a delegated one.
	 */
	private MathematicalExpressionParser()
	{
		this.init();
	}
	
	/**
	 * Constructs the object only via a mathematical expression.
	 * Left private as a clean code practice.
	 * 
	 * @param expression A mathematical expression
	 * @throws Exception
	 */
	private MathematicalExpressionParser(String expression) throws Exception
	{
		this();
		
		this.parseMathematicalExpression(expression);
	}
	
	/**
	 * Used in order to construct a MathematicalExpression object via an expression.
	 * 
	 * @param expression
	 * @return
	 * @throws Exception
	 */
	public static MathematicalExpressionParser constructFromExpression(String expression) throws Exception
	{
		return new MathematicalExpressionParser(expression);
	}
	
	/**
	 * Changes current expression with a new one.
	 * 
	 * @param expression
	 * @throws Exception
	 */
	public void changeExpression(String expression) throws Exception
	{
		this.init();
		
		this.parseMathematicalExpression(expression);
	}
	
	/** 
	 * Returns a copy of the inner Reverse polish notation.
	 * @return ReversePolishExpression
	 */
	public ReversePolishExpression getRPExpression()
	{
		return new ReversePolishExpression(this.rpExpression);
	}
	
	/**
	 * Accepts an expression and using Shunting yard algorithm transfers it into
	 * a reverse polish notation.
	 * 
	 * TODO Reverse Polish Notation
	 * @param expression The mathematical expression to be translated into Reverse Polish Notation.
	 * @throws Exception
	 */
	private void parseMathematicalExpression(String expression) throws Exception
	{
		boolean prevWasANum = false;
		
		String[] expressionElements = expression.split(" ");
	
		for (String element : expressionElements)
		{	
			if (!isNumber(element))
			{
				this.addOperation(element);
				
				prevWasANum = false;
			}
			else 
			{
				if (prevWasANum)
				{
					throw new NumberFormatException("Expected a number.");
				}
				
				this.addNumber(this.parseNumber(element));
				
				prevWasANum = true;
			}
		}
	}
	
	/**
	 * Checks whether a string can be parsed as a number.
	 * 
	 * @param element
	 * @return
	 */
	private boolean isNumber(String element)
	{
		return Pattern.matches("^(-|\\+){0,1}[0-9.]+$", element);
	}
	
	/**
	 * Parses a String into a Number object.
	 * 
	 * @param number
	 * @return
	 * @throws Exception
	 */
	private Double parseNumber(String number) throws Exception
	{
		if (!this.isNumber(number))
		{
			throw new Exception("Expected a number.");
		}
		
		return Double.parseDouble(number);
	}
	
	/**
	 * Adds a number within the Reverse Polish Number Stack.
	 * 
	 * @param number
	 */
	private void addNumber(Double number)
	{
		this.rpExpression.addNumber(number);
	}
	
	/**
	 * Adds an operation within the Reverse Polish Operation Stack.
	 * 
	 * @param operation
	 */
	private void addOperation(String operation) throws Exception
	{
		this.rpExpression.addOperation(operation);
	}
}
