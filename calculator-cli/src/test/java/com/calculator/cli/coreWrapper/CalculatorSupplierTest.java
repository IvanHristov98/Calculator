package com.calculator.cli.coreWrapper;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

import com.calculator.core.Calculator;

public class CalculatorSupplierTest {
	
	@Test
	public void supplyCalculator() {
		CalculatorSupplier calculatorSupplier = new CalculatorSupplier();
		Calculator calculator = calculatorSupplier.supplyCalculator("1");
		
		assertNotNull(calculator);
	}
}
