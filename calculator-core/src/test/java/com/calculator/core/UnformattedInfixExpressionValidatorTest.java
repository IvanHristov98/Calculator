package com.calculator.core;

import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.inOrder;
import static org.mockito.ArgumentMatchers.any;

import org.junit.Before;
import org.junit.Test;

import com.calculator.core.exception.*;

public class UnformattedInfixExpressionValidatorTest {
	public Expression expression;
	@Mock
	public ExpressionModifier expressionModifier;
	public UnformattedInfixExpressionValidator validator;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		// initializing with a correct stub value
		this.validator = new UnformattedInfixExpressionValidator(this.expressionModifier);
	}

	@Test
	public void verifyOrderOfExpressionMethodCalls_process() throws Exception {
		when(this.expressionModifier.getExpressionWrappedWithBrackets(any(Expression.class)))
				.thenReturn(new Expression("(1)"));
		when(this.expressionModifier.getExpressionWithStrippedWhiteSpaces(any(Expression.class)))
				.thenReturn(new Expression("(1)"));

		Expression expression = new Expression("1");
		this.validator.process(expression);

		InOrder mockOrder = inOrder(this.expressionModifier);

		mockOrder.verify(this.expressionModifier).getExpressionWrappedWithBrackets(any());
		mockOrder.verify(this.expressionModifier).getExpressionWithStrippedWhiteSpaces(any());

		mockOrder.verifyNoMoreInteractions();
	}

	@Test(expected = NumberMisplacementException.class)
	public void spacedConsecutiveNumbers_process() throws Exception {
		this.stubDependenciesOfProcess("(1 2)", "(12)");
		
		this.validator.process(this.getExpression("1 2"));
	}

	@Test(expected = NumberMisplacementException.class)
	public void consecutive_process() throws Exception {
		this.stubDependenciesOfProcess("(2+4.55.6)", "(2+4.55.6)");

		this.validator.process(this.getExpression("2+4.55.6"));
	}

	@Test(expected = EmptyExpressionException.class)
	public void emptyExpression_process() throws Exception {
		this.stubDependenciesOfProcess("()", "()");
		
		this.validator.process(this.getExpression(""));
	}

	@Test(expected = OperatorMisplacementException.class)
	public void manyLegitimateOperatorsAtBeginning_process() throws Exception {
		this.stubDependenciesOfProcess("(++1+2)", "(++1+2)");

		this.validator.process(this.getExpression("++1+2"));
	}

	@Test(expected = OperatorMisplacementException.class)
	public void consecutiveOperators_process() throws Exception {
		this.stubDependenciesOfProcess("(2+*3)", "(2+*3)");

		this.validator.process(this.getExpression("2+*3"));
	}

	@Test(expected = InvalidOperatorException.class)
	public void invalidTokenException_process() throws Exception {
		this.stubDependenciesOfProcess("(1A2)", "(1A2)");

		this.validator.process(this.getExpression("1A2"));
	}

	@Test(expected = BracketsException.class)
	public void numberGluedToTheLeftOfABracketedExpression_process() throws Exception {
		this.stubDependenciesOfProcess("(2(3+4))", "(2(3+4))");

		this.validator.process(this.getExpression("2(3+4)"));
	}

	@Test(expected = BracketsException.class)
	public void numberGluedToTheRightOfABracketedExpression_process() throws Exception {
		this.stubDependenciesOfProcess("((3+4)5)", "((3+4)5)");

		this.validator.process(this.getExpression("(3+4)5"));
	}

	@Test(expected = OperatorMisplacementException.class)
	public void misplacedOperatorAtStart_process() throws Exception {
		this.stubDependenciesOfProcess("(*1+2)", "(*1+2)");

		this.validator.process(this.getExpression("*1+2"));
	}

	@Test(expected = OperatorMisplacementException.class)
	public void manyMisplacedOperatorsAtBeginning_process() throws Exception {
		this.stubDependenciesOfProcess("(**/1/2)", "(**/1/2)");

		this.validator.process(this.getExpression("**/1/2"));
	}

	@Test(expected = OperatorMisplacementException.class)
	public void misplacedOperatorAtEnd_process() throws Exception {
		this.stubDependenciesOfProcess("(1/2*)", "(1/2*)");

		this.validator.process(this.getExpression("1/2*"));
	}

	@Test(expected = OperatorMisplacementException.class)
	public void manyMisplacedOperatorsAtEnd_process() throws Exception {
		this.stubDependenciesOfProcess("(1/2*/*/*)", "(1/2*/*/*)");

		this.validator.process(this.getExpression("1/2*/*/*"));
	}

	@Test(expected = OperatorMisplacementException.class)
	public void singleOperator_process() throws Exception {
		this.stubDependenciesOfProcess("(+)", "(+)");

		this.validator.process(this.getExpression("+"));
	}

	@Test(expected = OperatorMisplacementException.class)
	public void misplacedOperatorsStraightAfterOpeningBracket_process() throws Exception {
		this.stubDependenciesOfProcess("((*3))", "((*3))");

		this.validator.process(this.getExpression("(*3)"));
	}

	@Test(expected = OperatorMisplacementException.class)
	public void misplacedOperatorsAtEndsWithinBrackets_process() throws Exception {
		this.stubDependenciesOfProcess("((4+5*))", "((4+5*))");

		this.validator.process(this.getExpression("(4+5*)"));
	}

	@Test(expected = OperatorMisplacementException.class)
	public void singleOperatorWithinBrackets_process() throws Exception {
		this.stubDependenciesOfProcess("((+))", "((+))");

		this.validator.process(this.getExpression("(+)"));
	}

	@Test(expected = EmptyExpressionException.class)
	public void emptyBrackets_process() throws Exception {
		this.stubDependenciesOfProcess("(1+())", "(1+())");

		this.validator.process(this.getExpression("1+()"));
	}

	private Expression getExpression(String expressionContent) {
		return new Expression(expressionContent);
	}
	
	private void stubDependenciesOfProcess(String wrappedExpression, String wrappedExpressionWithStrippedWhiteSpaces) {
		when(this.expressionModifier.getExpressionWrappedWithBrackets(any()))
				.thenReturn(new Expression(wrappedExpression));
		when(this.expressionModifier.getExpressionWithStrippedWhiteSpaces(any()))
				.thenReturn(new Expression(wrappedExpressionWithStrippedWhiteSpaces));
	}
}
