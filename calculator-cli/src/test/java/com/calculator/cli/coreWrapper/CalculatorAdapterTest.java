package com.calculator.cli.coreWrapper;

import static org.junit.Assert.assertEquals;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.calculator.core.CalculatorFactory;
import com.calculator.core.Expression;
import com.calculator.core.exception.*;

public class CalculatorAdapterTest {
	@Mock
	CalculatorFactory calculatorFactory;
	@Mock
	private com.calculator.core.ICalculator calculator;
	@Mock
	private Expression expression;
	private CalculatorAdapter calculatorAdapter;
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		calculatorAdapter = new CalculatorAdapter(calculator);
	}
	
	@Test
	public void verifyCalculation() throws Exception {
		when(calculator.calculate(any())).thenReturn(1.0);
		
		assertEquals(1.0, calculatorAdapter.calculate(expression), 0.001);
	}
	
	@Test
	public void verifyOperatorMisplacementException_calculate() throws Exception {
		verifyCalculatorException("Expression error. Operator misplacement error encountered.",
				OperatorMisplacementException.class);
	}

	@Test
	public void verifyBracketsException_calculate() throws Exception {
		verifyCalculatorException("Expression error. Brackets error encountered.", BracketsException.class);
	}

	@Test
	public void verifyDivisionByZeroException_calculate() throws Exception {
		verifyCalculatorException("Expression error. Division by zero encountered.",
				DivisionByZeroException.class);
	}

	@Test
	public void verifyEmptyExpressionException_calculate() throws Exception {
		verifyCalculatorException("Expression error. Empty expression encountered.",
				EmptyExpressionException.class);
	}

	@Test
	public void verifyInvalidOperatorException_calculate() throws Exception {
		verifyCalculatorException(
				"Expression error. Invalid operators encountered. Valid operator symbols are +, -, *, / and ^.",
				InvalidOperatorException.class);
	}

	@Test
	public void verifyNumberMisplacementException() throws Exception {
		verifyCalculatorException(
				"Expression error. Number misplacement encountered. Numbers should be separated by arithmetic operators.",
				NumberMisplacementException.class);
	}

	private void verifyCalculatorException(String message, Class<? extends Throwable> exceptionToMockWith)
			throws Exception {
		expectedException.expect(Exception.class);
		expectedException.expectMessage(message);

		Expression expression = getExpression("1");

		when(calculatorFactory.makeCalculator()).thenReturn(calculator);
		when(calculator.calculate(any())).thenThrow(exceptionToMockWith);

		calculatorAdapter = new CalculatorAdapter(calculatorFactory.makeCalculator());
		calculatorAdapter.calculate(expression);
	}

	private Expression getExpression(String content) {
		return new Expression(content);
	}
}
