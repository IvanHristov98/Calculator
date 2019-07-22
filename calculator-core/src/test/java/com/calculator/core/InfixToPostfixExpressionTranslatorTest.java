package com.calculator.core;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class InfixToPostfixExpressionTranslatorTest
{
    @Test
    public void constructFromExpression()
    {
        Expression expression = new Expression("( 1 + 1 )");
        InfixToPostfixExpressionTranslator parser = InfixToPostfixExpressionTranslator.constructFromExpression(expression);

        assertEquals(expression, parser.getExpression());
    }

    @Test
    public void setExpression()
    {
        Expression expression = new Expression("( 1 + 1 )");
        InfixToPostfixExpressionTranslator parser = InfixToPostfixExpressionTranslator.constructFromExpression(expression);
        parser.setExpression(expression);

        assertEquals(expression, parser.getExpression());
    }

    @Test
    public void twoOperators_process() throws Exception
    {
        assertEquals("1 1 +", this.getConversedExpression("( 1 + 1 )"));
    }

    @Test
    public void expressionWithoutBrackets_process() throws Exception
    {
        assertEquals("1 1 +", this.getConversedExpression("1 + 1"));
    }

    @Test
    public void negativeNumbers_process() throws Exception
    {
        assertEquals("-1 1 +", this.getConversedExpression("-1 + 1"));
    }

    @Test
    public void priorityOfOperators_process() throws Exception
    {
        assertEquals("1 2 * 3 +", this.getConversedExpression("1 * 2 + 3"));
    }

    @Test
    public void equalPriorityOfLeftAssociativeOperators_process() throws Exception
    {
        assertEquals("1 2 / 3 *", this.getConversedExpression("1 / 2 * 3"));
    }

    @Test
    public void leftSideAssociativity_process() throws Exception
    {
        assertEquals("1 2 + 3 *", this.getConversedExpression("( 1 + 2 ) * 3"));
    }

    @Test
    public void rightSideAssociativity_process() throws Exception
    {
        assertEquals("1 2 3 + *", this.getConversedExpression("1 * ( 2 + 3 )"));
    }

    @Test
    public void multipleBrackets_process() throws Exception
    {
        assertEquals("1 1 +", this.getConversedExpression("( ( ( 1 + 1 ) ) )"));
    }

    @Test
    public void productOfTwoBracketedExpression_process() throws Exception
    {
        assertEquals("1 2 + 3 4 + *", this.getConversedExpression("( 1 + 2 ) * ( 3 + 4 )"));
    }

    @Test
    public void singlePowOfABracketedExpression_process() throws Exception
    {
        assertEquals("1 2 + 3 ^", this.getConversedExpression("( 1 + 2 ) ^ 3"));
    }

    @Test
    public void multiplePows_process() throws Exception
    {
        assertEquals("1 2 3 4 ^ ^ ^", this.getConversedExpression("1 ^ 2 ^ 3 ^ 4"));
    }

    @Test
    public void expressionWithinBracketsBetweenPows_process() throws Exception
    {
        assertEquals("1 2 3 + 4 ^ ^", this.getConversedExpression("1 ^ ( 2 + 3 ) ^ 4"));
    }

    public String getConversedExpression(String expressionContent) throws Exception
    {
        Expression expression = new Expression(expressionContent);
        InfixToPostfixExpressionTranslator parser = new InfixToPostfixExpressionTranslator(expression);

        return parser.process().getContent();
    }
}
