package com.calculator.operator;

public final class DivisionOperator extends Operator 
{
	protected static final int PRIORITY = 1;
	
	@Override
	public int getPriority() 
	{
		return DivisionOperator.PRIORITY;
	}

	@Override
	public boolean isLeftAssociative() 
	{
		return true;
	}
}
