package com.calculator.cli.coreWrapper;

import com.calculator.core.*;

public class CalculatorSupplier {
	private Expression expression;
	
	public CalculatorSupplier() {}
	
	public Calculator supplyCalculator(String expressionContent) {
		this.expression = new Expression(expressionContent);
		
		UnformattedInfixExpressionValidator infixExpressionValidator = this.supplyUnformattedInfixExpressionValidator();
		InfixExpressionFormatter infixExpressionFormatter = this.supplyInfixExpressionFormatter();
		InfixToPostfixExpressionTranslator postfixTranslator = this.supplyInfixToPostfixExpressionTranslator();
		PostfixExpressionCalculator postfixCalculator = this.supplyPostfixExpressionCalculator();
		
		Calculator calculator = new Calculator(
				this.expression,
				infixExpressionValidator,
				infixExpressionFormatter,
				postfixTranslator,
				postfixCalculator
				);
		
		return calculator;
	}
	
	private UnformattedInfixExpressionValidator supplyUnformattedInfixExpressionValidator() {
		ExpressionModifier expressionModifier = new ExpressionModifier();
		
		return new UnformattedInfixExpressionValidator(this.expression, expressionModifier);
	}
	
	private InfixExpressionFormatter supplyInfixExpressionFormatter() {
		ExpressionModifier expressionModifier = new ExpressionModifier();
		
		return new InfixExpressionFormatter(this.expression, expressionModifier);
	}
	
	private InfixToPostfixExpressionTranslator supplyInfixToPostfixExpressionTranslator() {
		ExpressionTokenSplitter tokenSplitter = new ExpressionTokenSplitter();
		NumberChecker numberChecker = new NumberChecker();
		
		return new InfixToPostfixExpressionTranslator(this.expression, tokenSplitter, numberChecker);
	}
	
	private PostfixExpressionCalculator supplyPostfixExpressionCalculator() {
		ExpressionTokenSplitter tokenSplitter = new ExpressionTokenSplitter();
		NumberChecker numberChecker = new NumberChecker();
		
		return new PostfixExpressionCalculator(this.expression, tokenSplitter, numberChecker);
	}
	
}
