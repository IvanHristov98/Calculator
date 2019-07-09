package com.calculator.operator;

public final class LeftBracketOperator extends Operator 
{
	protected static final int PRIORITY = 3;
	
	@Override
	public int getPriority() 
	{
		return LeftBracketOperator.PRIORITY;
	}
}
