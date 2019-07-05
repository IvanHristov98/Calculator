package com.calculator.exception;

public class OperatorMisplacementException extends OperatorException
{
	private static final long serialVersionUID = -7066236372174568388L;
	
	public OperatorMisplacementException(String message, String reason, String problematicOperator) 
	{
		super(message, reason, problematicOperator);
	}
}
