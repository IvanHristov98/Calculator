package com.calculator.core;

@FunctionalInterface
interface ExceptionThrowingFunction<T, R, E extends Exception> {
	R apply(T arg) throws E;
}
