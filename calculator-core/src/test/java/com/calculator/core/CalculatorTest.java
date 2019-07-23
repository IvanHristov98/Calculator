package com.calculator.core;

import com.calculator.core.exception.*;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CalculatorTest
{	
	private static final double ALLOWED_ERROR = 0.01;
	
	private double calculate(String expressionContent) throws Exception
	{
		Expression expression = new Expression(expressionContent);
		UnformattedInfixExpressionValidator infixValidator = new UnformattedInfixExpressionValidator(expression);
		InfixExpressionFormatUnifier infixFormatter = new InfixExpressionFormatUnifier(expression);
		InfixToPostfixExpressionTranslator postfixTranslator = new InfixToPostfixExpressionTranslator(expression);
		PostfixExpressionCalculator postfixCalculator = new PostfixExpressionCalculator(expression);
		
		Calculator calc  = new Calculator(
				expression, 
				infixValidator,
				infixFormatter,
				postfixTranslator,
				postfixCalculator
				);
		return calc.calculate();
	}
	
	@Test
	public void sumAndSubtractMultipleNumbers_calculate() throws Exception
	{
		assertEquals(5.8, this.calculate("3.5+2.3+3-3"), CalculatorTest.ALLOWED_ERROR);
	}
	
	@Test(expected = OperatorMisplacementException.class)
	public void multiplyNegativeNumbers_calculate() throws Exception
	{
		assertEquals(1, this.calculate("-1*-1"), 0);
	}

	@Test(expected = DivisionByZeroException.class)
	public void divideByZero_calculate() throws Exception
	{
		this.calculate("1/0");
	}
	
	@Test
	public void associativityWithBrackets_calculate() throws Exception
	{
		assertEquals(8, this.calculate("2+(3/2)*4"), CalculatorTest.ALLOWED_ERROR);
	}
	
	@Test(expected = BracketsException.class)
	public void missedOpeningBracket_calculate() throws Exception
	{
		this.calculate("3+4)/2");
	}
	
	@Test(expected = BracketsException.class)
	public void missedClosingBracket_calculate() throws Exception
	{
		this.calculate("(3+4/2");
	}
	
	@Test(expected = OperatorMisplacementException.class)
	public void operatorCount_calculate() throws Exception
	{
		this.calculate("3++5/2");
	}
	
	@Test(expected = OperatorMisplacementException.class)
	public void operatorMisplacementOnEachSide_calculate() throws Exception
	{
		this.calculate("*3+5+");
	}
	
	@Test(expected = InvalidOperatorException.class)
	public void letterInsteadOfNumber_calculate() throws Exception
	{
		this.calculate("5A5+3");
	}
	
	@Test(expected = NumberMisplacementException.class)
	public void spacedNumbers() throws Exception
	{
		this.calculate("5 4+6");
	}
	
	@Test
	public void spacedExpression_calculate() throws Exception
	{
		assertEquals(17, this.calculate("1 + 2*(3 +5)"), CalculatorTest.ALLOWED_ERROR);
	}
}
