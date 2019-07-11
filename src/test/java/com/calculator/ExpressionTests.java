package com.calculator;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ExpressionTests
{
	@Test
	public void test_constructFromExpression()
	{
		Expression expression = Expression.constructFromExpressionContent("1+1");

		assertEquals("1+1", expression.getContent());
	}

	@Test
	public void test_setContent()
	{
		Expression expression = Expression.constructFromExpressionContent("2+2");
		expression.setContent("1+1");

		assertEquals("1+1", expression.getContent());
	}

	@Test
	public void test_clone()
	{
		Expression expression = Expression.constructFromExpressionContent("1+1");
		Expression clonedExpression = expression.clone();

		assertEquals(expression, clonedExpression);
	}
}
