package com.calculator.core.tests;

import org.junit.Test;

import com.calculator.core.*;
import com.calculator.core.exception.*;

public class CalculatorIT {
	public Calculator calculator;

	@Test(expected = OperatorMisplacementException.class)
	public void operatorMisplacement() throws CalculatorException {
		this.calculator = this.getCalculator("1+-2");
		this.calculator.calculate();
	}
	
	@Test(expected = BracketsException.class)
	public void bracketsExcepion_calculate() throws CalculatorException {
		this.calculator = this.getCalculator("(1+2");
		this.calculator.calculate();
	}
	
	@Test(expected = DivisionByZeroException.class)
	public void divisionByZero_calculate() throws CalculatorException {
		this.calculator = this.getCalculator("1/0");
		this.calculator.calculate();
	}
	
	@Test(expected = EmptyExpressionException.class)
	public void emptyExpression_calculate() throws CalculatorException {
		this.calculator = this.getCalculator("");
		this.calculator.calculate();
	}
	
	@Test(expected = InvalidOperatorException.class)
	public void invalidOperator_calculate() throws CalculatorException {
		this.calculator = this.getCalculator("1A2");
		this.calculator.calculate();
	}
	
	@Test(expected = NumberMisplacementException.class)
	public void numberMisplacement_calculate() throws CalculatorException {
		this.calculator = this.getCalculator("1 2");
		this.calculator.calculate();
	}

	private Calculator getCalculator(String expressionContent) {
		Expression expression = this.getExpression(expressionContent);
		ExpressionModifier modifier = this.getExpressionModifier();
		ExpressionTokenSplitter tokenSplitter= this.getTokenSplitter();
		NumberChecker numberChecker = this.getNumberChecker();
		
		UnformattedInfixExpressionValidator validator = this.getValidator(expression, modifier);
		InfixExpressionFormatter formatter = this.getFormatter(expression, modifier);
		InfixToPostfixExpressionTranslator translator = this.getTranslator(expression, tokenSplitter, numberChecker);
		PostfixExpressionCalculator postfixCalculator = this.getPostfixCalculator(expression, tokenSplitter, numberChecker);
		
		
		return new Calculator(expression, validator, formatter, translator, postfixCalculator);
	}
	
	private Expression getExpression(String expressionContent) {
		return new Expression(expressionContent);
	}
	
	private ExpressionModifier getExpressionModifier() {
		return new ExpressionModifier();
	}
	
	private ExpressionTokenSplitter getTokenSplitter() {
		return new ExpressionTokenSplitter();
	}
	
	private NumberChecker getNumberChecker() {
		return new NumberChecker();
	}
	
	private UnformattedInfixExpressionValidator getValidator(Expression expression, ExpressionModifier modifier) {	
		return new UnformattedInfixExpressionValidator(expression, modifier);
	}
	
	private InfixExpressionFormatter getFormatter(Expression expression, ExpressionModifier modifier) {
		return new InfixExpressionFormatter(expression, modifier);
	}
	
	private InfixToPostfixExpressionTranslator getTranslator(
			Expression expression,
			ExpressionTokenSplitter tokenSplitter,
			NumberChecker numberChecker
			) {
		return new InfixToPostfixExpressionTranslator(expression, tokenSplitter, numberChecker);
	}
	
	private PostfixExpressionCalculator getPostfixCalculator(
			Expression expression, 
			ExpressionTokenSplitter tokenSplitter, 
			NumberChecker numberChecker
			) {
		return new PostfixExpressionCalculator(expression, tokenSplitter, numberChecker);
	}
}
