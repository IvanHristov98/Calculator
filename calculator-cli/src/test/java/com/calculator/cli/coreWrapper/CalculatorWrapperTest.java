package com.calculator.cli.coreWrapper;

import com.calculator.core.*;
import com.calculator.core.exception.*;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;

import org.junit.Before;
import org.junit.Rule;

import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class CalculatorWrapperTest {
	@Mock
	CalculatorFactory calculatorFactory;
	@Mock
	Calculator calculator;
	ExceptionWrappingCalculator calculatorWrapper;
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void returnOfCorrectCalculationResult_calculate() throws Exception {
		String expressionContent = "1";
		
		when(this.calculatorFactory.makeCalculator()).thenReturn(this.calculator);
		when(this.calculator.calculate(this.getExpression(expressionContent))).thenReturn(1.0d);
		
		this.calculatorWrapper = new ExceptionWrappingCalculator(this.calculatorFactory);
		
		assertEquals(1.0d, this.calculatorWrapper.calculate(expressionContent), 0.0001);
	}
	
	@Test
	public void verifyOperatorMisplacementException_calculate() throws Exception {
		this.verifyCalculatorException(
				"Expression error. Operator misplacement error encountered.",
				OperatorMisplacementException.class
				);
	}
	
	@Test
	public void verifyBracketsException_calculate() throws Exception {
		this.verifyCalculatorException(
				"Expression error. Brackets error encountered.",
				BracketsException.class
				);
	}
	
	@Test
	public void verifyDivisionByZeroException_calculate() throws Exception {
		this.verifyCalculatorException(
				"Expression error. Division by zero encountered.",
				DivisionByZeroException.class
				);
	}
	
	@Test
	public void verifyEmptyExpressionException_calculate() throws Exception {
		this.verifyCalculatorException(
				"Expression error. Empty expression encountered.",
				EmptyExpressionException.class
				);
	}
	
	@Test
	public void verifyInvalidOperatorException_calculate() throws Exception {
		this.verifyCalculatorException(
				"Expression error. Invalid operators encountered. Valid operator symbols are +, -, *, / and ^.",
				InvalidOperatorException.class
				);
	}
	
	@Test
	public void verifyNumberMisplacementException() throws Exception {
		this.verifyCalculatorException(
				"Expression error. Number misplacement encountered. Numbers should be separated by arithmetic operators.",
				NumberMisplacementException.class
				);
	}
	
	private void verifyCalculatorException(String message, Class<? extends Throwable> exceptionToMockWith)
			throws Exception {
		this.expectedException.expect(Exception.class);
		this.expectedException.expectMessage(message);
		
		String expressionContent = "1";
		
		when(this.calculatorFactory.makeCalculator()).thenReturn(this.calculator);
		when(this.calculator.calculate(any())).thenThrow(exceptionToMockWith);
		
		this.calculatorWrapper = new ExceptionWrappingCalculator(this.calculatorFactory);
		this.calculatorWrapper.calculate(expressionContent);
	}
	
	private Expression getExpression(String content) {
		return new Expression(content);
	}
}