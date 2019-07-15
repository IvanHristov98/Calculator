package com.calculator.operator;

public final class PlusOperator extends ArithmeticOperator 
{
	protected static final int PRIORITY = 0;

	{
		this.symbolicRepresentation = Operators.PLUS;
	}
	
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

	@Override
	public Double operate(Double leftNumber, Double rightNumber) {
		return leftNumber + rightNumber;
	}
}
