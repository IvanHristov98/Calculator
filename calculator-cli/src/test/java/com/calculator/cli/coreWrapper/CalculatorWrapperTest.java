package com.calculator.cli.coreWrapper;

import com.calculator.core.Calculator;
import com.calculator.core.exception.CalculatorException;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CalculatorWrapperTest {
	@Mock
	CalculatorSupplier calculatorSupplier;
	@Mock
	Calculator calculator;
	CalculatorWrapper calculatorWrapper;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void getCalculationResult_calculate() throws CalculatorException {
		String expressionContent = "1";
		when(this.calculatorSupplier.supplyCalculator(expressionContent)).thenReturn(this.calculator);
		when(this.calculator.calculate()).thenReturn(1.0d);
		
		this.calculatorWrapper = new CalculatorWrapper("1", this.calculatorSupplier);
		
		assertEquals(1.0d, this.calculatorWrapper.calculate(), 0.0001);
	}
}
