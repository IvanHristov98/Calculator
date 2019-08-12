package com.calculator.core;

import org.junit.*;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

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

		assertThat(expression.getContent(), equalTo("(1+2)"));
	}

	@Test
	public void testStripWhiteSpaces() {
		expression = new Expression("1 + 2");
		expression = expressionModifier.getExpressionWithStrippedWhiteSpaces(expression);

		assertThat(expression.getContent(), equalTo("1+2"));
	}
}
