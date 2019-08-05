package com.calculator.web.wrapper;

import com.calculator.core.Expression;
import com.calculator.core.exception.*;

public class CalculatorAdapter implements ICalculator {
	
	com.calculator.core.ICalculator calculator;
	
	public CalculatorAdapter(com.calculator.core.ICalculator calculator) {
		this.calculator = calculator;
	}
	
	@Override
	public Double calculate(Expression expression) throws Exception {
		try {
			return calculator.calculate(expression);
		} catch (BracketsException exception) {
			throw new Exception("Expression error. Brackets misplacement has been encountered.");
		} catch (OperatorMisplacementException exception) {
			throw new Exception("Expression error. Operator misplacement has been encountered.");
		} catch (DivisionByZeroException exception) {
			throw new Exception("Expression error. Division by zero encountered.");
		} catch (EmptyExpressionException exception) {
			throw new Exception("Expression error. Empty expressions are not permitted.");
		} catch (InvalidOperatorException exception) {
			throw new Exception("Expression error. An invalid operator has been encountered.");
		} catch (NumberMisplacementException exception) {
			throw new Exception("Expression error. An invalid number ordering has been encountered.");
		}
	}
}
