package com.calculator;

import com.calculator.exception.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CalculatorTests 
{	
	private static final double ALLOWED_ERROR = 0.01;
	
	@Test
	public void SumAndSubtractMultipleNumbers_Calculate() throws Exception
	{
		Calculator calc = Calculator.constructFromExpression("3.5 + 2.3 + 3 - 3");
		assertEquals(5.8, calc.calculate().doubleValue(), CalculatorTests.ALLOWED_ERROR);
	}
	
	@Test
	public void MultiplyNegativeNumbers_Calculate() throws Exception
	{
		Calculator calc  = Calculator.constructFromExpression("-1 * -1");
		assertEquals(1, calc.calculate().doubleValue(), 0);
	}

	@Test(expected = DivisionByZeroException.class)
	public void DivideByZero_Calculate() throws Exception
	{
		Calculator calc  = Calculator.constructFromExpression("1 / 0");
		assertEquals(0, calc.calculate().doubleValue(), CalculatorTests.ALLOWED_ERROR);
	}
	
	@Test
	public void AssociativityWithBrackets_Calculate() throws Exception
	{
		Calculator calc = Calculator.constructFromExpression("2 + ( 3 / 2 ) * 4");
		assertEquals(8, calc.calculate().doubleValue(), CalculatorTests.ALLOWED_ERROR);
	}
	
	@Test(expected = BracketsException.class)
	public void MissedFirstBracket_Calculate() throws Exception
	{
		Calculator calc = Calculator.constructFromExpression("3 + 4 ) / 2");
		assertEquals(5, calc.calculate().doubleValue(), CalculatorTests.ALLOWED_ERROR);
	}
}
