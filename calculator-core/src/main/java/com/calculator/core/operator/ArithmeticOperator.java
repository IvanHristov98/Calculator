package com.calculator.core.operator;

import com.calculator.core.exception.CalculatorException;

public abstract class ArithmeticOperator extends Operator {
	public abstract boolean isLeftAssociative();

	public abstract Double operate(Double leftNumber, Double rightNumber) throws CalculatorException;
}
