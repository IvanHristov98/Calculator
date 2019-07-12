package com.calculator.operator;

public abstract class ArithmeticOperator extends Operator 
{
	public abstract boolean isLeftAssociative();

	public abstract Double operate(Double leftNumber, Double rightNumber);
}
