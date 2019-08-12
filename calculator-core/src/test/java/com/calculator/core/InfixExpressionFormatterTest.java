package com.calculator.core;

import org.mockito.*;

import com.calculator.core.exception.CalculatorException;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.*;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

public class InfixExpressionFormatterTest {
	@Mock
	public ExpressionModifier expressionModifier;
	InfixExpressionFormatter formatUnifier;

	@Before
	public void setUp() {
		initMocks(this);

		formatUnifier = new InfixExpressionFormatter(expressionModifier);
	}

	@Test
	public void verifyOrderOfExpressionMethodCalls_process() throws CalculatorException {
		when(expressionModifier.getExpressionWrappedWithBrackets(any(Expression.class)))
				.thenReturn(new Expression("(1)"));
		when(expressionModifier.getExpressionWithStrippedWhiteSpaces(any(Expression.class)))
				.thenReturn(new Expression("(1)"));

		Expression expression = new Expression("1");
		formatUnifier.process(expression);

		InOrder mockOrder = inOrder(expressionModifier);

		mockOrder.verify(expressionModifier).getExpressionWrappedWithBrackets(any());
		mockOrder.verify(expressionModifier).getExpressionWithStrippedWhiteSpaces(any());

		mockOrder.verifyNoMoreInteractions();
	}

	@Test
	public void condensed_process() throws CalculatorException {
		stubDependenciesOfProcess("3+4+5", "(3+4+5)", "(3+4+5)");

		Expression expression = new Expression("3+4+5");
		expression = formatUnifier.process(expression);

		assertThat(expression.getContent(), equalTo("( 3 + 4 + 5 )"));
	}

	@Test
	public void condensedBrackets_process() throws CalculatorException {
		stubDependenciesOfProcess("3*(4+5)", "(3*(4+5))", "(3*(4+5))");

		Expression expression = new Expression("3*(4+5)");
		expression = formatUnifier.process(expression);
		assertThat(expression.getContent(), equalTo("( 3 * ( 4 + 5 ) )"));
	}

	@Test
	public void floatingPointNumbers_process() throws CalculatorException {
		stubDependenciesOfProcess("3.5+123.4567", "(3.5+123.4567)", "(3.5+123.4567)");

		Expression expression = new Expression("3.5+123.4567");
		expression = formatUnifier.process(expression);
		assertThat(expression.getContent(), equalTo("( 3.5 + 123.4567 )"));
	}

	@Test
	public void redundantOperatorAtStart_process() throws CalculatorException {
		stubDependenciesOfProcess("+1/2", "(+1/2)", "(+1/2)");

		Expression expression = new Expression("+1/2");
		expression = formatUnifier.process(expression);
		assertThat(expression.getContent(), equalTo("( 1 / 2 )"));
	}

	@Test
	public void minusStraightAfterBracket_process() throws CalculatorException {
		stubDependenciesOfProcess("(-1+1)", "((-1+1))", "((-1+1))");

		Expression expression = new Expression("(-1+1)");
		expression = formatUnifier.process(expression);
		assertThat(expression.getContent(), equalTo("( ( -1 + 1 ) )"));
	}

	@Test
	public void minusAtBeginning_process() throws CalculatorException {
		stubDependenciesOfProcess("-1+2", "(-1+2)", "(-1+2)");

		Expression expression = new Expression("-1+2");
		expression = formatUnifier.process(expression);
		assertThat(expression.getContent(), equalTo("( -1 + 2 )"));
	}

	private void stubDependenciesOfProcess(String initialExpression, String wrappedExpression,
			String wrappedExpressionWithStrippedWhiteSpaces) {
		when(expressionModifier.getExpressionWrappedWithBrackets(any()))
				.thenReturn(new Expression(wrappedExpression));
		when(expressionModifier.getExpressionWithStrippedWhiteSpaces(any()))
				.thenReturn(new Expression(wrappedExpressionWithStrippedWhiteSpaces));
	}
}
