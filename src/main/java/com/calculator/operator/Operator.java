package com.calculator.operator;

public abstract class Operator implements Comparable<Operator>
{
	public abstract int getPriority();
	
	@Override
	public int compareTo(Operator other)
	{
		return Integer.compare(this.getPriority(), other.getPriority());
	}
}
