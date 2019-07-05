package com.calculator;

import java.util.Stack;
import java.util.regex.Pattern;

/**
 * This class encapsulates the principles of using a reverse
 * polish notation by maintaining two stacks. One for numbers
 * and the other for operations.
 * 
 * @author I517939
 *
 */
class ReversePolishExpression
{
	private Stack<Double> numbers;
	private Stack<String> operations;
	
	/**
	 * Initializer function.
	 */
	private void init()
	{
		this.numbers = new Stack<Double>();
		this.operations = new Stack<String>();
	}
	
	
	public ReversePolishExpression()
	{
		this.init();
	}
	
	public ReversePolishExpression(ReversePolishExpression other)
	{
		this.numbers = this.cloneNumberStack(other.numbers);
		this.operations = (Stack<String>) other.operations.clone();
	}
	
	/**
	 * Discard the old number and operation Reverse Polish stacks and reinitialize 
	 * the variables.
	 */
	public void reset()
	{
		this.init();
	}
	
	/**
	 * Adds a number within the Reverse Polish Number Stack.
	 * 
	 * @param number
	 */
	public void addNumber(Double number)
	{
		this.numbers.add(number);
	}
	
	/**
	 * Uses a regex to validate whether the String is a valid mathematical operation.
	 */
	private void validateOperation(String operation) throws Exception
	{
		System.out.println(operation);
		if (!Pattern.matches("^[-+*/]$", operation))
		{
			throw new Exception("Invalid operation.");
		}
	}
	
	/**
	 * Adds an operation within the Reverse Polish Operation Stack.
	 * 
	 * @param operation
	 */
	public void addOperation(String operation) throws Exception
	{
		this.validateOperation(operation);
		
		this.operations.add(operation);
	}
	
	/**
	 * Returns the next number by popping it from the Reverse Polish Stack.
	 * On an empty stack it returns null.
	 * 
	 * @return Double | null
	 */
	public Double nextNum()
	{
		if (!this.hasNextNum())
		{
			return null;
		}
		
		return this.numbers.pop();
	}
	
	/**
	 * Returns true if there is a number within the Reverse Polish number stack 
	 * and false otherwise.
	 * 
	 * @return boolean
	 */
	public boolean hasNextNum()
	{
		return !(this.numbers.empty());
	}
	
	/**
	 * Returns the next operation if there is a such within the Reverse Polish
	 * stack and null otherwise.
	 * 
	 * @return String | null
	 */
	public String nextOperation()
	{
		if (!this.hasNextOperation())
		{
			return null;
		}
		
		return this.operations.pop();
	}
	
	/**
	 * Returns true if there is a next operation within the Reverse Polish operation stack
	 * and false otherwise.
	 * 
	 * @return boolean
	 */
	public boolean hasNextOperation()
	{
		return !(this.operations.empty());
	}

	/**
	 * Function used to deep copy stack<Double> elements.
	 * 
	 * @param other
	 * @return Stack<Double>
	 */
	private Stack<Double> cloneNumberStack(Stack<Double> other)
	{
		Stack<Double> copy = new Stack<Double>();
		
		for (Double item : other)
		{
			copy.add(Double.valueOf(item.doubleValue()));
		}
		
		return copy;
	}
}
