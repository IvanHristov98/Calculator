package com.calculator.exception;

public abstract class CalculatorException extends Exception
{
	private static final long serialVersionUID = 4383426569797774387L;
	private String reason;
	
	public CalculatorException(String message, String reason)
	{
		super(message);
		
		this.reason = reason;
	}
	
	public void printReason()
	{
		System.out.println(this.reason);
	}
}
