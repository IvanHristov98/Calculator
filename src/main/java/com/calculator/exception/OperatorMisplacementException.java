package com.calculator.exception;

public final class OperatorMisplacementException extends OperatorException
{
	private static final long serialVersionUID = -7066236372174568388L;
	
	public OperatorMisplacementException(String message, String problematicOperator) 
	{
		super(message, problematicOperator);
	}
}
