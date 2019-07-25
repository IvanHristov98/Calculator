package com.calculator.core.operator;

public final class ProductOperator extends ArithmeticOperator {
	protected static final int PRIORITY = 1;

	{
		this.symbolicRepresentation = Operators.PRODUCT;
	}

	@Override
	public int getPriority() {
		return ProductOperator.PRIORITY;
	}

	@Override
	public boolean isLeftAssociative() {
		return true;
	}

	@Override
	public Double operate(Double leftNumber, Double rightNumber) {
		return leftNumber * rightNumber;
	}
}
