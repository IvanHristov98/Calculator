package com.calculator.core;

import com.calculator.core.exception.*;

import java.util.regex.Pattern;

public class UnformattedInfixExpressionValidator extends ExpressionFilter {
	ExpressionModifier expressionModifier;

	public UnformattedInfixExpressionValidator(ExpressionModifier expressionModifier) {
		super();
		this.expressionModifier = expressionModifier;
	}

	public Expression process(Expression expression) throws CalculatorException {

		expression = this.expressionModifier.getExpressionWrappedWithBrackets(expression);
		String expressionContent = expression.getContent();

		this.validateIfAnyConsecutiveNumbers(expressionContent);
		this.validateNumbersFormat(expressionContent);

		expression = this.expressionModifier.getExpressionWithStrippedWhiteSpaces(expression);
		expressionContent = expression.getContent();

		this.validateIfExpressionHasEmptySubexpressions(expressionContent);
		this.validateOperatorSequence(expressionContent);
		this.validateTokens(expressionContent);
		this.validateIfAnyNumbersAreGluedAroundBracketedExpression(expressionContent);
		this.validateSubexpressionEnds(expressionContent);

		return expression;
	}

	private void validateIfAnyConsecutiveNumbers(String expressionContent) throws NumberMisplacementException {
		if (this.hasConsecutiveNumbers(expressionContent)) {
			throw new NumberMisplacementException(
					"Invalid expression. Numbers should be separated by a valid operator.");
		}
	}

	private boolean hasConsecutiveNumbers(String expressionContent) {
		return expressionContent.matches(".*[0-9.][ ]+[0-9.].*");
	}

	private void validateNumbersFormat(String expressionContent) throws NumberMisplacementException {
		if (this.hasAnInvalidNumber(expressionContent)) {
			throw new NumberMisplacementException("The given expression contains invalid numbers.");
		}
	}

	private boolean hasAnInvalidNumber(String expressionContent) {
		// checks if there are two floating point numbers one after another
		// following the mantissa_1.exponent_1mantissa_2.exponent_2
		return Pattern.matches(".*[0-9]+\\.[0-9]*\\..*", expressionContent);
	}

	private void validateIfExpressionHasEmptySubexpressions(String expressionContent) throws EmptyExpressionException {
		if (expressionContent.contains("()")) {
			throw new EmptyExpressionException("An empty expression is not a valid one.");
		}
	}

	private void validateOperatorSequence(String expressionContent) throws OperatorMisplacementException {
		if (this.hasConsecutiveOperators(expressionContent)) {
			throw new OperatorMisplacementException("The given expression contains consecutive operators.");
		}
	}

	private boolean hasConsecutiveOperators(String expressionContent) {
		// There should be no two consecutive operators -, +, *, / or ^
		return Pattern.matches(".*[-+*/^]{2,}.*", expressionContent);
	}

	private void validateTokens(String expressionContent) throws InvalidOperatorException {
		if (this.hasInvalidTokens(expressionContent)) {
			throw new InvalidOperatorException("The given expression contains invalid tokens.");
		}
	}

	private boolean hasInvalidTokens(String expressionContent) {
		// An expression should only contain the symbols -, +, *, /, ^, (, ), ., 0-9
		return Pattern.matches(".*[^-+*/^()0-9.].*", expressionContent);
	}

	private void validateIfAnyNumbersAreGluedAroundBracketedExpression(String expressionContent)
			throws BracketsException {
		if (this.hasNumbersGluedAroundBracketExpression(expressionContent)) {
			throw new BracketsException(
					"It is not allowed to have a number right before a '(' symbol or right after a ')' symbol.");
		}
	}

	private boolean hasNumbersGluedAroundBracketExpression(String expressionContent) {
		// Prevents having expressions with the format number1([sub_expression])number2
		return expressionContent.matches(".*([0-9.]\\(|\\)[0-9]).*");
	}

	private void validateSubexpressionEnds(String expressionContent) throws CalculatorException {
		this.validateExpressionAfterOpeningBrackets(expressionContent);
		this.validateExpressionBeforeClosingBrackets(expressionContent);
	}

	private void validateExpressionAfterOpeningBrackets(String expressionContent) throws OperatorMisplacementException {
		// there shouldn't be an *, / or ^ symbol right after an opening bracket
		this.validateStringByPattern(expressionContent, ".*\\([*\\/^].*");
	}

	private void validateExpressionBeforeClosingBrackets(String expressionContent)
			throws OperatorMisplacementException {
		// A there should be no arithmetic operators right before a closing bracket
		this.validateStringByPattern(expressionContent, ".*[-+*\\/^]\\).*");
	}

	private void validateStringByPattern(String string, String invalidPattern) throws OperatorMisplacementException {
		if (Pattern.matches(invalidPattern, string)) {
			throw new OperatorMisplacementException("Expression is not ordered properly.");
		}
	}
}
