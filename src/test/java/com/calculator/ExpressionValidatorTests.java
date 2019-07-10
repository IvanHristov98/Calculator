package com.calculator;

import org.junit.Test;
import com.calculator.exception.*;

import static org.junit.Assert.assertTrue;

public class ExpressionValidatorTests
{
    @Test(expected = OperatorMisplacementException.class)
    public void test_spacedConsecutiveNumbers() throws Exception
    {
        this.validateExpression("1 2");
    }

    private void validateExpression(String expressionContent) throws Exception
    {
        Expression expression = Expression.constructFromExpressionContent(expressionContent);
        ExpressionValidator validator = ExpressionContainer.makeValidatorFromExpression(expression);

        validator.validateExpression();
    }
}
