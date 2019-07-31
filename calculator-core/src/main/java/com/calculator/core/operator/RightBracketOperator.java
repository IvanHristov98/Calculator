package com.calculator.core.operator;

public final class RightBracketOperator extends Operator {
	protected static final int PRIORITY = 3;

	{
		symbolicRepresentation = Operators.RIGHT_BRACKET;
	}

	@Override
	public int getPriority() {
		return RightBracketOperator.PRIORITY;
	}
}
