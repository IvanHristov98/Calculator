package com.calculator.core.operator;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import com.calculator.core.exception.InvalidOperatorException;


public class OperatorFactoryTest
{
	@Test
	public void test_plus_makeOperator() throws InvalidOperatorException
	{
		Operator operator = OperatorFactory.makeOperator("+");
		
		assertTrue((operator instanceof PlusOperator));
	}
	
	@Test
	public void test_minus_makeOperator() throws InvalidOperatorException
	{
		Operator operator = OperatorFactory.makeOperator("-");
		
		assertTrue((operator instanceof MinusOperator));
	}
	
	@Test
	public void test_multiplication_makeOperator() throws InvalidOperatorException
	{
		Operator operator = OperatorFactory.makeOperator("*");
		
		assertTrue((operator instanceof ProductOperator));
	}
	
	@Test
	public void test_division_makeOperator() throws InvalidOperatorException
	{
		Operator operator = OperatorFactory.makeOperator("/");
		
		assertTrue((operator instanceof DivisionOperator));
	}
	
	@Test
	public void test_pow_makeOperator() throws InvalidOperatorException
	{
		Operator operator = OperatorFactory.makeOperator("^");
		
		assertTrue((operator instanceof PowOperator));
	}
	
	@Test(expected = InvalidOperatorException.class)
	public void test_invalidOperator_makeOperator() throws InvalidOperatorException
	{
		OperatorFactory.makeOperator("A");
	}
}
