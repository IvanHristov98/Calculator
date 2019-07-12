package com.calculator.operator;

public final class DivisionOperator extends ArithmeticOperator 
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

	@Override
	public Double operate(Double leftNumber, Double rightNumber) {
		return leftNumber / rightNumber;
	}
}
