package com.calculator.core;

import com.calculator.core.exception.CalculatorException;

import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;

import org.junit.Before;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

public class CalculatorTest {
	@Mock
	public Expression expression;
	@Mock
	public UnformattedInfixExpressionValidator infixValidator;
	@Mock
	public InfixExpressionFormatter infixFormatter;
	@Mock
	public InfixToPostfixExpressionTranslator postfixTranslator;
	@Mock
	public PostfixExpressionCalculator postfixCalculator;
	@Mock
	public Calculator calculator;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		this.calculator = new Calculator(this.expression, this.infixValidator, this.infixFormatter,
				this.postfixTranslator, this.postfixCalculator);
	}

	@Test
	public void verifyOrderOfExpressionContainerProcessCalls_calculate() throws CalculatorException {
		// prevents NullPointerException
		when(this.postfixCalculator.process(any())).thenReturn(new Expression("1"));

		this.calculator.calculate();

		InOrder mockOrder = inOrder(this.infixValidator, this.infixFormatter, this.postfixTranslator,
				this.postfixCalculator);

		mockOrder.verify(this.infixValidator).process(any());
		mockOrder.verify(this.infixFormatter).process(any());
		mockOrder.verify(this.postfixTranslator).process(any());
		mockOrder.verify(this.postfixCalculator).process(any());
		
		mockOrder.verifyNoMoreInteractions();
	}

	@Test
	public void verifyCorrectCalculation_calculate() throws CalculatorException {
		when(this.postfixCalculator.process(any())).thenReturn(new Expression("1"));

		assertThat(this.calculator.calculate(), equalTo(Double.valueOf(1.0d)));
	}
}
