package com.calculator.core;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExpressionModifierTest {
	Expression expression;
	ExpressionModifier expressionModifier;

	@Before
	public void setUp() {
		expressionModifier = new ExpressionModifier();
	}

	@Test
	public void testWrapWithBrackets() {
		expression = new Expression("1+2");
		expression = expressionModifier.getExpressionWrappedWithBrackets(expression);

		assertEquals("(1+2)", expression.getContent());
	}

	@Test
	public void testStripWhiteSpaces() {
		expression = new Expression("1 + 2");
		expression = expressionModifier.getExpressionWithStrippedWhiteSpaces(expression);

		assertEquals("1+2", expression.getContent());
	}
}
