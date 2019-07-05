package com.calculator.exception;

public final class InvalidOperatorException extends OperatorException 
{
	private static final long serialVersionUID = -546706523140104924L;

	public InvalidOperatorException(String message, String problematicOperator) 
	{
		super(message, problematicOperator);
	}
}
