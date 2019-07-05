package com.calculator.exception;

public final class ConsecutiveTokensException extends OperatorMisplacementException 
{
	private static final long serialVersionUID = 22169140531918216L;
	
	public ConsecutiveTokensException(String message, String reason, String problematicOperator) 
	{
		super(message, reason, problematicOperator);
	}
}
