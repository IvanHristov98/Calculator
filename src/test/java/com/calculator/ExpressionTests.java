package com.calculator;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ExpressionTests
{
	@Test
	public void test_clone()
	{
		Expression expression = new Expression("1+1");
		Expression clonedExpression = expression.clone();

		assertEquals(expression, clonedExpression);
	}
}
