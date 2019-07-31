package com.calculator.cli.coreWrapper;

import com.calculator.core.Expression;
import com.calculator.core.exception.BracketsException;
import com.calculator.core.exception.DivisionByZeroException;
import com.calculator.core.exception.EmptyExpressionException;
import com.calculator.core.exception.InvalidOperatorException;
import com.calculator.core.exception.NumberMisplacementException;
import com.calculator.core.exception.OperatorMisplacementException;

public class CalculatorAdapter implements ICalculator{
	private com.calculator.core.ICalculator calculator;
	
	public CalculatorAdapter(com.calculator.core.ICalculator calculator) {
		this.calculator = calculator;
	}
	
	@Override
	public Double calculate(Expression expression) throws Exception {
		try {
			return calculator.calculate(expression);
		} catch (BracketsException exception) {
			throw new Exception("Expression error. Brackets error encountered.", exception);
		} catch (OperatorMisplacementException exception) {
			throw new Exception("Expression error. Operator misplacement error encountered.", exception);
		} catch (InvalidOperatorException exception) {
			throw new Exception(
					"Expression error. Invalid operators encountered. Valid operator symbols are +, -, *, / and ^.", exception);
		} catch (DivisionByZeroException exception) {
			throw new Exception("Expression error. Division by zero encountered.", exception);
		} catch (EmptyExpressionException exception) {
			throw new Exception("Expression error. Empty expression encountered.", exception);
		} catch (NumberMisplacementException exception) {
			throw new Exception(
					"Expression error. Number misplacement encountered. Numbers should be separated by arithmetic operators.", exception);
		}
	}
}
