package com.calculator.core;

import com.calculator.core.exception.*;

import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.ArgumentMatchers.any;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertEquals;

public class InfixToPostfixExpressionTranslatorTest
{
	@Mock
	public ExpressionManipulator expressionManipulator;
	public Expression resultExpression;
	public InfixToPostfixExpressionTranslator postfixTranslator;
	
	@Before
	public void setUp()
	{
		MockitoAnnotations.initMocks(this);
		
		this.postfixTranslator = new InfixToPostfixExpressionTranslator(new Expression(""), this.expressionManipulator);
	}
	
	@Test
	public void verifyOrderOfExpressionMethodCalls() throws CalculatorException
	{
		when(this.expressionManipulator.getExpressionTokens(any())).thenReturn(new String[] {"1"});
		
		this.postfixTranslator.process();
		
		InOrder mockOrder = inOrder(this.expressionManipulator);
		
		mockOrder.verify(this.expressionManipulator).getExpressionTokens(any());
		
		verifyNoMoreInteractions(this.expressionManipulator);
	}
    
    @Test(expected = BracketsException.class)
    public void missingOpeningBracket_process() throws Exception
    {
    	when(this.expressionManipulator.getExpressionTokens(any())).thenReturn(new String[] {"1", "+", "1", ")"});
    	
    	this.postfixTranslator.process();
    }
    
    @Test(expected = BracketsException.class)
    public void missingClosingBracket_process() throws Exception
    {
    	when(this.expressionManipulator.getExpressionTokens(any())).thenReturn(new String[] {"(", "1", "+", "1"});
    	
    	this.postfixTranslator.process();
    }
    
    @Test
    public void twoOperators_process() throws Exception
    {
    	when(this.expressionManipulator.getExpressionTokens(any())).thenReturn(new String[] {"(", "1", "+", "1", ")"});
    	
    	this.resultExpression = this.postfixTranslator.process();
        assertEquals("1 1 +", this.resultExpression.getContent());
    }
    
    @Test
    public void expressionWithoutBrackets_process() throws Exception
    {
    	when(this.expressionManipulator.getExpressionTokens(any())).thenReturn(new String[] {"1", "+", "1"});
    	
    	this.resultExpression = this.postfixTranslator.process();
        assertEquals("1 1 +", this.resultExpression.getContent());
    }
   
    @Test
    public void negativeNumbers_process() throws Exception
    {
    	when(this.expressionManipulator.getExpressionTokens(any())).thenReturn(new String[] {"-1", "+", "1"});
    	
    	this.resultExpression = this.postfixTranslator.process();
        assertEquals("-1 1 +", this.resultExpression.getContent());
    }
    
    @Test
    public void priorityOfOperators_process() throws Exception
    {
    	when(this.expressionManipulator.getExpressionTokens(any())).thenReturn(new String[] {"1", "*", "2", "+", "3"});
    	
    	this.resultExpression = this.postfixTranslator.process();
        assertEquals("1 2 * 3 +", this.resultExpression.getContent());
    }
    
    @Test
    public void equalPriorityOfLeftAssociativeOperators_process() throws Exception
    {
    	when(this.expressionManipulator.getExpressionTokens(any())).thenReturn(new String[] {"1", "/", "2", "*", "3"});
    	
    	this.resultExpression = this.postfixTranslator.process();
        assertEquals("1 2 / 3 *", this.resultExpression.getContent());
    }
    
    @Test
    public void leftSideAssociativity_process() throws Exception
    {
    	when(this.expressionManipulator.getExpressionTokens(any())).thenReturn(new String[] {"(", "1", "+", "2", ")", "*", "3"});
    	
    	this.resultExpression = this.postfixTranslator.process();
        assertEquals("1 2 + 3 *", this.resultExpression.getContent());
    }
    
    @Test
    public void rightSideAssociativity_process() throws Exception
    {
    	when(this.expressionManipulator.getExpressionTokens(any())).thenReturn(new String[] {"1", "*", "(", "2", "+", "3", ")"});
    	
    	this.resultExpression = this.postfixTranslator.process();
        assertEquals("1 2 3 + *", this.resultExpression.getContent());
    }
    
    @Test
    public void multipleBrackets_process() throws Exception
    {
    	when(this.expressionManipulator.getExpressionTokens(any())).thenReturn(new String[] {"(", "(", "(", "1", "+", "1", ")", ")", ")"});
    	
    	this.resultExpression = this.postfixTranslator.process();
        assertEquals("1 1 +", this.resultExpression.getContent());
    }
    
    @Test
    public void productOfTwoBracketedExpression_process() throws Exception
    {
    	when(this.expressionManipulator.getExpressionTokens(any())).thenReturn(new String[] {"(", "1", "+", "2", ")", "*", "(", "3", "+", "4", ")"});
    	
    	this.resultExpression = this.postfixTranslator.process();
        assertEquals("1 2 + 3 4 + *", this.resultExpression.getContent());
    }
    
    @Test
    public void singlePowOfABracketedExpression_process() throws Exception
    {
    	when(this.expressionManipulator.getExpressionTokens(any())).thenReturn(new String[] {"(", "1", "+", "2", ")", "^", "3"});
    	
    	this.resultExpression = this.postfixTranslator.process();
        assertEquals("1 2 + 3 ^", this.resultExpression.getContent());
    }
    
    @Test
    public void multiplePows_process() throws Exception
    {
    	when(this.expressionManipulator.getExpressionTokens(any())).thenReturn(new String [] {"1", "^", "2", "^", "3", "^", "4"});
    	
    	this.resultExpression = this.postfixTranslator.process();
        assertEquals("1 2 3 4 ^ ^ ^", this.resultExpression.getContent());
    }
    
    @Test
    public void expressionWithinBracketsBetweenPows_process() throws Exception
    {
    	when(this.expressionManipulator.getExpressionTokens(any())).thenReturn(new String [] {"1", "^", "(", "2", "+", "3", ")", "^", "4"});
    	
    	this.resultExpression = this.postfixTranslator.process();
        assertEquals("1 2 3 + 4 ^ ^", this.resultExpression.getContent());
    }
}
