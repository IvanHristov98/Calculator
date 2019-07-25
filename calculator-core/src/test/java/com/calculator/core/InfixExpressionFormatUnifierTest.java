package com.calculator.core;

import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.calculator.core.exception.CalculatorException;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertEquals;

public class InfixExpressionFormatUnifierTest
{
	public Expression expression;
	@Mock
	public ExpressionManipulator expressionModifier;
	InfixExpressionFormatter formatUnifier;
	
	@Before
	public void setUp()
	{
		MockitoAnnotations.initMocks(this);
		
		// initializing with a correct stub value
		this.expression = new Expression("1");
		this.formatUnifier = new InfixExpressionFormatter(this.expression, this.expressionModifier);
	}
	
	@Test
	public void verifyOrderOfExpressionMethodCalls_process() throws CalculatorException
	{
		when(this.expressionModifier.getExpressionWrappedWithBrackets(any(Expression.class))).thenReturn(new Expression("(1)"));
		when(this.expressionModifier.getExpressionWithStrippedWhiteSpaces(any(Expression.class))).thenReturn(new Expression("(1)"));
		
		this.formatUnifier.process();
		
		InOrder mockOrder = inOrder(this.expressionModifier);
		
		mockOrder.verify(this.expressionModifier).getExpressionWrappedWithBrackets(any());
		mockOrder.verify(this.expressionModifier).getExpressionWithStrippedWhiteSpaces(any());
		
		mockOrder.verifyNoMoreInteractions();
	}
	
	@Test
	public void condensed_process() throws CalculatorException
	{
		this.stubDependenciesOfProcess("3+4+5", "(3+4+5)", "(3+4+5)");
		
		Expression resultExpression = this.formatUnifier.process();
		assertEquals("( 3 + 4 + 5 )", resultExpression.getContent());
	}
	
	@Test
	public void condensedBrackets_process() throws CalculatorException
	{
		this.stubDependenciesOfProcess("3*(4+5)", "(3*(4+5))", "(3*(4+5))");
		
		Expression resultExpression = this.formatUnifier.process();
		assertEquals("( 3 * ( 4 + 5 ) )", resultExpression.getContent());
	}
	
	@Test
	public void floatingPointNumbers_process() throws CalculatorException
	{
		this.stubDependenciesOfProcess("3.5+123.4567", "(3.5+123.4567)", "(3.5+123.4567)");
		
		Expression resultExpression = this.formatUnifier.process();
		assertEquals("( 3.5 + 123.4567 )", resultExpression.getContent());
	}
	
	@Test
	public void redundantOperatorAtStart_process() throws CalculatorException
	{
		this.stubDependenciesOfProcess("+1/2", "(+1/2)", "(+1/2)");
		
		Expression resultExpression = this.formatUnifier.process();
		assertEquals("( 1 / 2 )", resultExpression.getContent());
	}
	
	@Test
	public void minusStraightAfterBracket_process() throws CalculatorException
	{
		this.stubDependenciesOfProcess("(-1+1)", "((-1+1))", "((-1+1))");
		
		Expression resultExpression = this.formatUnifier.process();
		assertEquals("( ( -1 + 1 ) )", resultExpression.getContent());
	}
	
	@Test
	public void minusAtBeginning_process() throws CalculatorException
	{
		this.stubDependenciesOfProcess("-1+2", "(-1+2)", "(-1+2)");
		
		Expression resultExpression = this.formatUnifier.process();
		assertEquals("( -1 + 2 )", resultExpression.getContent());
	}
	
    private void stubDependenciesOfProcess(String initialExpression, String wrappedExpression, String wrappedExpressionWithStrippedWhiteSpaces)
    {
    	this.expression.setContent(initialExpression);
    	when(this.expressionModifier.getExpressionWrappedWithBrackets(any())).thenReturn(new Expression(wrappedExpression));
    	when(this.expressionModifier.getExpressionWithStrippedWhiteSpaces(any())).thenReturn(new Expression(wrappedExpressionWithStrippedWhiteSpaces));
    }
}
