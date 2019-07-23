package com.calculator.core;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.calculator.core.exception.CalculatorException;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import org.junit.Before;

public class CalculatorTest
{	
	@Mock
	public Expression expression;
	@Mock
	public UnformattedInfixExpressionValidator infixValidator;
	@Mock
	public InfixExpressionFormatUnifier infixFormatter;
	@Mock
	public InfixToPostfixExpressionTranslator postfixTranslator;
	@Mock
	public PostfixExpressionCalculator postfixCalculator;
	
	@Before
	public void setUp()
	{
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void calculationSequence_calculate() throws CalculatorException
	{
		when(postfixCalculator.process()).thenReturn(new Expression("1"));
		
		Calculator calculator = new Calculator(
				this.expression,
				this.infixValidator,
				this.infixFormatter,
				this.postfixTranslator,
				this.postfixCalculator
				);
		
		calculator.calculate();
		
		verify(this.infixValidator, times(1)).process();
		verify(this.infixFormatter, times(1)).process();
		verify(this.postfixTranslator, times(1)).process();
		verify(this.postfixCalculator, times(1)).process();
	}
}
