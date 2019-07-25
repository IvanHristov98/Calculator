package com.calculator.core;

import com.calculator.core.exception.UnformattedExpressionException;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Before;
import org.junit.Test;

public class ExpressionTokenSplitterTest {
	private Expression expression;
	private ExpressionTokenSplitter splitter;

	@Before
	public void setUp() {
		this.splitter = new ExpressionTokenSplitter();
	}

	@Test
	public void formattedExpression_getExpressionTokens() {
		this.expression = new Expression("( 1 + 2 ) * 3");

		assertArrayEquals(new String[] { "(", "1", "+", "2", ")", "*", "3" },
				this.splitter.getExpressionTokens(this.expression));
	}

	@Test(expected = UnformattedExpressionException.class)
	public void unformattedExpression_getExpressionTokens() {
		this.expression = new Expression("1+1");

		this.splitter.getExpressionTokens(this.expression);
	}
}
