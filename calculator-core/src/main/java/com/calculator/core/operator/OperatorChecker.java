package com.calculator.core.operator;

public class OperatorChecker {

	public static boolean isPlus(Operator operator) {
		return operator.getSymbolicRepresentation().equals(Operators.PLUS);
	}

	public static boolean isMinus(Operator operator) {
		return operator.getSymbolicRepresentation().equals(Operators.MINUS);
	}

	public static boolean isProduct(Operator operator) {
		return operator.getSymbolicRepresentation().equals(Operators.PRODUCT);
	}

	public static boolean isDivision(Operator operator) {
		return operator.getSymbolicRepresentation().equals(Operators.DIVISION);
	}

	public static boolean isPow(Operator operator) {
		return operator.getSymbolicRepresentation().equals(Operators.POW);
	}

	public static boolean isLeftBracket(Operator operator) {
		return operator.getSymbolicRepresentation().equals(Operators.LEFT_BRACKET);
	}

	public static boolean isRightBracket(Operator operator) {
		return operator.getSymbolicRepresentation().equals(Operators.RIGHT_BRACKET);
	}

	public static boolean isBracket(Operator operator) {
		return isLeftBracket(operator) || isRightBracket(operator);
	}

	public static boolean isArithmeticOperator(Operator operator) {
		return !OperatorChecker.isBracket(operator);
	}

	private OperatorChecker() {
	}
}
