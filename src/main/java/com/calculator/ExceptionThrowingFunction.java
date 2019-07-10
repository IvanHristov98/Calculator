package com.calculator;

@FunctionalInterface
public interface ExceptionThrowingFunction<T, R, E extends Exception>
{
    R apply (T arg) throws E;
}
