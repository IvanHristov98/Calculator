package com.calculator.core;

import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Before;
import org.junit.Test;

import com.calculator.core.exception.*;

public class UnformattedInfixExpressionValidatorTest
{
	@Mock
	public Expression expression;
	@Mock
	public ExpressionModifier expressionModifier;
	public UnformattedInfixExpressionValidator validator;
	
	@Before
	public void setUp()
	{
		MockitoAnnotations.initMocks(this);
		this.validator = new UnformattedInfixExpressionValidator(this.expression, this.expressionModifier);
	}
	
	@Test
	public void verifyOrderOfExpressionMethodCalls_process() throws Exception
	{
		final String CORRECT_STUB_VALUE = "(1)";
		when(this.expression.getContent()).thenReturn(CORRECT_STUB_VALUE);
		
		this.validator.process();
		
		InOrder mockOrder = inOrder(this.expression);
		
		//mockOrder.verify(this.expression).wrapWithBrackets();
		mockOrder.verify(this.expression).getContent();
		//mockOrder.verify(this.expression).stripContentSpaces();
		mockOrder.verify(this.expression).getContent();
		
		verifyNoMoreInteractions(this.expression);
	}
	
    @Test(expected = NumberMisplacementException.class)
    public void spacedConsecutiveNumbers_process() throws Exception
    {
    	when(this.expression.getContent()).thenReturn("( 1 2 )", "(12)");

    	this.validator.process();
    }

    @Test(expected = NumberMisplacementException.class)
    public void consecutive_process() throws Exception
    {
    	when(this.expression.getContent()).thenReturn("(2+4.55.6)", "(2+4.55.6)");
    	
    	this.validator.process();
    }

    @Test(expected = EmptyExpressionException.class)
    public void emptyExpression_process() throws Exception
    {
    	when(this.expression.getContent()).thenReturn("()", "()");
    	
    	this.validator.process();
    }

    @Test(expected = OperatorMisplacementException.class)
    public void manyLegitimateOperatorsAtBeginning_process() throws Exception
    {
    	when(this.expression.getContent()).thenReturn("(++1+2)", "(++1+2)");
    	
    	this.validator.process();
    }

    @Test(expected = OperatorMisplacementException.class)
    public void consecutiveOperators_process() throws Exception
    {
    	when(this.expression.getContent()).thenReturn("(2+*3)", "(2+*3)");
    	
    	this.validator.process();
    }

    @Test(expected = InvalidOperatorException.class)
    public void invalidTokenException_process() throws Exception
    {
    	when(this.expression.getContent()).thenReturn("(1A2)", "(1A2)");
    	
    	this.validator.process();
    }

    @Test(expected = BracketsException.class)
    public void numberGluedToTheLeftOfABracketedExpression_process() throws Exception
    {
    	when(this.expression.getContent()).thenReturn("(2(3+4))", "(2(3+4))");
    	
    	this.validator.process();
    }

    @Test(expected = BracketsException.class)
    public void numberGluedToTheRightOfABracketedExpression_process() throws Exception
    {
    	when(this.expression.getContent()).thenReturn("((3+4)5)", "((3+4)5)");
    	
    	this.validator.process();
    }

    @Test(expected = OperatorMisplacementException.class)
    public void misplacedOperatorAtStart_process() throws Exception
    {
    	when(this.expression.getContent()).thenReturn("(*1+2)", "(*1+2)");
    	
    	this.validator.process();
    }

    @Test(expected = OperatorMisplacementException.class)
    public void manyMisplacedOperatorsAtBeginning_process() throws Exception
    {
    	when(this.expression.getContent()).thenReturn("(**/1/2)", "(**/1/2)");
    	
    	this.validator.process();
    }

    @Test(expected = OperatorMisplacementException.class)
    public void misplacedOperatorAtEnd_process() throws Exception
    {
    	when(this.expression.getContent()).thenReturn("(1/2*)", "(1/2*)");
    	
    	this.validator.process();
    }

    @Test(expected = OperatorMisplacementException.class)
    public void manyMisplacedOperatorsAtEnd_process() throws Exception
    {
    	when(this.expression.getContent()).thenReturn("(1/2*/*/*)", "(1/2*/*/*)");
    	
    	this.validator.process();
    }

    @Test(expected = OperatorMisplacementException.class)
    public void singleOperator_process() throws Exception
    {
    	when(this.expression.getContent()).thenReturn("(+)", "(+)");
    	
    	this.validator.process();
    }

    @Test(expected = OperatorMisplacementException.class)
    public void misplacedOperatorsStraightAfterOpeningBracket_process() throws Exception
    {
    	when(this.expression.getContent()).thenReturn("((*3))", "((*3))");
    	
    	this.validator.process();
    }

    @Test(expected = OperatorMisplacementException.class)
    public void misplacedOperatorsAtEndsWithinBrackets_process() throws Exception
    {
    	when(this.expression.getContent()).thenReturn("((4+5*))", "((4+5*))");
    	
    	this.validator.process();
    }
    
    @Test(expected = OperatorMisplacementException.class)
    public void singleOperatorWithinBrackets_process() throws Exception
    {
    	when(this.expression.getContent()).thenReturn("((+))", "((+))");
    	
    	this.validator.process();
    }

    @Test(expected = EmptyExpressionException.class)
    public void emptyBrackets_process() throws Exception
    {
    	when(this.expression.getContent()).thenReturn("(1+())", "(1+())");
    	
    	this.validator.process();
    }
}
