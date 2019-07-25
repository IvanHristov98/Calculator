package com.calculator.core;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExpressionModifierTest 
{
	Expression expression;
	ExpressionManipulator modifier;
	
	@Before
	public void setUp()
	{
		this.modifier = new ExpressionManipulator();
	}
	
	@Test
	public void testWrapWithBrackets()
	{
		this.expression = new Expression("1+2");
		this.expression = this.modifier.getExpressionWrappedWithBrackets(expression);
		
		assertEquals("(1+2)", expression.getContent());
	}
	
	@Test
	public void testStripWhiteSpaces()
	{
		this.expression = new Expression("1 + 2");
		this.expression = this.modifier.getExpressionWithStrippedWhiteSpaces(expression);
		
		assertEquals("1+2", expression.getContent());
	}
}
