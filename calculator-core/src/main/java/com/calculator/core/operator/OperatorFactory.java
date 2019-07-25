package com.calculator.core.operator;

import com.calculator.core.exception.InvalidOperatorException;

public class OperatorFactory {
	public static final Operator makeOperator(String operator) throws InvalidOperatorException {
		switch (operator) {
		case Operators.PLUS:
			return new PlusOperator();
		case Operators.MINUS:
			return new MinusOperator();
		case Operators.PRODUCT:
			return new ProductOperator();
		case Operators.DIVISION:
			return new DivisionOperator();
		case Operators.POW:
			return new PowOperator();
		case Operators.LEFT_BRACKET:
			return new LeftBracketOperator();
		case Operators.RIGHT_BRACKET:
			return new RightBracketOperator();
		default:
			throw new InvalidOperatorException("An invalid operator has been inserted.");
		}
	}
}
