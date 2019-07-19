package com.calculator.core.exception;

public class EmptyExpressionException extends CalculatorException 
{
	private static final long serialVersionUID = -2587724148179971977L;

	public EmptyExpressionException(String message) 
	{
		super(message);
	}
}
