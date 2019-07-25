package com.calculator.core.exception;

public final class DivisionByZeroException extends CalculatorException {
	private static final long serialVersionUID = 4273590546184072262L;

	public DivisionByZeroException(String message) {
		super(message);
	}
}
