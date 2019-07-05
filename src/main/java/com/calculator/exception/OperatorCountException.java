package com.calculator.exception;

public final class OperatorCountException extends OperatorException
{
	private static final long serialVersionUID = -4937930580428296618L;
	
	public OperatorCountException(String message, String reason, String problematicOperator) 
	{
		super(message, reason, problematicOperator);
	}
}
