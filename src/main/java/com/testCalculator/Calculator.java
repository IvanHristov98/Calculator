package com.testCalculator;

class Calculator
{	
	private Calculator()
	{}
	
	/**
	 * 
	 * @param <T>
	 * @param left
	 * @param right
	 * @param operation
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static<T extends Number> T calculate(T left, T right, String operation) throws Exception
	{				
		if (operation.equals("+"))
		{
			return (T)(Double.valueOf(left.doubleValue() + right.doubleValue()));
		}
		else if (operation.equals("-"))
		{
			return (T)Double.valueOf(left.doubleValue() - right.doubleValue());
		}
		else if (operation.equals("*"))
		{
			return (T)Double.valueOf(left.doubleValue() * right.doubleValue());
		}
		else if (operation.equals("/"))
		{
			return (T)Double.valueOf(left.doubleValue() / right.doubleValue());
		}
		
		throw new Exception("Not a valid sign");
	}
}
