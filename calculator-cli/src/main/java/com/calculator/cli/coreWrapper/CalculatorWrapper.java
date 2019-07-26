package com.calculator.cli.coreWrapper;

import com.calculator.core.*;
import com.calculator.core.exception.*;

public class CalculatorWrapper {
	private CalculatorSupplier calculatorSupplier;
	
	public CalculatorWrapper(CalculatorSupplier calculatorSupplier) {
		this.calculatorSupplier = calculatorSupplier;
	}
	
	public double calculate(String expressionContent) throws Exception {
		try {
			return this.getCalculationResult(expressionContent);
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
	
	public double getCalculationResult(String expressionContent) throws CalculatorException {
		Calculator calculator = this.calculatorSupplier.supplyCalculator(expressionContent);
		
		return calculator.calculate();
	}
}

