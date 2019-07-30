package com.calculator.core;

public class CalculatorFactory {
	public CalculatorFactory() {}
	
	public Calculator makeCalculator() {
		ExpressionModifier expressionModifier = makeExpressionModifier();
		UnformattedInfixExpressionValidator infixValidator = makeUnformattedInfixExpressionValidator(expressionModifier);
		InfixExpressionFormatter infixFormatter = makeInfixExpressionFormatter(expressionModifier);
		
		ExpressionTokenSplitter tokenSplitter = makeExpressionTokenSplitter();
		NumberChecker numberChecker = makeNumberChecker();
		InfixToPostfixExpressionTranslator postfixTranslator = makeInfixToPostfixExpressionTranslator(tokenSplitter, numberChecker);
		
		InfixExpressionFilter infixFilter = makeInfixExpressionFilter(infixValidator, infixFormatter, postfixTranslator);
		
		PostfixExpressionCalculator postfixCalculator = makePostfixExpressionCalculator(tokenSplitter, numberChecker);
		
		return new Calculator(infixFilter, postfixCalculator);
	}
	
	private ExpressionModifier makeExpressionModifier() {
		return new ExpressionModifier();
	}
	
	private UnformattedInfixExpressionValidator makeUnformattedInfixExpressionValidator(ExpressionModifier expressionModifier) {
		return new UnformattedInfixExpressionValidator(expressionModifier);
	}
	
	private InfixExpressionFormatter makeInfixExpressionFormatter(ExpressionModifier expressionModifier) {
		return new InfixExpressionFormatter(expressionModifier);
	}
	
	private ExpressionTokenSplitter makeExpressionTokenSplitter() {
		return new ExpressionTokenSplitter();
	}
	
	private NumberChecker makeNumberChecker() {
		return new NumberChecker();
	}
	
	private InfixToPostfixExpressionTranslator makeInfixToPostfixExpressionTranslator(
			ExpressionTokenSplitter tokenSplitter,
			NumberChecker numberChecker
			) {
		return new InfixToPostfixExpressionTranslator(tokenSplitter, numberChecker);
	}
	
	private InfixExpressionFilter makeInfixExpressionFilter(
			UnformattedInfixExpressionValidator infixValidator,
			InfixExpressionFormatter infixFormatter,
			InfixToPostfixExpressionTranslator postfixTranslator
			) {
		return new InfixExpressionFilter(infixValidator, infixFormatter, postfixTranslator);
	}
	
	private PostfixExpressionCalculator makePostfixExpressionCalculator(
			ExpressionTokenSplitter tokenSplitter,
			NumberChecker numberChecker
			) {
		return new PostfixExpressionCalculator(tokenSplitter, numberChecker);
	}
}
