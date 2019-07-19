package com.calculator.core.exception;

public abstract class CalculatorException extends Exception
{
	private static final long serialVersionUID = 4383426569797774387L;
	
	public CalculatorException(String message)
	{
		super(message);
	}
}
