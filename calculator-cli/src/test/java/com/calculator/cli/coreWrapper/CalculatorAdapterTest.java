package com.calculator.cli.coreWrapper;

import static org.junit.Assert.assertEquals;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import org.junit.Before;
import org.junit.Test;

import com.calculator.core.Expression;

public class CalculatorAdapterTest {
	@Mock
	private com.calculator.core.ICalculator calculator;
	@Mock
	private Expression expression;
	private CalculatorAdapter calculatorAdapter;
	
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
}
