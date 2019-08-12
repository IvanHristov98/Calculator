package com.calculator.core;

import com.calculator.core.exception.*;

import java.util.*;

import org.mockito.*;
import org.mockito.internal.stubbing.answers.ReturnsElementsOf;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.*;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

public class InfixToPostfixExpressionTranslatorTest {
	@Mock
	public ExpressionTokenSplitter expressionTokenSplitter;
	@Mock
	public NumberChecker numberChecker;
	public InfixToPostfixExpressionTranslator postfixTranslator;

	@Before
	public void setUp() {
		initMocks(this);

		postfixTranslator = new InfixToPostfixExpressionTranslator(expressionTokenSplitter,
				numberChecker);
	}

	@Test
	public void verifyOrderOfExpressionMethodCalls() throws CalculatorException {
		mockTokensInExpression("1");
		mockNumberCheckingByOrderOfTokens(true);

		Expression expression = this.getFormattedExpression("1");
		postfixTranslator.process(expression);

		InOrder mockOrder = inOrder(expressionTokenSplitter);

		mockOrder.verify(expressionTokenSplitter).getExpressionTokens(any());

		mockOrder.verifyNoMoreInteractions();
	}

	@Test(expected = BracketsException.class)
	public void missingOpeningBracket_process() throws Exception {
		mockTokensInExpression("1", "+", "1", ")");
		mockNumberCheckingByOrderOfTokens(true, false, true, false);

		Expression expression = this.getFormattedExpression("1+1)");
		postfixTranslator.process(expression);
	}

	@Test(expected = BracketsException.class)
	public void missingClosingBracket_process() throws Exception {
		mockTokensInExpression("(", "1", "+", "1");
		mockNumberCheckingByOrderOfTokens(false, true, false, true);

		Expression expression = this.getFormattedExpression("(1+1");
		postfixTranslator.process(expression);
	}

	@Test
	public void sumOfTwoNumbersInBrackets_process() throws Exception {
		mockTokensInExpression("(", "1", "+", "1", ")");
		mockNumberCheckingByOrderOfTokens(false, true, false, true, false);

		Expression expression = this.getFormattedExpression("(1+1)");
		expression = postfixTranslator.process(expression);
		assertThat(expression.getContent(), equalTo("1 1 +"));
	}

	@Test
	public void sumOfTwoNumbers_process() throws Exception {
		mockTokensInExpression("1", "+", "1");
		mockNumberCheckingByOrderOfTokens(true, false, true);

		Expression expression = this.getFormattedExpression("1+1");
		expression = postfixTranslator.process(expression);
		assertThat(expression.getContent(), equalTo("1 1 +"));
	}

	@Test
	public void sumOfANegativeAndAPositiveNumber_process() throws Exception {
		mockTokensInExpression("-1", "+", "1");
		mockNumberCheckingByOrderOfTokens(true, false, true);

		Expression expression = this.getFormattedExpression("-1+1");
		expression = postfixTranslator.process(expression);
		assertThat(expression.getContent(), equalTo("-1 1 +"));
	}

	@Test
	public void priorityOfOperators_process() throws Exception {
		mockTokensInExpression("1", "*", "2", "+", "3");
		mockNumberCheckingByOrderOfTokens(true, false, true, false, true);

		Expression expression = this.getFormattedExpression("1*2+3");
		expression = postfixTranslator.process(expression);
		assertThat(expression.getContent(), equalTo("1 2 * 3 +"));
	}

	@Test
	public void equalPriorityOfLeftAssociativeOperators_process() throws Exception {
		mockTokensInExpression("1", "/", "2", "*", "3");
		mockNumberCheckingByOrderOfTokens(true, false, true, false, true);

		Expression expression = this.getFormattedExpression("1/2*3");
		expression = postfixTranslator.process(expression);
		assertThat(expression.getContent(), equalTo("1 2 / 3 *"));
	}

	@Test
	public void leftSideAssociativity_process() throws Exception {
		mockTokensInExpression("(", "1", "+", "2", ")", "*", "3");
		mockNumberCheckingByOrderOfTokens(false, true, false, true, false, false, true);

		Expression expression = this.getFormattedExpression("(1+2)*3");
		expression = postfixTranslator.process(expression);
		assertThat(expression.getContent(), equalTo("1 2 + 3 *"));
	}

	@Test
	public void rightSideAssociativity_process() throws Exception {
		mockTokensInExpression("1", "*", "(", "2", "+", "3", ")");
		mockNumberCheckingByOrderOfTokens(true, false, false, true, false, true, false);

		Expression expression = this.getFormattedExpression("1*(2+3)");
		expression = postfixTranslator.process(expression);
		assertThat(expression.getContent(), equalTo("1 2 3 + *"));
	}

	@Test
	public void multipleBrackets_process() throws Exception {
		mockTokensInExpression("(", "(", "(", "1", "+", "1", ")", ")", ")");
		mockNumberCheckingByOrderOfTokens(false, false, false, true, false, true, false, false, false);

		Expression expression = this.getFormattedExpression("(((1+1)))");
		expression = postfixTranslator.process(expression);
		assertThat(expression.getContent(), equalTo("1 1 +"));
	}

	@Test
	public void productOfTwoBracketedExpression_process() throws Exception {
		mockTokensInExpression("(", "1", "+", "2", ")", "*", "(", "3", "+", "4", ")");
		mockNumberCheckingByOrderOfTokens(false, true, false, true, false, false, false, true, false, true, false);

		Expression expression = this.getFormattedExpression("(1+2)*(3+4)");
		expression = postfixTranslator.process(expression);
		assertThat(expression.getContent(), equalTo("1 2 + 3 4 + *"));
	}

	@Test
	public void singlePowOfABracketedExpression_process() throws Exception {
		mockTokensInExpression("(", "1", "+", "2", ")", "^", "3");
		mockNumberCheckingByOrderOfTokens(false, true, false, true, false, false, true);

		Expression expression = this.getFormattedExpression("(1+2)^3");
		expression = postfixTranslator.process(expression);
		assertThat(expression.getContent(), equalTo("1 2 + 3 ^"));
	}

	@Test
	public void multiplePows_process() throws Exception {
		mockTokensInExpression("1", "^", "2", "^", "3", "^", "4");
		mockNumberCheckingByOrderOfTokens(true, false, true, false, true, false, true);

		Expression expression = this.getFormattedExpression("1^2^3^4");
		expression = postfixTranslator.process(expression);
		assertThat(expression.getContent(), equalTo("1 2 3 4 ^ ^ ^"));
	}

	@Test
	public void expressionWithinBracketsBetweenPows_process() throws Exception {
		mockTokensInExpression("1", "^", "(", "2", "+", "3", ")", "^", "4");
		mockNumberCheckingByOrderOfTokens(true, false, false, true, false, true, false, false, true);

		Expression expression = this.getFormattedExpression("1^(2+3)^4");
		expression = postfixTranslator.process(expression);
		assertThat(expression.getContent(), equalTo("1 2 3 + 4 ^ ^"));
	}

	private void mockTokensInExpression(String... tokens) {
		when(expressionTokenSplitter.getExpressionTokens(any())).thenReturn(tokens);
	}

	private void mockNumberCheckingByOrderOfTokens(Boolean... isNumberValues) {
		List<Boolean> isNumberValuesAsList = Arrays.asList(isNumberValues);
		when(numberChecker.isNumber(anyString())).then(new ReturnsElementsOf(isNumberValuesAsList));
	}
	
	private FormattedExpression getFormattedExpression(String content) {
		return new FormattedExpression(content);
	}
}
