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
		if (operator.equals(Operators.PLUS))
		{
			return new PlusOperator();
		}
		else if (operator.equals(Operators.MINUS))
		{
			return new MinusOperator();
		}
		else if (operator.equals(Operators.PRODUCT))
		{
			return new ProductOperator();
		}
		else if (operator.equals(Operators.DIVISION))
		{
			return new DivisionOperator();
		}
		else if (operator.equals(Operators.POW))
		{
			return new PowOperator();
		}
		else if (operator.equals(Operators.LEFT_BRACKET))
		{
			return new LeftBracketOperator();
		}
		else if (operator.equals(Operators.RIGHT_BRACKET))
		{
			return new RightBracketOperator();
		}
		
		throw new InvalidOperatorException("An invalid operator has been inserted.");
	}
}
