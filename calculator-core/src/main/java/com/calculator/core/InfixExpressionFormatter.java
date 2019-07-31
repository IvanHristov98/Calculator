package com.calculator.core;

import com.calculator.core.exception.*;

public class InfixExpressionFormatter extends ExpressionFilter {
	ExpressionModifier expressionModifier;

	public InfixExpressionFormatter(ExpressionModifier expressionModifier) {
		super();

		this.expressionModifier = expressionModifier;
	}

	public Expression process(Expression expression) throws CalculatorException {
		expression = this.expressionModifier.getExpressionWrappedWithBrackets(expression);
		expression = this.expressionModifier.getExpressionWithStrippedWhiteSpaces(expression);

		return new Expression(this.unify(expression.getContent()));
	}

	private String unify(String expression) throws CalculatorException {
		expression = this.stripRedundantSymbolsAtBeginning(expression);
		expression = this.splitTokensWithIntervals(expression).trim();

		return expression;
	}

	private String stripRedundantSymbolsAtBeginning(String expression) {
		// There is no need to have + operators at the beginning of a subexpression
		return expression.replaceAll("(\\()[+]+", "$1");
	}

	private String splitTokensWithIntervals(String expression) {
		expression = this.splitNumbersWithIntervals(expression);
		expression = this.splitOperatorsWithIntervals(expression);
		expression = this.mergeNegativeNumbersAtBeginning(expression);

		return expression;
	}

	private String splitNumbersWithIntervals(String expression) {
		// Whenever a number of the format mantissa.exponent is found
		// a whitespace is added in front of it.
		return expression.replaceAll("([0-9.]+)", " $1");
	}

	private String splitOperatorsWithIntervals(String expression) {
		// Whenever a valid operator is found
		// a whitespace character is added in front of it.
		return expression.replaceAll("([-+*/^()])", " $1");
	}

	private String mergeNegativeNumbersAtBeginning(String expression) {
		// A negative number can only be found at the start of the expression
		// or after an opening bracket.
		return expression.replaceFirst("(\\( -) ([0-9]+)", "$1$2");
	}
}
