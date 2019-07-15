package com.calculator;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ExpressionTests
{
	@Test
	public void testClone()
	{
		Expression expression = new Expression("1+1");
		Expression clonedExpression = expression.clone();

		assertEquals(expression, clonedExpression);
	}
}
