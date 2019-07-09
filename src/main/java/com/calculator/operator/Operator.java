package com.calculator.operator;

public abstract class Operator 
{
	public abstract int getPriority();
	
	public abstract boolean isLeftAssociative();
}
