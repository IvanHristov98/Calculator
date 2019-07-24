package com.calculator.core;

import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.calculator.core.exception.CalculatorException;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertEquals;

public class InfixExpressionFormatUnifierTest
{
	@Mock
	public Expression expression;
	@Mock
	public ExpressionModifier expressionModifier;
	InfixExpressionFormatUnifier formatUnifier;
	
	@Before
	public void setUp()
	{
		MockitoAnnotations.initMocks(this);
		
		this.formatUnifier = new InfixExpressionFormatUnifier(this.expression, this.expressionModifier);
	}
	
	@Test
	public void verifyOrderOfExpressionMethodCalls_process() throws CalculatorException
	{
		when(this.expression.getContent()).thenReturn("( 1 )");
		
		this.formatUnifier.process();
		
		InOrder mockOrder = inOrder(this.expression);
		
		//mockOrder.verify(this.expression).wrapWithBrackets();
		//mockOrder.verify(this.expression).stripContentSpaces();
		mockOrder.verify(this.expression).getContent();
		
		verifyNoMoreInteractions(this.expression);
	}
	
	@Test
	public void condensed_process() throws CalculatorException
	{
		when(this.expression.getContent()).thenReturn("(3+4+5)");
		
		Expression resultExpression = this.formatUnifier.process();
		assertEquals("( 3 + 4 + 5 )", resultExpression.getContent());
	}
	
	@Test
	public void condensedBrackets_process() throws CalculatorException
	{
		when(this.expression.getContent()).thenReturn("(3*(4+5))");
		
		Expression resultExpression = this.formatUnifier.process();
		assertEquals("( 3 * ( 4 + 5 ) )", resultExpression.getContent());
	}
	
	@Test
	public void floatingPointNumbers_process() throws CalculatorException
	{
		when(this.expression.getContent()).thenReturn("(3.5+123.4567)");
		
		Expression resultExpression = this.formatUnifier.process();
		assertEquals("( 3.5 + 123.4567 )", resultExpression.getContent());
	}
	
	@Test
	public void redundantOperatorAtStart_process() throws CalculatorException
	{
		when(this.expression.getContent()).thenReturn("(+1/2)");
		
		Expression resultExpression = this.formatUnifier.process();
		assertEquals("( 1 / 2 )", resultExpression.getContent());
	}
	
	@Test
	public void minusStraightAfterBracket_process() throws CalculatorException
	{
		when(this.expression.getContent()).thenReturn("((-1+1))");
		
		Expression resultExpression = this.formatUnifier.process();
		assertEquals("( ( -1 + 1 ) )", resultExpression.getContent());
	}
	
	@Test
	public void minusAtBeginning_process() throws CalculatorException
	{
		when(this.expression.getContent()).thenReturn("(-1+2)");
		
		Expression resultExpression = this.formatUnifier.process();
		assertEquals("( -1 + 2 )", resultExpression.getContent());
	}
}
