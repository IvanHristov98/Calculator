package com.calculator.cli.coreWrapper;

import com.calculator.core.*;
import com.calculator.core.exception.*;

public class ExceptionWrappingCalculator extends CalculatorDecorator{
	public ExceptionWrappingCalculator(iCalculator calculator) {
		super(calculator);
	}
	
	public Double calculate(Expression expression) throws CalculatorException {
		try {
			return calculator.calculate(expression);
		}
		catch (BracketsException exception) {
			throw new BracketsException("Expression error. Brackets error encountered.");
		}
		catch (OperatorMisplacementException exception) {
			throw new OperatorMisplacementException("Expression error. Operator misplacement error encountered.");
		}
		catch (InvalidOperatorException exception) {
			throw new InvalidOperatorException("Expression error. Invalid operators encountered. Valid operator symbols are +, -, *, / and ^.");
		}
		catch (DivisionByZeroException exception) {
			throw new DivisionByZeroException("Expression error. Division by zero encountered.");
		}
		catch (EmptyExpressionException exception) {
			throw new EmptyExpressionException("Expression error. Empty expression encountered.");
		}
		catch (NumberMisplacementException exception) {
			throw new NumberMisplacementException("Expression error. Number misplacement encountered. Numbers should be separated by arithmetic operators.");
		}
	}
}

