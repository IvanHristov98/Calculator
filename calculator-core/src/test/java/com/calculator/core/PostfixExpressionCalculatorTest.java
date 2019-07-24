package com.calculator.core;

import com.calculator.core.exception.*;

import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Before;
import org.junit.Test;


public class PostfixExpressionCalculatorTest
{
	@Mock
	Expression expression;
	Expression resultExpression;
	Double calculationResult;
    PostfixExpressionCalculator calculator;
   
    @Before
    public void setUp()
    {
    	MockitoAnnotations.initMocks(this);
    	
    	this.calculator = new PostfixExpressionCalculator(this.expression);
    }
    
    @Test
    public void verifyOrderOfExpressionMethodCalls() throws CalculatorException
    {
    	when(this.expression.getTokens()).thenReturn(new String[] {"1"});
    	
    	this.calculator.process();
    	
    	InOrder mockOrder = inOrder(this.expression);
    	
    	mockOrder.verify(this.expression).getTokens();
    
    	verifyNoMoreInteractions(this.expression);
    }

    @Test
    public void twoOperators_process() throws CalculatorException
    {
    	when(this.expression.getTokens()).thenReturn(new String[] {"1", "2", "+"});
    	
        assertEquals(3.0, this.getExpressionCalculationResult(), 0.0001);
    }

    @Test
    public void allExpression_process() throws CalculatorException
    {
    	when(this.expression.getTokens()).thenReturn(new String [] {"1", "2", "3", "4", "5", "+", "*", "/", "^"});
    	
        assertEquals(1 ^ (2 / (3 * (4 + 5))), this.getExpressionCalculationResult(), 0.0001);
    }

    @Test(expected = InvalidOperatorException.class)
    public void invalidOperator_process() throws CalculatorException
    {
    	when(this.expression.getTokens()).thenReturn(new String [] {"1", "A", "5"});
    	
        this.calculator.process();
    }
    
    @Test(expected = NumberMisplacementException.class)
    public void tooManyNumbers_process() throws CalculatorException
    {
    	when(this.expression.getTokens()).thenReturn(new String [] {"1", "2", "3", "+"});
    	
        this.calculator.process();
    }
    
    @Test(expected = NumberMisplacementException.class)
    public void tooFewNumbers_process() throws CalculatorException
    {
    	when(this.expression.getTokens()).thenReturn(new String [] {"1", "+"});
    	
    	this.calculator.process();
    }

    private double getExpressionCalculationResult() throws CalculatorException
    {
    	this.resultExpression = this.calculator.process();
    	this.calculationResult = Double.valueOf(this.resultExpression.getContent());
    	
        return this.calculationResult;
    }
}
