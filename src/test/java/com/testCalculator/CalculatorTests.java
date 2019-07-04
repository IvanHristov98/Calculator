package com.testCalculator;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CalculatorTests 
{	
	private static final double DELTA = 0.01;
	
	@Test
	public void SumAndSubtractNumbers_Calculated() throws Exception
	{
		Calculator calc = Calculator.constructFromExpression("3 + 3 + 3");
		assertEquals(9, calc.calculate().doubleValue(), CalculatorTests.DELTA);
		
		calc.changeExpression("-3 + 3");
		assertEquals(0, calc.calculate().doubleValue(), CalculatorTests.DELTA);
		
		calc.changeExpression("0 + 0");
		assertEquals(0, calc.calculate().doubleValue(), CalculatorTests.DELTA);
		
		calc  = Calculator.constructFromExpression("1 - 1");
		assertEquals(0, calc.calculate().doubleValue(), CalculatorTests.DELTA);
		
		calc  = Calculator.constructFromExpression("1 - 15");
		assertEquals(-14, calc.calculate().doubleValue(), CalculatorTests.DELTA);
	}
	
	@Test
	public void MultiplyNumbers_Calculated() throws Exception
	{
		Calculator calc  = Calculator.constructFromExpression("0 * 1");
		assertEquals(0, calc.calculate().doubleValue(), 0);
		
		calc  = Calculator.constructFromExpression("1 * 1");
		assertEquals(1, calc.calculate().doubleValue(), 0);
		
		calc  = Calculator.constructFromExpression("-1 * 1");
		assertEquals(-1, calc.calculate().doubleValue(), 0);
		
		calc  = Calculator.constructFromExpression("-1 * -1");
		assertEquals(1, calc.calculate().doubleValue(), 0);
	}
	
	/**
	 * TODO Add divide by zero
	 * @throws Exception
	 */
	@Test
	public void DivideNumbers_Calculated() throws Exception
	{
		Calculator calc  = Calculator.constructFromExpression("0 / 1");
		assertEquals(0, calc.calculate().doubleValue(), CalculatorTests.DELTA);
		
		calc  = Calculator.constructFromExpression("2 / 3");
		assertEquals(0.66, calc.calculate().doubleValue(), CalculatorTests.DELTA);
	}
}
