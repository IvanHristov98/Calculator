package com.calculator.operator;

import com.calculator.exception.InvalidOperatorException;

public class OperatorFactory 
{
	static class Operators
	{
		static final String PLUS = "+";
		static final String MINUS = "-";
		static final String PRODUCT = "*";
		static final String DIVISION = "/";
		static final String POW = "^";
		static final String LEFT_BRACKET = "(";
		static final String RIGHT_BRACKET = ")";
	}
	
	public static final Operator makeOperator(String operator) throws InvalidOperatorException
	{
		switch(operator)
		{
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
