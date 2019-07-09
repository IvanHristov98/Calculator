package com.calculator.operator;

public final class ProductOperator extends Operator 
{
	protected static final int PRIORITY = 1;
	
	@Override
	public int getPriority() 
	{
		return ProductOperator.PRIORITY;
	}

	@Override
	public boolean isLeftAssociative() 
	{
		return true;
	}
}
