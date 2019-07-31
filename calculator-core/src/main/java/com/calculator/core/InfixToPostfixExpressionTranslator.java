package com.calculator.core;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import com.calculator.core.exception.*;
import com.calculator.core.operator.*;

public class InfixToPostfixExpressionTranslator extends ExpressionFilter {
	private ExpressionTokenSplitter expressionTokenSplitter;
	private NumberChecker numberChecker;

	public InfixToPostfixExpressionTranslator(ExpressionTokenSplitter expressionTokenSplitter,
			NumberChecker numberChecker) {
		super();

		this.expressionTokenSplitter = expressionTokenSplitter;
		this.numberChecker = numberChecker;
	}

	public Expression process(Expression expression) throws CalculatorException {
		return new FormattedExpression(toPostfixExpression(expressionTokenSplitter.getExpressionTokens((FormattedExpression)expression)));
	}

	private String toPostfixExpression(String[] tokens) throws OperatorException {
		Queue<String> output = new LinkedList<>();
		Stack<String> operators = new Stack<>();

		readTokens(output, operators, tokens);
		moveTokensFromOperatorsStackToOutputQueue(output, operators);

		return popQueueToString(output);
	}

	private void readTokens(Queue<String> output, Stack<String> operators, String[] tokens) throws OperatorException {
		for (String token : tokens) {
			distributeToken(output, operators, token);
		}
	}

	private void distributeToken(Queue<String> output, Stack<String> operators, String token) throws OperatorException {
		if (numberChecker.isNumber(token)) {
			addItemToOutput(output, token);
		} else if (OperatorChecker.isArithmeticOperator(convertToOperator(token))) {
			addArithmeticOperatorToOperatorStack(output, operators, token);
		} else if (OperatorChecker.isLeftBracket(convertToOperator(token))) {
			addItemToOperatorStack(operators, token);
		} else if (OperatorChecker.isRightBracket(convertToOperator(token))) {
			moveTokensFromOperatorStackToOutputUntilLeftBracket(operators, output);
		}
	}

	private void addItemToOutput(Queue<String> output, String token) {
		output.add(token);
	}

	private Operator convertToOperator(String operator) throws InvalidOperatorException {
		return OperatorFactory.makeOperator(operator);
	}

	private void addArithmeticOperatorToOperatorStack(Queue<String> output, Stack<String> operators, String token)
			throws OperatorException {
		while (!operators.empty() && shouldPopFromTheOperatorStack(operators, token)) {
			moveTokenFromOperatorStackToOutput(operators, output);
		}

		addItemToOperatorStack(operators, token);
	}

	private boolean shouldPopFromTheOperatorStack(Stack<String> operators, String token)
			throws InvalidOperatorException {
		Operator nextOperator = convertToOperator(operators.peek());
		Operator current = convertToOperator(token);

		if (OperatorChecker.isLeftBracket(nextOperator)) {
			return false;
		}

		boolean result = nextOperator.compareTo(current) > 0;
		result |= (nextOperator.compareTo(current) == 0 && ((ArithmeticOperator) nextOperator).isLeftAssociative());

		return result;
	}

	private void moveTokenFromOperatorStackToOutput(Stack<String> operators, Queue<String> output) {
		addItemToOutput(output, operators.pop());
	}

	private void addItemToOperatorStack(Stack<String> operators, String token) {
		operators.add(token);
	}

	private void moveTokensFromOperatorStackToOutputUntilLeftBracket(Stack<String> operators, Queue<String> output)
			throws OperatorException {
		while (!operators.empty() && !OperatorChecker.isLeftBracket(convertToOperator(operators.peek()))) {
			moveTokenFromOperatorStackToOutput(operators, output);
		}

		// Removing the opening bracket
		if (!operators.empty()) {
			operators.pop();
		} else {
			throw new BracketsException("Brackets error encountered - missing opening bracket.");
		}
	}

	private void moveTokensFromOperatorsStackToOutputQueue(Queue<String> output, Stack<String> operators)
			throws OperatorException {
		while (!operators.empty()) {
			Operator topOperator = convertToOperator(operators.peek());

			if (OperatorChecker.isBracket(topOperator)) {
				throw new BracketsException("Brackets error encountered - missing closing bracket.");
			}

			moveTokenFromOperatorStackToOutput(operators, output);
		}
	}

	private String popQueueToString(Queue<String> output) {
		StringBuilder outputString = new StringBuilder();
		final String WHITE_SPACE = " ";

		while (!output.isEmpty()) {
			outputString.append(WHITE_SPACE).append(output.remove());
		}

		// result with surrounding whitespaces removed
		return outputString.toString().trim();
	}
}