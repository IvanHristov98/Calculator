package com.calculator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ShuntingYardParserTests
{
    @Test
    public void test_constructFromExpression() throws Exception
    {
        Expression expression = Expression.constructFromExpressionContent("( 1 + 1 )");
        ShuntingYardParser parser = ShuntingYardParser.constructFromExpression(expression);

        assertEquals(expression, parser.getExpression());
    }

    @Test
    public void test_setExpression() throws Exception
    {
        Expression expression = Expression.constructFromExpressionContent("( 1 + 1 )");
        ShuntingYardParser parser = ShuntingYardParser.constructFromExpression(expression);
        parser.setExpression(expression);

        assertEquals(expression, parser.getExpression());
    }
}