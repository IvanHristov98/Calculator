package com.calculator.core;

import com.calculator.core.exception.CalculatorException;
import com.calculator.core.exception.InvalidOperatorException;
import com.calculator.core.exception.NumberMisplacementException;
import com.calculator.core.operator.ArithmeticOperator;
import com.calculator.core.operator.OperatorChecker;
import com.calculator.core.operator.OperatorFactory;

import java.util.EmptyStackException;
import java.util.Stack;

public class PostfixExpressionCalculator implements IExpressionFilter {
	private ExpressionTokenSplitter expressionTokenSplitter;
	private NumberChecker numberChecker;

	public PostfixExpressionCalculator(ExpressionTokenSplitter expressionTokenSplitter, NumberChecker numberChecker) {
		this.expressionTokenSplitter = expressionTokenSplitter;
		this.numberChecker = numberChecker;
	}

	public Expression process(Expression expression) throws CalculatorException {
		Stack<Double> numbers = new Stack<>();

		try {
			numbers = getPostfixExpressionValue(expression, numbers);
		} catch (EmptyStackException exception) {
			throw new NumberMisplacementException("Invalid number of numbers has been received.");
		}

		if (numbers.size() > 1) {
			throw new NumberMisplacementException("Invalid number of numbers has been received.");
		}

		return new Expression(numbers.peek().toString());
	}

	private Stack<Double> getPostfixExpressionValue(Expression expression, Stack<Double> numbers)
			throws CalculatorException {
		for (String token : expressionTokenSplitter.getExpressionTokens((FormattedExpression)expression)) {
			if (numberChecker.isNumber(token)) {
				numbers = addNumberToNumbersStack(numbers, token);
			} else if (OperatorChecker.isArithmeticOperator(OperatorFactory.makeOperator(token))) {
				numbers = operateWithNumbersFromStackTop(numbers, token);
			} else {
				throw new InvalidOperatorException("Unexpected operator has been received.");
			}
		}

		return numbers;
	}

	private Stack<Double> addNumberToNumbersStack(Stack<Double> numbers, String token) {
		numbers.add(toNumber(token));
		return numbers;
	}

	protected Double toNumber(String token) throws NumberFormatException {
		return Double.parseDouble(token);
	}

	private Stack<Double> operateWithNumbersFromStackTop(Stack<Double> numbers, String token)
			throws CalculatorException {
		Double rightNumber = numbers.pop();
		Double leftNumber = numbers.pop();
		ArithmeticOperator operator = (ArithmeticOperator) OperatorFactory.makeOperator(token);

		Double operationResult = operator.operate(leftNumber, rightNumber);
		numbers.push(operationResult);

		return numbers;
	}
}
