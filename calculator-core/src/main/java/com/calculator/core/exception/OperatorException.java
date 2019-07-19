package com.calculator.core.exception;

public abstract class OperatorException extends CalculatorException
{
	private static final long serialVersionUID = -6753414124864400196L;

	public OperatorException(String message) 
	{
		super(message);
	}
}
