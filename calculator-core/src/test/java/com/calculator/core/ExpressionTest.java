package com.calculator.core;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

public class ExpressionTest
{
	@Test
	public void testClone()
	{
		Expression expression = new Expression("1+1");
		Expression clonedExpression = expression.clone();

		assertEquals(expression, clonedExpression);
	}
	
	@Test
	public void testGetTokens()
	{
		Expression expression = new Expression("( 1 + 2 ) * 3");
		
		assertArrayEquals(new String[] {"(", "1", "+", "2", ")", "*", "3"}, expression.getTokens());
	}
}
