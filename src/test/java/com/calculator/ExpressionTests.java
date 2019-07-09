package com.calculator;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class ExpressionTests 
{
	@Test
	public void test_constructEmptyExpression()
	{
		Expression expression = Expression.constructEmptyExpression();
		
		assertTrue(expression.getContent() == null);
	}
	
	@Test
	public void test_constructFromExpression()
	{
		Expression expression = Expression.constructFromExpressionContent("1+1");
		
		assertTrue("1+1".equals(expression.getContent()));
	}
	
	@Test
	public void test_setContent()
	{
		Expression expression = Expression.constructEmptyExpression();
		expression.setContent("1+1");
		
		assertTrue("1+1".equals(expression.getContent()));
	}
	
	@Test
	public void test_clone()
	{
		Expression expression = Expression.constructFromExpressionContent("1+1");
		Expression clonedExpression = expression.clone();
		
		assertTrue(expression.getContent().equals(clonedExpression.getContent()));
	}
}
