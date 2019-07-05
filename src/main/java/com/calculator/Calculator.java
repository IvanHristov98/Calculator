package com.calculator;

public class Calculator
{	
	private MathematicalExpressionParser parser;
	
	private void init(String expression) throws Exception
	{
		this.parser = MathematicalExpressionParser.constructFromExpression(expression);
	}
	
	private Calculator()
	{}
	
	private Calculator(String expression) throws Exception
	{
		this.init(expression);
	}
	
	public static Calculator constructFromExpression(String expression) throws Exception
	{
		return new Calculator(expression);
	}
	
	public void changeExpression(String expression) throws Exception
	{
		this.init(expression);
	}
	
	public Double calculate() throws Exception
	{
		CalculationUnit  calcUnit = CalculationUnit.constructFromParser(this.parser.getRPExpression());
		
		return calcUnit.getCalculationResult();
	}
}