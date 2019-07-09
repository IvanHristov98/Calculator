package com.calculator.operator;

public final class PowOperator extends ArithmeticOperator 
{
	protected static final int PRIORITY = 2;
	
	@Override
	public int getPriority() 
	{
		return PowOperator.PRIORITY;
	}

	@Override
	public boolean isLeftAssociative() 
	{
		return false;
	}
}
