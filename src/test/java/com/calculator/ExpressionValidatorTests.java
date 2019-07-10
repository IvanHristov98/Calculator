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

    private void validateExpression(String expressionContent) throws Exception
    {
        Expression expression = Expression.constructFromExpressionContent(expressionContent);
        ExpressionValidator validator = ExpressionContainer.makeValidatorFromExpression(expression);

        validator.validateExpression();
    }
}
