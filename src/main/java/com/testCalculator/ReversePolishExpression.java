package com.testCalculator;

import java.util.Stack;
import java.util.regex.Pattern;

class ReversePolishExpression<T extends Number>
{
	private Stack<T> numbers;
	private Stack<String> operations;
	
	/**
	 * Initializer function.
	 */
	private void init()
	{
		this.numbers = new Stack<T>();
		this.operations = new Stack<String>();
	}
	
	public ReversePolishExpression()
	{
		this.init();
	}
	
	@SuppressWarnings("unchecked")
	public ReversePolishExpression(ReversePolishExpression<T> other)
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
	public void addNumber(T number)
	{
		this.numbers.add(number);
	}
	
	private void validateOperation(String operation) throws Exception
	{
		if (Pattern.matches("^+$|^-$|^*$|^/$", operation))
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
	 * @return T | null
	 */
	public T nextNum()
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
	 * @return T | null
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

	
	private <E extends Number> Stack<E> cloneNumberStack(Stack<E> other)
	{
		Stack<E> copy = new Stack<E>();
		
		for (E item : other)
		{
			copy.add(this.<E>copyNumber(item));
		}
		
		return copy;
	}
	
	@SuppressWarnings("unchecked")
	private <E extends Number> E copyNumber(E number)
	{
		return (E)((Number)Double.valueOf(number.doubleValue()));
	}
}
