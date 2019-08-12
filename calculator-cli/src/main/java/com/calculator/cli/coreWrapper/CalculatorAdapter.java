package com.calculator.cli.coreWrapper;

import com.calculator.cli.coreWrapper.exception.CliCalculatorException;
import com.calculator.core.Expression;
import com.calculator.core.exception.*;

public class CalculatorAdapter implements ICalculator{
	private com.calculator.core.ICalculator calculator;
	
	public CalculatorAdapter(com.calculator.core.ICalculator calculator) {
		this.calculator = calculator;
	}
	
	@Override
	public Double calculate(Expression expression) throws CliCalculatorException {
		try {
			return calculator.calculate(expression);
		} catch (BracketsException exception) {
			throw new CliCalculatorException("Expression error. Brackets error encountered.", exception);
		} catch (OperatorMisplacementException exception) {
			throw new CliCalculatorException("Expression error. Operator misplacement error encountered.", exception);
		} catch (InvalidOperatorException exception) {
			throw new CliCalculatorException(
					"Expression error. Invalid operators encountered. Valid operator symbols are +, -, *, / and ^.", exception);
		} catch (DivisionByZeroException exception) {
			throw new CliCalculatorException("Expression error. Division by zero encountered.", exception);
		} catch (EmptyExpressionException exception) {
			throw new CliCalculatorException("Expression error. Empty expression encountered.", exception);
		} catch (NumberMisplacementException exception) {
			throw new CliCalculatorException(
					"Expression error. Number misplacement encountered. Numbers should be separated by arithmetic operators.", exception);
		} catch (CalculatorException exception) {
			throw new CliCalculatorException("Expression error. Invalid expression encountered.");
		}
	}
}
