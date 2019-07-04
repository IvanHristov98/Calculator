package com.testCalculator;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CalculatorTests 
{	
	private static final double DELTA = 0.01;
	
	@Test
	public void SumAndSubtractMultipleNumbers_Calculated() throws Exception
	{
		Calculator calc = Calculator.constructFromExpression("3 + 3 + 3 - 3");
		assertEquals(6, calc.calculate().doubleValue(), CalculatorTests.DELTA);
	}
	
	@Test
	public void MultiplyNegativeNumbers_Calculated() throws Exception
	{
		Calculator calc  = Calculator.constructFromExpression("-1 * -1");
		assertEquals(1, calc.calculate().doubleValue(), 0);
	}

	@Test
	public void DivideByZero_Calculated() throws Exception
	{
		Calculator calc  = Calculator.constructFromExpression("0 / 1");
		assertEquals(0, calc.calculate().doubleValue(), CalculatorTests.DELTA);
	}
}
