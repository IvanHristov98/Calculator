package com.calculator.core;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ExpressionManipulatorTest 
{
	Expression expression;
	ExpressionManipulator manipulator;
	
	@Before
	public void setUp()
	{
		this.manipulator = new ExpressionManipulator();
	}
	
	@Test
	public void testWrapWithBrackets()
	{
		this.expression = new Expression("1+2");
		this.expression = this.manipulator.getExpressionWrappedWithBrackets(expression);
		
		assertEquals("(1+2)", expression.getContent());
	}
	
	@Test
	public void testStripWhiteSpaces()
	{
		this.expression = new Expression("1 + 2");
		this.expression = this.manipulator.getExpressionWithStrippedWhiteSpaces(expression);
		
		assertEquals("1+2", expression.getContent());
	}
}
