package com.calculator.operator;

public final class RightBracketOperator extends Operator
{
	protected static final int PRIORITY = 3;

	{
		this.symbolicRepresentation = Operators.RIGHT_BRACKET;
	}
	
	@Override
	public int getPriority() 
	{
		return RightBracketOperator.PRIORITY;
	}
}
