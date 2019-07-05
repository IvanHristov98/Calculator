package com.calculator.exception;

public final class ConsecutiveTokensException extends CalculatorException 
{
	private static final long serialVersionUID = 22169140531918216L;

	public ConsecutiveTokensException(String message, String reason)
	{
		super(message, reason);
	}
}
