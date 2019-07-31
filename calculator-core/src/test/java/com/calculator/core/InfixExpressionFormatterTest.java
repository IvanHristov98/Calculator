package com.calculator.core;

import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.calculator.core.exception.CalculatorException;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertEquals;

public class InfixExpressionFormatterTest {
	@Mock
	public ExpressionModifier expressionModifier;
	InfixExpressionFormatter formatUnifier;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		this.formatUnifier = new InfixExpressionFormatter(this.expressionModifier);
	}

	@Test
	public void verifyOrderOfExpressionMethodCalls_process() throws CalculatorException {
		when(this.expressionModifier.getExpressionWrappedWithBrackets(any(Expression.class)))
				.thenReturn(new Expression("(1)"));
		when(this.expressionModifier.getExpressionWithStrippedWhiteSpaces(any(Expression.class)))
				.thenReturn(new Expression("(1)"));

		Expression expression = new Expression("1");
		this.formatUnifier.process(expression);

		InOrder mockOrder = inOrder(this.expressionModifier);

		mockOrder.verify(this.expressionModifier).getExpressionWrappedWithBrackets(any());
		mockOrder.verify(this.expressionModifier).getExpressionWithStrippedWhiteSpaces(any());

		mockOrder.verifyNoMoreInteractions();
	}

	@Test
	public void condensed_process() throws CalculatorException {
		this.stubDependenciesOfProcess("3+4+5", "(3+4+5)", "(3+4+5)");

		Expression expression = new Expression("3+4+5");
		expression = this.formatUnifier.process(expression);

		assertEquals("( 3 + 4 + 5 )", expression.getContent());
	}

	@Test
	public void condensedBrackets_process() throws CalculatorException {
		this.stubDependenciesOfProcess("3*(4+5)", "(3*(4+5))", "(3*(4+5))");

		Expression expression = new Expression("3*(4+5)");
		expression = this.formatUnifier.process(expression);
		assertEquals("( 3 * ( 4 + 5 ) )", expression.getContent());
	}

	@Test
	public void floatingPointNumbers_process() throws CalculatorException {
		this.stubDependenciesOfProcess("3.5+123.4567", "(3.5+123.4567)", "(3.5+123.4567)");

		Expression expression = new Expression("3.5+123.4567");
		expression = this.formatUnifier.process(expression);
		assertEquals("( 3.5 + 123.4567 )", expression.getContent());
	}

	@Test
	public void redundantOperatorAtStart_process() throws CalculatorException {
		this.stubDependenciesOfProcess("+1/2", "(+1/2)", "(+1/2)");

		Expression expression = new Expression("+1/2");
		expression = this.formatUnifier.process(expression);
		assertEquals("( 1 / 2 )", expression.getContent());
	}

	@Test
	public void minusStraightAfterBracket_process() throws CalculatorException {
		this.stubDependenciesOfProcess("(-1+1)", "((-1+1))", "((-1+1))");

		Expression expression = new Expression("(-1+1)");
		expression = this.formatUnifier.process(expression);
		assertEquals("( ( -1 + 1 ) )", expression.getContent());
	}

	@Test
	public void minusAtBeginning_process() throws CalculatorException {
		this.stubDependenciesOfProcess("-1+2", "(-1+2)", "(-1+2)");

		Expression expression = new Expression("-1+2");
		expression = this.formatUnifier.process(expression);
		assertEquals("( -1 + 2 )", expression.getContent());
	}

	private void stubDependenciesOfProcess(String initialExpression, String wrappedExpression,
			String wrappedExpressionWithStrippedWhiteSpaces) {
		when(this.expressionModifier.getExpressionWrappedWithBrackets(any()))
				.thenReturn(new Expression(wrappedExpression));
		when(this.expressionModifier.getExpressionWithStrippedWhiteSpaces(any()))
				.thenReturn(new Expression(wrappedExpressionWithStrippedWhiteSpaces));
	}
}
