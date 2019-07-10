package com.calculator;

import com.calculator.exception.*;

public abstract class ExpressionContainer
{
    protected Expression expression;

    protected ExpressionContainer()
    {}

    protected ExpressionContainer(Expression expression)
    {
        this.setExpression(expression);
    }

    public void setExpression(Expression expression)
    {
        this.expression = expression;
    }

    public Expression getExpression()
    {
        return this.expression.clone();
    }

    protected String stripSpaces(String expression)
    {
        return expression.replaceAll(" ", "");
    }

    public static ExpressionValidator makeValidatorFromExpression(Expression expression)
    {
        return new ExpressionValidator(expression);
    }

    public static ExpressionParser makeParserFromExpression(Expression expression)
    {
        return new ExpressionParser(expression);
    }
}
