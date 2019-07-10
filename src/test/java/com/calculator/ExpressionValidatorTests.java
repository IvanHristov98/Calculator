package com.calculator;

import org.junit.Test;
import com.calculator.exception.*;

import static org.junit.Assert.assertTrue;

public class ExpressionValidatorTests
{
    @Test(expected = NumberMisplacementException.class)
    public void test_spacedConsecutiveNumbers() throws Exception
    {
        this.validateExpression("1 2");
    }

    @Test(expected = NumberMisplacementException.class)
    public void test_consecutive_getExpression() throws Exception
    {
        this.validateExpression("2+4.55.6");
    }

    @Test(expected = EmptyExpressionException.class)
    public void test_emptyExpression_getExpression() throws Exception
    {
        this.validateExpression("");
    }

    @Test(expected = OperatorMisplacementException.class)
    public void test_manyLegitimateOperatorsAtBeginning_getExpression() throws Exception
    {
        this.validateExpression("++1+2");
    }

    @Test(expected = OperatorMisplacementException.class)
    public void test_consecutiveOperators_getExpression() throws Exception
    {
        this.validateExpression("2+*3");
    }

    @Test(expected = OperatorMisplacementException.class)
    public void test_invalidTokenException_getExpression() throws Exception
    {
        this.validateExpression("1A2");
    }

    @Test(expected = BracketsException.class)
    public void test_numberGluedToTheLeftOfABracketedExpression_getExpression() throws Exception
    {
        this.validateExpression("2(3+4)");
    }

    @Test(expected = BracketsException.class)
    public void test_numberGluedToTheRightOfABracketedExpression_getExpression() throws Exception
    {
        this.validateExpression("(3+4)5");
    }

    private void validateExpression(String expressionContent) throws Exception
    {
        Expression expression = Expression.constructFromExpressionContent(expressionContent);
        ExpressionValidator validator = ExpressionContainer.makeValidatorFromExpression(expression);

        validator.validateExpression();
    }
}
