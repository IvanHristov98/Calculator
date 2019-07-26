package com.calculator.cli.coreWrapper;

import com.calculator.core.*;
import com.calculator.core.exception.*;

public class CalculatorWrapper {
	private String expressionContent;
	private CalculatorSupplier calculatorSupplier;
	
	public CalculatorWrapper(String expressionContent, CalculatorSupplier calculatorSupplier) {
		this.expressionContent = expressionContent;
		this.calculatorSupplier = calculatorSupplier;
	}
	
	public double calculate() throws Exception {
		try {
			return this.getCalculationResult();
		}
		catch (BracketsException exception) {
			throw new Exception("Expression error. Brackets error encountered.");
		}
		catch (OperatorMisplacementException exception) {
			throw new Exception("Expression error. Operator misplacement error encountered.");
		}
		catch (InvalidOperatorException exception) {
			throw new Exception("Expression error. Invalid operators encountered. Valid operator symbols are +, -, *, / and ^.");
		}
		catch (DivisionByZeroException exception) {
			throw new Exception("Expression error. Division by zero encountered.");
		}
		catch (EmptyExpressionException exception) {
			throw new Exception("Expression error. Empty expression encountered.");
		}
		catch (NumberMisplacementException exception) {
			throw new Exception("Expression error. Number misplacement encountered. Numbers should be separated by arithmetic operators.");
		}
	}
	
	public double getCalculationResult() throws CalculatorException {
		Calculator calculator = this.calculatorSupplier.supplyCalculator(this.expressionContent);
		
		return calculator.calculate();
	}
}

