package com.calculator.core;

import com.calculator.core.exception.*;

import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.stubbing.answers.ReturnsElementsOf;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.inOrder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import org.junit.Before;
import org.junit.Test;

public class PostfixExpressionCalculatorTest {
	@Mock
	ExpressionTokenSplitter expressionTokenSplitter;
	@Mock
	NumberChecker numberChecker;
	Expression resultExpression;
	Double calculationResult;
	PostfixExpressionCalculator calculator;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		this.calculator = new PostfixExpressionCalculator(this.expressionTokenSplitter,
				this.numberChecker);
	}

	@Test
	public void verifyOrderOfExpressionMethodCalls() throws CalculatorException {
		this.mockTokensInExpression("1");
		this.mockNumberCheckingByOrderOfTokens(true);

		Expression expression = new Expression("1");
		this.calculator.process(expression);

		InOrder mockOrder = inOrder(this.expressionTokenSplitter);

		mockOrder.verify(this.expressionTokenSplitter).getExpressionTokens(any());

		mockOrder.verifyNoMoreInteractions();
	}

	@Test
	public void twoOperators_process() throws CalculatorException {
		this.mockTokensInExpression("1", "2", "+");
		this.mockNumberCheckingByOrderOfTokens(true, true, false);

		Expression expression = new Expression("1 2 +");
		assertEquals(3.0, this.getExpressionCalculationResult(expression), 0.0001);
	}

	@Test
	public void allExpression_process() throws CalculatorException {
		this.mockTokensInExpression("1", "2", "3", "4", "5", "+", "*", "/", "^");
		this.mockNumberCheckingByOrderOfTokens(true, true, true, true, true, false, false, false, false);

		Expression expression = new Expression("1 2 3 4 5 + * / ^");
		assertEquals(1 ^ (2 / (3 * (4 + 5))), this.getExpressionCalculationResult(expression), 0.0001);
	}

	@Test(expected = InvalidOperatorException.class)
	public void invalidOperator_process() throws CalculatorException {
		this.mockTokensInExpression("1", "A", "5");
		this.mockNumberCheckingByOrderOfTokens(true, false, true);

		Expression expression = new Expression("1 A 5");
		this.calculator.process(expression);
	}

	@Test(expected = NumberMisplacementException.class)
	public void tooManyNumbers_process() throws CalculatorException {
		this.mockTokensInExpression("1", "2", "3", "+");
		this.mockNumberCheckingByOrderOfTokens(true, true, true, false);

		Expression expression = new Expression("1 2 3 +");
		this.calculator.process(expression);
	}

	@Test(expected = NumberMisplacementException.class)
	public void tooFewNumbers_process() throws CalculatorException {
		this.mockTokensInExpression("1", "+");
		this.mockNumberCheckingByOrderOfTokens(true, false);

		Expression expression = new Expression("1 +");
		this.calculator.process(expression);
	}

	private void mockTokensInExpression(String... tokens) {
		when(this.expressionTokenSplitter.getExpressionTokens(any())).thenReturn(tokens);
	}

	private void mockNumberCheckingByOrderOfTokens(Boolean... isNumberValues) {
		List<Boolean> isNumberValuesAsList = Arrays.asList(isNumberValues);
		when(this.numberChecker.isNumber(anyString())).then(new ReturnsElementsOf(isNumberValuesAsList));
	}

	private double getExpressionCalculationResult(Expression expression) throws CalculatorException {
		this.resultExpression = this.calculator.process(expression);
		this.calculationResult = Double.valueOf(this.resultExpression.getContent());

		return this.calculationResult;
	}
}
