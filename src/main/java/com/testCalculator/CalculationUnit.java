package com.testCalculator;

/**
 * This class takes an object representing reverse polish notation and 
 * calculates the value of the expression.
 * 
 * @author I517939
 *
 */
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
	/**
	 * The final result after the calculation.
	 */
	private Double calculationResult;
	
	/**
	 * Forbidding default constructor.
	 */
	private CalculationUnit()
	{}
	
	/**
	 * Constructor from reverse polish expression. 
	 * Can be accessed only via a factory method.
	 * 
	 * @param expression
	 * @throws Exception
	 */
	private CalculationUnit(ReversePolishExpression expression) throws Exception
	{
		this.polishExpression = expression;
		this.setCalculationResult(null);
		
		this.calculate();
	}
	
	/**
	 * Factory method constructing and returning a CalculationUnit object from a
	 * reverse polish expression object.
	 * 
	 * @param expression
	 * @return CalculationUnit
	 * @throws Exception
	 */
	public static CalculationUnit constructFromParser(ReversePolishExpression expression) throws Exception
	{
		return new CalculationUnit(expression);
	}
	
	/**
	 * Calculates the value of the polish expression by modifying
	 * the corresponding local field.
	 * 
	 * @throws Exception
	 */
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
	
	/**
	 * Function used to return the operation type.
	 * If the operation is an invalid one it throws an exception.
	 * Valid operations are +,-,/ and *.
	 * 
	 * @param operation
	 * @return Operation
	 * @throws Exception
	 */
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

	/**
	 * Based on a value from Operation enum calls a corresponding operation method.
	 * Throws an Exception when an unexpected operation is submitted.
	 * 
	 * @param left
	 * @param right
	 * @param operation
	 * @return Double
	 * @throws Exception
	 */
	private Double calculate(Double left, Double right, Operation operation) throws Exception
	{
		switch(operation)
		{
		case PLUS:
			return this.getSum(left, right);
		case MINUS:
			return this.getMinus(left, right);
		case MULTIPLICATION:
			return this.getProduct(left, right);
		case DIVISION:
			return this.getDivision(left, right);
		default:
			throw new Exception("Unexpected operation.");
		}
	}
	
	/**
	 * @param left
	 * @param right
	 * @return Double
	 */
	private Double getSum(Double left, Double right)
	{
		return Double.valueOf(left.doubleValue() + right.doubleValue());
	}
	
	/**
	 * @param left
	 * @param right
	 * @return Double
	 */
	private Double getMinus(Double left, Double right)
	{
		return Double.valueOf(left.doubleValue() - right.doubleValue());
	}
	
	/**
	 * @param left
	 * @param right
	 * @return Double
	 */
	private Double getProduct(Double left, Double right)
	{
		return Double.valueOf(left.doubleValue() * right.doubleValue());
	}
	
	/**
	 * @param left
	 * @param right
	 * @return Double
	 */
	private Double getDivision(Double left, Double right)
	{
		return Double.valueOf(left.doubleValue() / right.doubleValue());
	}

	
	/**
	 * @return Double
	 */
	public Double getCalculationResult() 
	{
		return calculationResult;
	}

	
	/**
	 * @param calculationResult
	 */
	private void setCalculationResult(Double calculationResult) 
	{
		this.calculationResult = calculationResult;
	}
}
