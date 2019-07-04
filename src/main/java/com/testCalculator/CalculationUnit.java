package com.testCalculator;

class CalculationUnit
{
	enum Operation
	{
		PLUS,
		MINUS,
		MULTIPLICATION,
		DIVISION
	}
	
	private ReversePolishExpression polishExpression;
	private Double calculationResult;
	
	private CalculationUnit()
	{}
	
	private CalculationUnit(ReversePolishExpression expression) throws Exception
	{
		this.polishExpression = expression;
		this.setCalculationResult(null);
		
		this.calculate();
	}
	
	public static CalculationUnit constructFromParser(ReversePolishExpression expression) throws Exception
	{
		return new CalculationUnit(expression);
	}
	
	private void calculate() throws Exception
	{
		if (!polishExpression.hasNextNum()) { return; }
		
		Double prev = polishExpression.nextNum();
		
		while (polishExpression.hasNextNum() && polishExpression.hasNextOperation())
		{
			Double curr = polishExpression.nextNum();			
			Operation operation = this.getOperationType(polishExpression.nextOperation());
			
			prev = calculate(curr, prev, operation);
		}
		
		this.setCalculationResult(prev);
	}
	
	private Operation getOperationType(String operation) throws Exception
	{
		if (operation.equals("+"))
		{
			return Operation.PLUS;
		}
		else if (operation.equals("-"))
		{
			return Operation.MINUS;
		}
		else if (operation.equals("*"))
		{
			return Operation.MULTIPLICATION;
		}
		else if (operation.equals("/"))
		{
			return Operation.DIVISION;
		}
		
		throw new Exception("Invalid operation.");
	}

	private Double calculate(Double left, Double right, Operation operation) throws Exception
	{
		switch(operation)
		{
		case PLUS:
			return this.getSum(left, right);
		case MINUS:
			return this.getMinus(left, right);
		case MULTIPLICATION:
			return this.getMultiplication(left, right);
		case DIVISION:
			return this.getDivision(left, right);
		default:
			throw new Exception("Invalid operation.");
		}
	}
	
	private Double getSum(Double left, Double right)
	{
		return Double.valueOf(left.doubleValue() + right.doubleValue());
	}
	
	private Double getMinus(Double left, Double right)
	{
		return Double.valueOf(left.doubleValue() - right.doubleValue());
	}
	
	private Double getMultiplication(Double left, Double right)
	{
		return Double.valueOf(left.doubleValue() * right.doubleValue());
	}
	
	
	private Double getDivision(Double left, Double right)
	{
		return Double.valueOf(left.doubleValue() / right.doubleValue());
	}

	
	public Double getCalculationResult() 
	{
		return calculationResult;
	}

	
	public void setCalculationResult(Double calculationResult) 
	{
		this.calculationResult = calculationResult;
	}
}
