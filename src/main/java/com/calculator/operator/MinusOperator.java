package com.calculator.operator;

public final class MinusOperator extends Operator 
{
	protected static final int PRIORITY = 0;
	
	@Override
	public int getPriority() 
	{
		return MinusOperator.PRIORITY;
	}

	@Override
	public boolean isLeftAssociative() 
	{
		return true;
	}

}
