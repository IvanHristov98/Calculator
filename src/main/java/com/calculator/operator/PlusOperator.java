package com.calculator.operator;

public final class PlusOperator extends Operator 
{
	protected static final int PRIORITY = 0;
	
	@Override
	public int getPriority() 
	{
		return PlusOperator.PRIORITY;
	}

	@Override
	public boolean isLeftAssociative() 
	{
		return true;
	}
}
