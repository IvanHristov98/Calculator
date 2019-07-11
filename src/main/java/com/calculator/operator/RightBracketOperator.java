package com.calculator.operator;

public final class RightBracketOperator extends Operator
{
	protected static final int PRIORITY = 0;
	
	@Override
	public int getPriority() 
	{
		return MinusOperator.PRIORITY;
	}
}
