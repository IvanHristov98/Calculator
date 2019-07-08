package com.calculator.exception;

public final class BracketsException extends OperatorMisplacementException
{
	private static final long serialVersionUID = -212012675847552595L;
	
	public BracketsException(String message) 
	{
		super(message);
	}
}