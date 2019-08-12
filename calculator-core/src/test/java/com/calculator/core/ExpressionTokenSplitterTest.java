package com.calculator.core;

import com.calculator.core.exception.UnformattedExpressionException;

import static org.junit.Assert.assertArrayEquals;

import org.junit.*;

public class ExpressionTokenSplitterTest {
	private FormattedExpression expression;
	private ExpressionTokenSplitter splitter;

	@Before
	public void setUp() {
		splitter = new ExpressionTokenSplitter();
	}

	@Test
	public void formattedExpression_getExpressionTokens() {
		expression = new FormattedExpression("( 1 + 2 ) * 3");

		assertArrayEquals(new String[] { "(", "1", "+", "2", ")", "*", "3" },
				splitter.getExpressionTokens(expression));
	}

	@Test(expected = UnformattedExpressionException.class)
	public void unformattedExpression_getExpressionTokens() {
		expression = new FormattedExpression("1+1");

		splitter.getExpressionTokens(expression);
	}
}
