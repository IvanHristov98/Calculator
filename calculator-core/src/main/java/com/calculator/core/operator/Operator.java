package com.calculator.core.operator;

public abstract class Operator implements Comparable<Operator> {
	public abstract int getPriority();

	protected String symbolicRepresentation;

	@Override
	public int compareTo(Operator other) {
		return Integer.compare(getPriority(), other.getPriority());
	}

	public String getSymbolicRepresentation() {
		return symbolicRepresentation;
	}
}
