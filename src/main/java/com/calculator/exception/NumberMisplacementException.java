package com.calculator.exception;

public class NumberMisplacementException extends CalculatorException 
{
	private static final long serialVersionUID = -7188723167599841369L;

	public NumberMisplacementException(String message, String reason) 
	{
		super(message, reason);
	}
}
