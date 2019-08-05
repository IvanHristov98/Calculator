package com.calculator.web.wrapper;

import com.calculator.core.Expression;
import com.calculator.core.exception.*;
import com.calculator.web.wrapper.exception.WebCalculatorException;

public class CalculatorAdapter implements ICalculator {
	
	com.calculator.core.ICalculator calculator;
	
	public CalculatorAdapter(com.calculator.core.ICalculator calculator) {
		this.calculator = calculator;
	}
	
	@Override
	public Double calculate(Expression expression) throws WebCalculatorException {
		try {
			return calculator.calculate(expression);
		} catch (BracketsException exception) {
			throw new WebCalculatorException("Expression error. Brackets misplacement has been encountered.");
		} catch (OperatorMisplacementException exception) {
			throw new WebCalculatorException("Expression error. Operator misplacement has been encountered.");
		} catch (DivisionByZeroException exception) {
			throw new WebCalculatorException("Expression error. Division by zero encountered.");
		} catch (EmptyExpressionException exception) {
			throw new WebCalculatorException("Expression error. Empty expressions are not permitted.");
		} catch (InvalidOperatorException exception) {
			throw new WebCalculatorException("Expression error. An invalid operator has been encountered.");
		} catch (NumberMisplacementException exception) {
			throw new WebCalculatorException("Expression error. An invalid number ordering has been encountered.");
		} catch (CalculatorException exception) {
			throw new WebCalculatorException("Expression error. Invalid expression given.");
		}
	}
}
