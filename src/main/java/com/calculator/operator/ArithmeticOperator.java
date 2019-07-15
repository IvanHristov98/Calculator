package com.calculator.operator;

import com.calculator.exception.CalculatorException;

public abstract class ArithmeticOperator extends Operator
{
	public abstract boolean isLeftAssociative();

	public abstract Double operate(Double leftNumber, Double rightNumber) throws CalculatorException;
}
