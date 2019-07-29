package com.calculator.core;

import com.calculator.core.exception.*;

import java.util.Arrays;
import java.util.List;

import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.stubbing.answers.ReturnsElementsOf;

import static org.mockito.Mockito.inOrder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertEquals;

public class InfixToPostfixExpressionTranslatorTest {
	@Mock
	public ExpressionTokenSplitter expressionTokenSplitter;
	@Mock
	public NumberChecker numberChecker;
	public InfixToPostfixExpressionTranslator postfixTranslator;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		this.postfixTranslator = new InfixToPostfixExpressionTranslator(this.expressionTokenSplitter, this.numberChecker);
	}

	@Test
	public void verifyOrderOfExpressionMethodCalls() throws CalculatorException {
		this.mockTokensInExpression("1");
		this.mockNumberCheckingByOrderOfTokens(true);

		Expression expression = new Expression("1");
		this.postfixTranslator.process(expression);

		InOrder mockOrder = inOrder(this.expressionTokenSplitter);

		mockOrder.verify(this.expressionTokenSplitter).getExpressionTokens(any());

		mockOrder.verifyNoMoreInteractions();
	}

	@Test(expected = BracketsException.class)
	public void missingOpeningBracket_process() throws Exception {
		this.mockTokensInExpression("1", "+", "1", ")");
		this.mockNumberCheckingByOrderOfTokens(true, false, true, false);
		
		Expression expression = new Expression("1+1)");
		this.postfixTranslator.process(expression);
	}

	@Test(expected = BracketsException.class)
	public void missingClosingBracket_process() throws Exception {
		this.mockTokensInExpression("(", "1", "+", "1");
		this.mockNumberCheckingByOrderOfTokens(false, true, false, true);

		Expression expression = new Expression("(1+1");
		this.postfixTranslator.process(expression);
	}

	@Test
	public void sumOfTwoNumbersInBrackets_process() throws Exception {
		this.mockTokensInExpression("(", "1", "+", "1", ")");
		this.mockNumberCheckingByOrderOfTokens(false, true, false, true, false);

		Expression expression = new Expression("(1+1)");
		expression = this.postfixTranslator.process(expression);
		assertEquals("1 1 +", expression.getContent());
	}

	@Test
	public void sumOfTwoNumbers_process() throws Exception {
		this.mockTokensInExpression("1", "+", "1");
		this.mockNumberCheckingByOrderOfTokens(true, false, true);

		Expression expression = new Expression("1+1");
		expression = this.postfixTranslator.process(expression);
		assertEquals("1 1 +", expression.getContent());
	}

	@Test
	public void sumOfANegativeAndAPositiveNumber_process() throws Exception {
		this.mockTokensInExpression("-1", "+", "1");
		this.mockNumberCheckingByOrderOfTokens(true, false, true);

		Expression expression = new Expression("-1+1");
		expression = this.postfixTranslator.process(expression);
		assertEquals("-1 1 +", expression.getContent());
	}

	@Test
	public void priorityOfOperators_process() throws Exception {
		this.mockTokensInExpression("1", "*", "2", "+", "3");
		this.mockNumberCheckingByOrderOfTokens(true, false, true, false, true);

		Expression expression = new Expression("1*2+3");
		expression = this.postfixTranslator.process(expression);
		assertEquals("1 2 * 3 +", expression.getContent());
	}

	@Test
	public void equalPriorityOfLeftAssociativeOperators_process() throws Exception {
		this.mockTokensInExpression("1", "/", "2", "*", "3");
		this.mockNumberCheckingByOrderOfTokens(true, false, true, false, true);

		Expression expression = new Expression("1/2*3");
		expression = this.postfixTranslator.process(expression);
		assertEquals("1 2 / 3 *", expression.getContent());
	}

	@Test
	public void leftSideAssociativity_process() throws Exception {
		this.mockTokensInExpression("(", "1", "+", "2", ")", "*", "3");
		this.mockNumberCheckingByOrderOfTokens(false, true, false, true, false, false, true);

		Expression expression = new Expression("(1+2)*3");
		expression = this.postfixTranslator.process(expression);
		assertEquals("1 2 + 3 *", expression.getContent());
	}

	@Test
	public void rightSideAssociativity_process() throws Exception {
		this.mockTokensInExpression("1", "*", "(", "2", "+", "3", ")");
		this.mockNumberCheckingByOrderOfTokens(true, false, false, true, false, true, false);

		Expression expression = new Expression("1*(2+3)");
		expression = this.postfixTranslator.process(expression);
		assertEquals("1 2 3 + *", expression.getContent());
	}

	@Test
	public void multipleBrackets_process() throws Exception {
		this.mockTokensInExpression("(", "(", "(", "1", "+", "1", ")", ")", ")");
		this.mockNumberCheckingByOrderOfTokens(false, false, false, true, false, true, false, false, false);

		Expression expression = new Expression("(((1+1)))");
		expression = this.postfixTranslator.process(expression);
		assertEquals("1 1 +", expression.getContent());
	}

	@Test
	public void productOfTwoBracketedExpression_process() throws Exception {
		this.mockTokensInExpression("(", "1", "+", "2", ")", "*", "(", "3", "+", "4", ")");
		this.mockNumberCheckingByOrderOfTokens(false, true, false, true, false, false, false, true, false, true, false);

		Expression expression = new Expression("(1+2)*(3+4)");
		expression = this.postfixTranslator.process(expression);
		assertEquals("1 2 + 3 4 + *", expression.getContent());
	}

	@Test
	public void singlePowOfABracketedExpression_process() throws Exception {
		this.mockTokensInExpression("(", "1", "+", "2", ")", "^", "3");
		this.mockNumberCheckingByOrderOfTokens(false, true, false, true, false, false, true);

		Expression expression = new Expression("(1+2)^3");
		expression = this.postfixTranslator.process(expression);
		assertEquals("1 2 + 3 ^", expression.getContent());
	}

	@Test
	public void multiplePows_process() throws Exception {
		this.mockTokensInExpression("1", "^", "2", "^", "3", "^", "4");
		this.mockNumberCheckingByOrderOfTokens(true, false, true, false, true, false, true);

		Expression expression = new Expression("1^2^3^4");
		expression = this.postfixTranslator.process(expression);
		assertEquals("1 2 3 4 ^ ^ ^", expression.getContent());
	}

	@Test
	public void expressionWithinBracketsBetweenPows_process() throws Exception {
		this.mockTokensInExpression("1", "^", "(", "2", "+", "3", ")", "^", "4");
		this.mockNumberCheckingByOrderOfTokens(true, false, false, true, false, true, false, false, true);

		Expression expression = new Expression("1^(2+3)^4");
		expression = this.postfixTranslator.process(expression);
		assertEquals("1 2 3 + 4 ^ ^", expression.getContent());
	}

	private void mockTokensInExpression(String... tokens) {
		when(this.expressionTokenSplitter.getExpressionTokens(any())).thenReturn(tokens);
	}

	private void mockNumberCheckingByOrderOfTokens(Boolean... isNumberValues) {
		List<Boolean> isNumberValuesAsList = Arrays.asList(isNumberValues);
		when(this.numberChecker.isNumber(anyString())).then(new ReturnsElementsOf(isNumberValuesAsList));
	}
}
