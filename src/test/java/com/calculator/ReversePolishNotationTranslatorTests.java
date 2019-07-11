package com.calculator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ReversePolishNotationTranslatorTests
{
    @Test
    public void test_constructFromExpression()
    {
        Expression expression = new Expression("( 1 + 1 )");
        ReversePolishNotationTranslator parser = ReversePolishNotationTranslator.constructFromExpression(expression);

        assertEquals(expression, parser.getExpression());
    }

    @Test
    public void test_setExpression()
    {
        Expression expression = new Expression("( 1 + 1 )");
        ReversePolishNotationTranslator parser = ReversePolishNotationTranslator.constructFromExpression(expression);
        parser.setExpression(expression);

        assertEquals(expression, parser.getExpression());
    }

    @Test
    public void test__twoOperators_getConversedExpression() throws Exception
    {
        assertEquals("1 1 +", this.getConversedExpression("( 1 + 1 )"));
    }

    @Test
    public void test__expressionWithoutBrackets_getConversedExpression() throws Exception
    {
        assertEquals("1 1 +", this.getConversedExpression("1 + 1"));
    }

    @Test
    public void test_negativeNumbers_getConversedExpression() throws Exception
    {
        assertEquals("-1 1 +", this.getConversedExpression("-1 + 1"));
    }

    @Test
    public void test_priorityOfOperators_getConversedExpression() throws Exception
    {
        assertEquals("1 2 * 3 +", this.getConversedExpression("1 * 2 + 3"));
    }

    @Test
    public void test_equalPriorityOfLeftAssociativeOperators_getConversedExpression() throws Exception
    {
        assertEquals("1 2 / 3 *", this.getConversedExpression("1 / 2 * 3"));
    }

    @Test
    public void test_leftSideAssociativity_getConversedExpression() throws Exception
    {
        assertEquals("1 2 + 3 *", this.getConversedExpression("( 1 + 2 ) * 3"));
    }

    @Test
    public void test_rightSideAssociativity_getConversedExpression() throws Exception
    {
        assertEquals("1 2 3 + *", this.getConversedExpression("1 * ( 2 + 3 )"));
    }

    @Test
    public void test_multipleBrackets_getConversedExpression() throws Exception
    {
        assertEquals("1 1 +", this.getConversedExpression("( ( ( 1 + 1 ) ) )"));
    }

    @Test
    public void test_productOfTwoBracketedExpression_getConversedExpression() throws Exception
    {
        assertEquals("1 2 + 3 4 + *", this.getConversedExpression("( 1 + 2 ) * ( 3 + 4 )"));
    }

    @Test
    public void test_singlePowOfABracketedExpression_getConversedExpression() throws Exception
    {
        assertEquals("1 2 + 3 ^", this.getConversedExpression("( 1 + 2 ) ^ 3"));
    }

    @Test
    public void test_multiplePows_getConversedExpression() throws Exception
    {
        assertEquals("1 2 3 4 ^ ^ ^", this.getConversedExpression("1 ^ 2 ^ 3 ^ 4"));
    }

    @Test
    public void test_expressionWithinBracketsBetweenPows_getConversedExpression() throws Exception
    {
        assertEquals("1 2 3 + 4 ^ ^", this.getConversedExpression("1 ^ ( 2 + 3 ) ^ 4"));
    }

    public String getConversedExpression(String expressionContent) throws Exception
    {
        Expression expression = new Expression(expressionContent);
        ReversePolishNotationTranslator parser = ExpressionContainer.makeReversePolishNotationTranslatorFromExpression(expression);

        return parser.getConvertedExpression().getContent();
    }
}
