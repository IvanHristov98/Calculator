package com.calculator.core.operator;

public final class LeftBracketOperator extends Operator 
{
	protected static final int PRIORITY = 3;

	{
		this.symbolicRepresentation = Operators.LEFT_BRACKET;
	}
	
	@Override
	public int getPriority() 
	{
		return LeftBracketOperator.PRIORITY;
	}
}
