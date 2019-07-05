package com.calculator.exception;

public final class BracketsException extends OperatorCountException
{
	private static final long serialVersionUID = -212012675847552595L;
	
	public BracketsException(String message, String reason, String problematicOperator) 
	{
		super(message, reason, problematicOperator);
	}
}
