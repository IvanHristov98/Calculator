package com.calculator;

import com.calculator.exception.*;

import org.junit.*;
import static org.junit.Assert.assertEquals;

public class ReversePolishNotationCalculatorTests
{
    ReversePolishNotationCalculator calculator;
    Expression expression;

    @Test
    public void twoOperators_getExpressionResult() throws CalculatorException
    {
        assertEquals(3.0, this.getExpressionResult("1 2 +"), 0.0001);
    }

    @Test
    public void allExpression_getExpressionResult() throws CalculatorException
    {
        assertEquals(1 ^ (2 / (3 * (4 + 5))), this.getExpressionResult("1 2 3 4 5 + * / ^"), 0.0001);
    }

    @Test(expected = InvalidOperatorException.class)
    public void invalidOperator_getExpressionResult() throws CalculatorException
    {
        this.getExpressionResult("1 A 5");
    }

    @Test(expected = NumberMisplacementException.class)
    public void tooManyNumbers_getExpressionResult() throws CalculatorException
    {
        this.getExpressionResult("1 2 3 +");
    }

    @Test(expected = NumberMisplacementException.class)
    public void tooFewNumbers_getExpressionResult() throws CalculatorException
    {
        this.getExpressionResult("1 +");
    }

    private double getExpressionResult(String expressionContent) throws CalculatorException
    {
        this.expression = new Expression(expressionContent);
        this.calculator = new ReversePolishNotationCalculator(this.expression);

        return this.calculator.getExpressionResult();
    }
}
