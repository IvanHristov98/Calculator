package com.calculator;

public class Calculator
{
	private Calculator(String expression) throws Exception
	{

	}
	
	public static Calculator constructFromExpression(String expression) throws Exception
	{
		return new Calculator(expression);
	}

	public Double calculate() throws Exception
	{
		// stub
		return 0.0d;
	}
}