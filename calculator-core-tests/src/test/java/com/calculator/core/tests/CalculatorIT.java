package com.calculator.core.tests;

import org.junit.Before;
import org.junit.Test;

import com.calculator.core.*;
import com.calculator.core.exception.*;

public class CalculatorIT {
	public Calculator calculator;
	
	@Before
	public void setUp() {
		this.calculator = this.getCalculator();
	}

	@Test(expected = OperatorMisplacementException.class)
	public void operatorMisplacement() throws CalculatorException {
		this.calculator.calculate(this.getExpression("1+-2"));
	}
	
	@Test(expected = BracketsException.class)
	public void bracketsExcepion_calculate() throws CalculatorException {
		this.calculator.calculate(this.getExpression("(1+2"));
	}
	
	@Test(expected = DivisionByZeroException.class)
	public void divisionByZero_calculate() throws CalculatorException {
		this.calculator.calculate(this.getExpression("1/0"));
	}
	
	@Test(expected = EmptyExpressionException.class)
	public void emptyExpression_calculate() throws CalculatorException {
		this.calculator.calculate(this.getExpression(""));
	}
	
	@Test(expected = InvalidOperatorException.class)
	public void invalidOperator_calculate() throws CalculatorException {
		this.calculator.calculate(this.getExpression("1A2"));
	}
	
	@Test(expected = NumberMisplacementException.class)
	public void numberMisplacement_calculate() throws CalculatorException {
		this.calculator.calculate(this.getExpression("1 2"));
	}

	private Calculator getCalculator() {
		ExpressionModifier modifier = this.getExpressionModifier();
		ExpressionTokenSplitter tokenSplitter= this.getTokenSplitter();
		NumberChecker numberChecker = this.getNumberChecker();
		
		UnformattedInfixExpressionValidator validator = this.getValidator(modifier);
		InfixExpressionFormatter formatter = this.getFormatter(modifier);
		InfixExpressionFilter infixFilter = this.getInfixFilter(validator, formatter);
		
		InfixToPostfixExpressionTranslator translator = this.getTranslator(tokenSplitter, numberChecker);
		PostfixExpressionCalculator postfixCalculator = this.getPostfixCalculator(tokenSplitter, numberChecker);
		
		
		return new Calculator(infixFilter, translator, postfixCalculator);
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
	
	private UnformattedInfixExpressionValidator getValidator(ExpressionModifier modifier) {	
		return new UnformattedInfixExpressionValidator(modifier);
	}
	
	private InfixExpressionFormatter getFormatter(ExpressionModifier modifier) {
		return new InfixExpressionFormatter(modifier);
	}
	
	private InfixExpressionFilter getInfixFilter(
			UnformattedInfixExpressionValidator validator,
			InfixExpressionFormatter formatter
			) {
		return new InfixExpressionFilter(validator, formatter);
	}
	
	private InfixToPostfixExpressionTranslator getTranslator(
			ExpressionTokenSplitter tokenSplitter,
			NumberChecker numberChecker
			) {
		return new InfixToPostfixExpressionTranslator(tokenSplitter, numberChecker);
	}
	
	private PostfixExpressionCalculator getPostfixCalculator(
			ExpressionTokenSplitter tokenSplitter, 
			NumberChecker numberChecker
			) {
		return new PostfixExpressionCalculator(tokenSplitter, numberChecker);
	}
}
